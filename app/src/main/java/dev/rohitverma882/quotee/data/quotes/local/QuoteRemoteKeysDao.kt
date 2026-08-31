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

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Data Access Object for quote remote keys.
 */
@Dao
interface QuoteRemoteKeysDao {
    /**
     * Returns the remote keys for the given quote ID.
     */
    @Query(
        """
        SELECT *
        FROM quote_remote_keys
        WHERE quoteId = :quoteId
        """
    )
    suspend fun getById(quoteId: Int): QuoteRemoteKeysEntity?

    /**
     * Inserts a list of remote keys into the database.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(keys: List<QuoteRemoteKeysEntity>)

    /**
     * Clears all remote keys from the database.
     */
    @Query("DELETE FROM quote_remote_keys")
    suspend fun clear()
}