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

package dev.rohitverma882.quotee.data.quotes.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Data Access Object for quotes.
 */
@Dao
interface QuoteDao {
    /**
     * Returns a [PagingSource] for paginated quotes from the database.
     */
    @Query("SELECT * FROM quotes ORDER BY id ASC")
    fun pagingSource(): PagingSource<Int, QuoteEntity>

    /**
     * Inserts a list of quotes into the database.
     *
     * @param quotes The list of [QuoteEntity] to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(quotes: List<QuoteEntity>)

    /**
     * Clears all quotes from the database.
     */
    @Query("DELETE FROM quotes")
    suspend fun clear()

    /**
     * Returns the total number of quotes in the database.
     */
    @Query("SELECT COUNT(*) FROM quotes")
    suspend fun count(): Int
}