package mx.edu.utch.sqlroom.Model.SQL

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "estudiante") //Le da un nombre a una tabla en la base de datos
data class Estudiante(
    @PrimaryKey(autoGenerate = true) val id:Int?, //Declaracion de la llave primaria
    @ColumnInfo(name = "nombre") val nombre:String?, // Declaracion del campo nombre
    @ColumnInfo(name = "apellido") val apellido:String?, // Declaracion del campo apellido
    @ColumnInfo(name = "mtr", index = true) val mtr:Int?, // Declaracion del campo matricula, generando un indice
)
