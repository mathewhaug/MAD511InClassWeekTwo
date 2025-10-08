package com.example.myapplication.data


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.model.Note


@Database(entities = [Note::class], version = 2, exportSchema = false)
abstract class CoreDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: CoreDatabase? = null
/*
DO NOT USE .fallbackToDestructiveMigration() IN PRODUCTION
IT WILL DELETE ALL YOUR USERS DATA - I PROMISE
 */
        fun getDatabase(context: Context): CoreDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CoreDatabase::class.java,
                    "core_database"
                ).fallbackToDestructiveMigration() //Destruction and rebuild when schema changes
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

