package com.example.piandroid.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//Criação database
@Database(entities =
    [Livro::class], //Aqui podem ser inseridos os objetos
    version = 1,
    exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun livroDao(): LivroDao

    companion object { //Companion object pode ser utilizado em qualquer lugar do codigo sem precisar instanciar
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "bookup_db"
            ).build()
    }
}

//        fun getDatabase(context: Context): AppDatabase{ //Pega a instancia do banco de dados
//            return INSTANCE ?: synchronized(this){
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    AppDatabase::class.java,
//                    "bookUp_database" //Nome do banco de dados do App
//                ).fallbackToDestructiveMigration().build()//Se uma atualização corromper os dados, o banco de dados será destruído e recriado
//                INSTANCE = instance
//                //Retorna instancia criadau
//                //return instance (mesma coisa)
//                instance
//
//            }
//        }
//    }
//
//}