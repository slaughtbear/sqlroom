package mx.edu.utch.sqlroom.Model.SQL

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import java.sql.SQLClientInfoException
import java.sql.SQLException

@Dao
interface IEstudianteDao {
    @Query("select * from estudiante")
    fun getAll():List<Estudiante>

    @Query("select * from estudiante where mtr like :mtr limit 1")
    @Throws(SQLClientInfoException::class)
    fun getEstudiante(mtr:Int):Estudiante?

    @Insert(onConflict = OnConflictStrategy.IGNORE) //En caso de haber un error ignorarlo
    fun addEstudiante(estudiante: Estudiante)

    @Delete()
    fun delEstudiante(estudiante: Estudiante)

    @Query("Delete from estudiante")
    fun deleteAll()

    @Update()
    fun updateEstudiante(estudiante: Estudiante)
}