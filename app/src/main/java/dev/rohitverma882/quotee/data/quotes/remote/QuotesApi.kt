package dev.rohitverma882.quotee.data.quotes.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface QuotesApi {
    @GET("quotes")
    suspend fun getQuotes(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int,
    ): QuotesResponseDto

    companion object {
        const val BASE_URL = "https://dummyjson.com/"
    }
}