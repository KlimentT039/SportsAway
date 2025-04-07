package com.diplomska.sportsaway.feature.events.view.visualtransformation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class ExpirationDateVisualTransformation : VisualTransformation {

  override fun filter(text: AnnotatedString): TransformedText {
    val rawInput = text.text.take(4)
    val formatted = buildString {
      rawInput.forEachIndexed { index, char ->
        append(char)
        if (index == 1 && rawInput.length > 2) {
          append('/')
        }
      }
    }

    val transformedText = AnnotatedString(formatted)

    val offsetMapping = object : OffsetMapping {
      override fun originalToTransformed(offset: Int): Int {
        return when {
          offset <= 1 -> offset
          offset <= 3 && rawInput.length > 2 -> (offset + 1).coerceAtMost(transformedText.text.length)
          else -> transformedText.text.length
        }
      }

      override fun transformedToOriginal(offset: Int): Int {
        return when {
          offset <= 2 -> offset
          offset <= 5 -> (offset - 1).coerceAtMost(rawInput.length)
          else -> rawInput.length
        }
      }
    }

    return TransformedText(transformedText, offsetMapping)
  }
}
