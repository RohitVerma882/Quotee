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

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit API interface for accessing quotes.
 */
interface QuotesApi {
    /**
     * Fetches a list of quotes from the API.
     *
     * @param limit The maximum number of quotes to return.
     * @param skip The number of quotes to skip.
     * @return A [QuotesResponseDto] containing the list of quotes.
     */
    @GET("quotes")
    suspend fun getQuotes(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int,
    ): QuotesResponseDto

    companion object {
        /**
         * The base URL for the quotes API.
         */
        const val BASE_URL = "https://dummyjson.com/"
    }
}