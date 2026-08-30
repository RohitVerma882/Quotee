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

@Singleton
class SettingsSerializer @Inject constructor(
    private val json: Json,
    @param:IoDispatcher
    private val dispatcher: CoroutineDispatcher
) : Serializer<AppSettings> {
    override val defaultValue = AppSettings()

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

    override suspend fun writeTo(t: AppSettings, output: OutputStream) = withContext(dispatcher) {
        output.write(
            json.encodeToString(
                serializer = AppSettings.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }
}
