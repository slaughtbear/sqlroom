package mx.edu.utch.sqlroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mx.edu.utch.sqlroom.Model.SQL.AppDB
import mx.edu.utch.sqlroom.Model.SQL.Estudiante
import mx.edu.utch.sqlroom.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnClickListener{
    private var database:AppDB? = null
    private var binding:ActivityMainBinding?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)


        database = AppDB.getDB(this)

        binding!!.btnReadData.setOnClickListener(this)
        binding!!.btnDeleteAll.setOnClickListener(this)
        binding!!.btnUpdate.setOnClickListener(this)
        binding!!.btnWriteData.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when(v.id){
                R.id.btn_write_data->{
                    writeData()
                }
                R.id.btnReadData->{
                    readData()
                }
            }
        }
    }

    private fun readData() {
        val mat:String = binding!!.etRollNoRead.text.toString()
        if (mat.isEmpty()){
            Toast.makeText(this,"Datos invalidos", Toast.LENGTH_SHORT).show()
            return
        }
        var estudiante:Estudiante
        //Para hacer la consulta se debe colocar en un hint(cortina)
        GlobalScope.launch{
            try {
                estudiante = database!!.estudianteDao().getEstudiante(mat.toInt())!!

                // Se interrumple el hilo principal
                withContext(Dispatchers.Main){
                    binding!!.tvFirstName.text = estudiante.nombre
                    binding!!.tvLastName.text = estudiante.apellido
                    binding!!.tvRollNo.text = estudiante.mtr.toString()
                }
            } catch (e:Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(this@MainActivity,"Datos invalidos", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun writeData() {
        val nombre:String = binding!!.etFirstName.toString()
        val apellido:String = binding!!.etLastName.toString()
        val mat:String = binding!!.etRollNo.text.toString()
        if (nombre.isEmpty() || apellido.isEmpty() || mat.isEmpty()){
            Toast.makeText(this,"Datos invalidos", Toast.LENGTH_SHORT).show()
            return
        }
        //Si los datos son nulos, los guardamos en la DB
        val estudiante:Estudiante = Estudiante(null, nombre,apellido,mat.toInt())
        //Ejecutamos un subproceso corutina thread
        //Para insertar en la base de datos
        GlobalScope.launch(Dispatchers.IO){
            database!!.estudianteDao().addEstudiante(estudiante)
        }
        binding!!.etFirstName.text.clear()
        binding!!.etLastName.text.clear()
        binding!!.etRollNo.text.clear()

    }
}

