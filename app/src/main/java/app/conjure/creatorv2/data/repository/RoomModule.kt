package app.conjure.creatorv2.data.repository

import android.content.Context
import androidx.room.Room
import app.conjure.creatorv2.data.room.AppDatabase
import app.conjure.creatorv2.data.room.CardDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context):
        AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, "card_database")
                .fallbackToDestructiveMigration()
                .build()

    @Provides
    fun provideCardDAO(appDatabase: AppDatabase):
        CardDAO = appDatabase.getCardDAO()

    @Provides
    fun provideRoomRepository(cardDAO: CardDAO):
        RoomRepository = RoomRepository(cardDAO)
}