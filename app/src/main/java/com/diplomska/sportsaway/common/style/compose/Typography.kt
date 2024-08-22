package com.diplomska.sportsaway.common.style.compose

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.diplomska.sportsaway.common.style.compose.theme.typographyTextPrimary
import com.diplomska.sportsaway.common.style.compose.theme.typographyTextSecondary

data class Typography(
  val mRegular: TextStyle,
  val xsRegular: TextStyle,
  val sRegularPrimary: TextStyle,
  val sRegularSecundary: TextStyle,
  val mLarge: TextStyle
)

val typography = Typography(
  mRegular = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight(500),
    fontSize = 18.sp,
    color = typographyTextPrimary
  ),
  mLarge = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight(500),
    fontSize = 22.sp,
    color = typographyTextPrimary
  ),
  xsRegular = TextStyle(
    fontFamily = FontFamily.Default,
    fontSize = 14.sp,
    color = typographyTextSecondary
  ),
  sRegularPrimary = TextStyle(
    fontFamily = FontFamily.Default,
    fontSize = 16.sp,
    color = typographyTextPrimary
  ),
  sRegularSecundary = TextStyle(
    fontFamily = FontFamily.Default,
    fontSize = 16.sp,
    color = typographyTextSecondary
  )
)