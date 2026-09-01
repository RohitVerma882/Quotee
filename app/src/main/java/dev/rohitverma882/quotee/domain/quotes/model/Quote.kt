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

package dev.rohitverma882.quotee.domain.quotes.model

/**
 * Domain model representing a quote.
 *
 * @property id The unique identifier of the quote.
 * @property content The text content of the quote.
 * @property author The author of the quote.
 */
data class Quote(
    val id: Int,
    val content: String,
    val author: String
) {
    val displayContent get() = "“$content”"
}
