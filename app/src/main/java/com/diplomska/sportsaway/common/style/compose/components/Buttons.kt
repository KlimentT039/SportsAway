package com.diplomska.sportsaway.common.style.compose.components


import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.diplomska.sportsaway.R
import com.diplomska.sportsaway.common.style.compose.theme.mainColor
import kotlin.math.roundToInt

object Buttons {

  @Composable
  fun SlideToOrderButton(
    onSlideComplete: () -> Unit,
    modifier: Modifier = Modifier,
    trackColor: Color = mainColor,
    handleColor: Color = Color.White,
    textColor: Color = Color.White,
    buttonText: String = stringResource(R.string.slide_to_place_order),
    isEnabled: Boolean = true,
  ) {
    var offsetX by remember { mutableFloatStateOf(0f) }
    var isCompleted by remember { mutableStateOf(false) }

    val sliderHeight = 56.dp
    val handleDiameter = 52.dp

    BoxWithConstraints(
      modifier = modifier
        .fillMaxWidth()
        .height(sliderHeight)
    ) {
      val maxWidth = constraints.maxWidth.toFloat()
      val handleDiameterPx = with(LocalDensity.current) { handleDiameter.toPx() }

      // Slider track
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(sliderHeight)
          .background(
            color = if (isEnabled) trackColor else trackColor.copy(alpha = 0.5f),
            shape = RoundedCornerShape(30.dp)
          ),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = buttonText,
          fontSize = 16.sp,
          fontWeight = FontWeight.Bold,
          color = if (isEnabled) textColor else textColor.copy(alpha = 0.5f)
        )
      }

      // Slider handle
      Box(
        modifier = Modifier
          .offset { IntOffset(offsetX.roundToInt(), 0) }
          .size(handleDiameter)
          .padding(5.dp)
          .align(Alignment.CenterStart)
          .background(
            color = if (isEnabled) handleColor else handleColor.copy(alpha = 0.5f),
            shape = CircleShape
          )
          .pointerInput(isEnabled) {
            if (isEnabled) {
              detectHorizontalDragGestures { _, dragAmount ->
                if (!isCompleted) {
                  offsetX = (offsetX + dragAmount)
                    .coerceIn(0f, maxWidth - handleDiameterPx)
                  if (offsetX >= maxWidth - handleDiameterPx) {
                    isCompleted = true
                    onSlideComplete()
                  }
                }
              }
            }
          }
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
fun SlideToOrderButtonPreview() {
  Column {
    Buttons.SlideToOrderButton(
      onSlideComplete = { /* Handle completion */ },
      isEnabled = true // Enabled button
    )
    Spacer(modifier = Modifier.height(16.dp))
    Buttons.SlideToOrderButton(
      onSlideComplete = { /* Handle completion */ },
      isEnabled = false // Disabled button
    )
  }
}
