package com.example.catsappchallenge.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.catsappchallenge.data.dao.BreedDao
import com.example.catsappchallenge.data.model.Breed

@Database(
    entities = [Breed::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class CatsAppDatabase: RoomDatabase() {
    abstract val breedDao: BreedDao

    companion object {
        @Volatile
        private var INSTANCE: CatsAppDatabase? = null

        fun getDatabase(
            context: Context
        ): CatsAppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CatsAppDatabase::class.java,
                    "catsAppDB"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}