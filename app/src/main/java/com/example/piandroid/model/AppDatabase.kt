package com.example.piandroid.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//Criação database
@Database(entities =
    [Livro::class], //Aqui podem ser inseridos os objetos
    version = 3,
    exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun livroDao(): LivroDao

    companion object{
        // Volatile para garantir que a instância seja visível para todas as threads
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // retorna a instância do banco de dados
        fun getDatabase(context: Context): AppDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "bookUp_database" //Nome do banco de dados do App
                ).fallbackToDestructiveMigration().build()//Se uma atualização corromper os dados, o banco de dados será destruído e recriado
                INSTANCE = instance
                //Retorna instancia criadau
                //return instance (mesma coisa)
                instance

            }
        }
    }

}