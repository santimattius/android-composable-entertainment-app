package com.santimattius.template.data.client.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.santimattius.template.data.entities.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    companion object {

        private const val DATABASE_NAME = "todo_database"

        fun get(context: Context) =
            Room.databaseBuilder(context, AppDataBase::class.java, DATABASE_NAME)
                .build()
    }

    abstract fun dao(): MovieDao
}