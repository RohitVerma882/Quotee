package dev.rohitverma882.quotee.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont

import dev.rohitverma882.quotee.R

private val GoogleFontProvider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.google_fonts_certs,
)

private val GoogleSansFont = GoogleFont("Google Sans")

private val GoogleSansFontFamily = FontFamily(
    Font(
        fontProvider = GoogleFontProvider,
        googleFont = GoogleSansFont,
        weight = FontWeight.Normal
    ),
    Font(
        fontProvider = GoogleFontProvider,
        googleFont = GoogleSansFont,
        weight = FontWeight.Medium
    ),
    Font(
        fontProvider = GoogleFontProvider,
        googleFont = GoogleSansFont,
        weight = FontWeight.Normal,
        style = FontStyle.Italic
    ),
    Font(
        fontProvider = GoogleFontProvider,
        googleFont = GoogleSansFont,
        weight = FontWeight.Medium,
        style = FontStyle.Italic
    )
)

private val baseline = Typography()

internal val QuoteeTypography = Typography(
    displayLarge = baseline.displayLarge.copy(
        fontFamily = GoogleSansFontFamily
    ),
    displayMedium = baseline.displayMedium.copy(
        fontFamily = GoogleSansFontFamily
    ),
    displaySmall = baseline.displaySmall.copy(
        fontFamily = GoogleSansFontFamily
    ),
    headlineLarge = baseline.headlineLarge.copy(
        fontFamily = GoogleSansFontFamily
    ),
    headlineMedium = baseline.headlineMedium.copy(
        fontFamily = GoogleSansFontFamily
    ),
    headlineSmall = baseline.headlineSmall.copy(
        fontFamily = GoogleSansFontFamily
    ),
    titleLarge = baseline.titleLarge.copy(
        fontFamily = GoogleSansFontFamily
    ),
    titleMedium = baseline.titleMedium.copy(
        fontFamily = GoogleSansFontFamily,
        fontWeight = FontWeight.Medium
    ),
    titleSmall = baseline.titleSmall.copy(
        fontFamily = GoogleSansFontFamily,
        fontWeight = FontWeight.Medium
    ),
    bodyLarge = baseline.bodyLarge.copy(
        fontFamily = GoogleSansFontFamily
    ),
    bodyMedium = baseline.bodyMedium.copy(
        fontFamily = GoogleSansFontFamily
    ),
    bodySmall = baseline.bodySmall.copy(
        fontFamily = GoogleSansFontFamily
    ),
    labelLarge = baseline.labelLarge.copy(
        fontFamily = GoogleSansFontFamily,
        fontWeight = FontWeight.Medium
    ),
    labelMedium = baseline.labelMedium.copy(
        fontFamily = GoogleSansFontFamily,
        fontWeight = FontWeight.Medium
    ),
    labelSmall = baseline.labelSmall.copy(
        fontFamily = GoogleSansFontFamily,
        fontWeight = FontWeight.Medium
    )
)