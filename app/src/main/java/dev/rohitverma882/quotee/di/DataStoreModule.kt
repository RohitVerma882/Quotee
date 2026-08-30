package dev.rohitverma882.quotee.di

import android.content.Context

import androidx.datastore.core.DataStoreFactory

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

import dev.rohitverma882.quotee.common.ApplicationScope
import dev.rohitverma882.quotee.data.settings.SettingsSerializer

import kotlinx.coroutines.CoroutineScope

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun provideSettingsDataStore(
        @ApplicationContext context: Context,
        @ApplicationScope scope: CoroutineScope,
        serializer: SettingsSerializer
    ) = DataStoreFactory.createInDeviceProtectedStorage(
        context = context,
        scope = scope,
        serializer = serializer,
        fileName = "settings.json"
    )
}
