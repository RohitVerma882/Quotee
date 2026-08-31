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

package dev.rohitverma882.quotee.data.settings

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer

import dev.rohitverma882.quotee.common.IoDispatcher
import dev.rohitverma882.quotee.domain.settings.model.AppSettings

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

import java.io.InputStream
import java.io.OutputStream

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Serializer for [AppSettings] to be used with DataStore.
 */
@Singleton
class SettingsSerializer @Inject constructor(
    private val json: Json,
    @param:IoDispatcher
    private val dispatcher: CoroutineDispatcher
) : Serializer<AppSettings> {
    /**
     * The default [AppSettings] value.
     */
    override val defaultValue = AppSettings()

    /**
     * Reads [AppSettings] from the [InputStream].
     */
    override suspend fun readFrom(input: InputStream): AppSettings {
        return try {
            json.decodeFromString(
                deserializer = AppSettings.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            throw CorruptionException("Cannot read json.", e)
        }
    }

    /**
     * Writes [AppSettings] to the [OutputStream].
     */
    override suspend fun writeTo(t: AppSettings, output: OutputStream) = withContext(dispatcher) {
        output.write(
            json.encodeToString(
                serializer = AppSettings.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }
}
