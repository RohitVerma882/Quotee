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

package dev.rohitverma882.quotee.data.quotes.remote

import kotlinx.serialization.Serializable

/**
 * Data transfer object for the quotes response.
 *
 * @property quotes The list of [QuoteDto].
 * @property total The total number of quotes available.
 * @property skip The number of quotes skipped.
 * @property limit The maximum number of quotes returned.
 */
@Serializable
data class QuotesResponseDto(
    val quotes: List<QuoteDto>,
    val total: Int,
    val skip: Int,
    val limit: Int,
)

/**
 * Data transfer object for a single quote.
 *
 * @property id The unique identifier of the quote.
 * @property quote The text content of the quote.
 * @property author The author of the quote.
 */
@Serializable
data class QuoteDto(
    val id: Int,
    val quote: String,
    val author: String,
)