package com.glaze.knowledgebase.di
import android.content.Context
import androidx.room.Room
import com.glaze.knowledgebase.data.local.GlazeDatabase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides @Singleton
    fun provideGlazeDatabase(@ApplicationContext context: Context): GlazeDatabase =
        Room.databaseBuilder(context, GlazeDatabase::class.java, "glaze.db").build()
    @Provides @Singleton
    fun provideGson(): Gson = Gson()
}
