package br.com.rotacilio.android.boredapp.di

import android.content.Context
import androidx.room.Room
import br.com.rotacilio.android.boredapp.db.AppDatabase
import br.com.rotacilio.android.boredapp.db.dao.ActivityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class StorageModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "bored-app-database"
        ).build()

    @Provides
    fun provideActivityDao(database: AppDatabase): ActivityDao = database.activityDao()
}