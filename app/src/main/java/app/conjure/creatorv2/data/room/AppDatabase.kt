package app.conjure.creatorv2.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.conjure.creatorv2.data.CardModel
import app.conjure.creatorv2.data.Converters

@Database(entities = [CardModel::class], version = 5)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getCardDAO(): CardDAO
}