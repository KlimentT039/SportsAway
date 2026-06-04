package com.diplomska.sportsaway.common.style.compose.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val SportsAwayColorScheme = lightColorScheme(
  primary = mainColor,
  onPrimary = topBarTextColor,
  primaryContainer = sectionColor,
  onPrimaryContainer = mainColor,
  secondary = buttonColor,
  onSecondary = topBarTextColor,
  secondaryContainer = sectionColor,
  onSecondaryContainer = mainColor,
  tertiary = buttonColor,
  onTertiary = topBarTextColor,
  background = backgroundDefault,
  onBackground = typographyTextPrimary,
  surface = backgroundSurface,
  onSurface = typographyTextPrimary,
  surfaceVariant = backgroundDefault,
  onSurfaceVariant = typographyTextSecondary,
  outline = outlineColor,
  error = errorColor,
  onError = topBarTextColor
)

private val SportsAwayTypography = Typography(
  titleLarge = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.SemiBold,
    fontSize = 22.sp,
    color = typographyTextPrimary
  ),
  titleMedium = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.SemiBold,
    fontSize = 18.sp,
    color = typographyTextPrimary
  ),
  titleSmall = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp,
    color = typographyTextPrimary
  ),
  bodyLarge = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    color = typographyTextPrimary
  ),
  bodyMedium = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    color = typographyTextPrimary
  ),
  bodySmall = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp,
    color = typographyTextSecondary
  ),
  labelLarge = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp,
    color = typographyTextPrimary
  )
)

@Composable
fun SportsAwayTheme(content: @Composable () -> Unit) {
  MaterialTheme(
    colorScheme = SportsAwayColorScheme,
    typography = SportsAwayTypography,
    content = content
  )
}
