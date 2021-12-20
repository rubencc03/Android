package com.rucech.listacompra.ui

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rucech.listacompra.adapters.TaskAdapter
import com.rucech.listacompra.database.entities.TaskEntity
import com.rucech.listacompra.databinding.ActivityMainBinding
import com.rucech.listacompra.viewmodel.TaskViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var recyclerView: RecyclerView
    var tasks: MutableList<TaskEntity> = mutableListOf()

    private lateinit var taskViewModel:TaskViewModel

    lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).also {
            binding = it
        }.root)



        taskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]

        taskViewModel.getAllTasks()
        taskViewModel.getSuma()

        taskViewModel.taskListLD.observe(this){
            tasks.clear()
            tasks.addAll(it)
            recyclerView.adapter?.notifyDataSetChanged()
        }
        taskViewModel.updateTaskLD.observe(this){ taskUpdated ->
            if(taskUpdated == null){
                showMessage("Error updating task")
            }
        }

        //Observamos el cambio de la variable mutable referida al precio total de la compra y modificamos
        taskViewModel.sumarTotal.observe(this){
            binding.tvCompraTotal.text=it.toString()
        }
        //Al borrar
        taskViewModel.deleteTaskLD.observe(this){ id ->
            println("Parte3")

            CoroutineScope(Dispatchers.IO).launch {
                //Actualizamos precio
                taskViewModel.getSuma()

            }

            if(id != -1){
                val task = tasks.filter {
                    it.id == id

                }[0]
                val pos = tasks.indexOf(task)
                tasks.removeAt(pos)
                recyclerView.adapter?.notifyItemRemoved(pos)
            }else{
                showMessage("Error deleting task")
            }
        }
        //Al insertar un nuevo producto
        taskViewModel.insertTaskLD.observe(this){
            tasks.add(it)
            recyclerView.adapter?.notifyItemInserted(tasks.size)


        val precioTotal = it.precio * it.cantidad
        val precioQueYaHay = binding.tvCompraTotal.text.toString().replace("€","").toDouble()
            //Actualizamos precio que se ve
            binding.tvCompraTotal.text=String.format("%.2f", precioTotal+precioQueYaHay).toString() +"€"

        CoroutineScope(Dispatchers.IO).launch {
            //Actualizamos el precio de la BD
            taskViewModel.getSuma()

        }
        }

        //Boton añadir
        binding.btnAddTask.setOnClickListener {
            var buleano:Boolean = true
            if (binding.etPrecio.text.contains(".")){
                //Realizamos un split para coger los decimales
            val strs = binding.etPrecio.text.toString().split(".").toTypedArray()
                //Si la longitud de despues del punto es > 2
            if (strs[1].length > 2) {
                //Muestra el mensaje
                showMessage("Oye ,que maximo son 2 decimales")
                buleano=false

            }
            }
            //Lo mismo que lo anterior pero con el nombre
             if (binding.etTask.text.toString().length > 10) {
                showMessage("Oye ,que maximo son 10 caracteres")
                buleano=false

            }
                //Si no introduce nombre
                if (binding.etTask.text.toString().length == 0) {
                    //Mensaje de advertencia
                    showMessage("Oye ,Tienes que escribir un nombre")
                    buleano=false
            }


                //Si la cantidad esta vacia
                if (binding.etCantidad.text.toString().equals("")) {
                    //mensaje
                    showMessage("Oye ,que no has puesto cantidad")
                    buleano=false
                    //Si no es un int
                }else if(isInteger(binding.etCantidad.text.toString())){}else{
                    //mensaje
                    showMessage("Oye ,que has puesto una LETRA en CANTIDAD burro")
                    buleano=false}
            //Si el precio esta vacio
            if (binding.etPrecio.text.toString().equals("")) {
                //mensaje
                showMessage("Oye ,que no has puesto ningun precio")
                buleano=false
            }

            //Si te has fijado,si daba algun fallo poniamos a buleano false, para que aquí no creara la tarea
            if(buleano==true){ addTask()}


        }

        setUpRecyclerView()
    }

    private fun showMessage(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }


    private fun addTask() {
        taskViewModel.add(binding.etTask.text.toString(),binding.etCantidad.text.toString().toInt(),binding.etPrecio.text.toString().toDouble(),binding.spinner.selectedItem.toString())

        clearFocus()
        hideKeyboard()

    }

    fun setUpRecyclerView() {
        adapter = TaskAdapter(tasks, { taskEntity ->  updateTask(taskEntity) }, { taskEntity -> deleteTask(taskEntity) })
        recyclerView = binding.rvTask
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun updateTask(taskEntity: TaskEntity) {
        taskViewModel.update(taskEntity)
    }

    private fun deleteTask(taskEntity: TaskEntity) {
        taskViewModel.delete(taskEntity)

    }

    private fun clearFocus(){
        binding.etTask.setText("")
    }

    private fun Context.hideKeyboard() {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    fun isInteger(str: String?) = str?.toIntOrNull()?.let { true } ?: false

}