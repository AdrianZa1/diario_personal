package com.example.inventory.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.example.DiarioApplication.data.Etiqueta.Etiqueta
import com.example.DiarioApplication.data.Etiqueta.EtiquetaDao
import com.example.DiarioApplication.data.Note.Note
import com.example.DiarioApplication.data.Note.NoteDao
import com.tuapp.model.User

@Database(entities = [User::class, Note::class, Etiqueta::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun noteDao(): NoteDao
    abstract fun etiquetaDao(): EtiquetaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

