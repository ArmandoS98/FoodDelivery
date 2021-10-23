package com.aesc.fooddelivery.providers.database.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aesc.fooddelivery.providers.database.models.Favorites
import com.aesc.fooddelivery.providers.database.models.Pedidos

@Database(entities = [Favorites::class, Pedidos::class], version = 4, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getFavoritesDao(): AccessDao

    companion object {
        @Volatile
        private var db_instance: AppDatabase? = null
        fun getAppDatabaseInstance(context: Context): AppDatabase {
            return db_instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                db_instance = instance
                instance
            }
        }
    }
}