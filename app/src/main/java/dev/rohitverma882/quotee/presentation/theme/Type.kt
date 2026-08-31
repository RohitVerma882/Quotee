/*
 * Copyright (C) 2026  Rohit Verma
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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