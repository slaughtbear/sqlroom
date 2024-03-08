package mx.edu.utch.sqlroom.Model.SQL

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Estudiante::class], version = 1)
abstract class AppDB : RoomDatabase() {
    abstract fun estudianteDao():IEstudianteDao

    companion object{
        @Volatile
        private var INSTANCE: AppDB?= null

        fun getDB(context:Context):AppDB{
            val temp :AppDB? = INSTANCE
            if (temp != null) {
                return temp
            }
            //Si es nulo es necesario crear conexion
            //la unica forma de crear la conexion es a traves de un thread (corutinas)
            synchronized(this){
                val instancia = Room.databaseBuilder(
                    context.applicationContext,
                    AppDB::class.java,"estudiante"
                ).build()
                INSTANCE = instancia
                return instancia
            }
        }
    } //end companion
} //end class