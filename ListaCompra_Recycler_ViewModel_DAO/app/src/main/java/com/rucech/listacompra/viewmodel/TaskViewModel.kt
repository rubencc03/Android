package com.rucech.listacompra.viewmodel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.rucech.listacompra.database.MyDao
import com.rucech.listacompra.database.TasksDatabase
import com.rucech.listacompra.database.entities.TaskEntity

class TaskViewModel(application: Application): AndroidViewModel(application) {
    val context = application

    var myDao: MyDao = TasksDatabase.getInstance(context)

    val taskListLD:MutableLiveData<MutableList<TaskEntity>> = MutableLiveData()
    val updateTaskLD:MutableLiveData<TaskEntity?> = MutableLiveData()
    val deleteTaskLD:MutableLiveData<Int> = MutableLiveData()
    val insertTaskLD:MutableLiveData<TaskEntity> = MutableLiveData()
    val sumarTotal:MutableLiveData<String> = MutableLiveData<String>()


    fun getAllTasks(){
        CoroutineScope(Dispatchers.IO).launch {
            taskListLD.postValue(myDao.getAllTasks())
        }

    }
    fun add(task:String,cantidad:Int,precio:Double,sniper:String) {
        CoroutineScope(Dispatchers.IO).launch {
            val id = myDao.addTask(TaskEntity(name = task , precio = precio, cantidad = cantidad, imagen = sniper))
            val recoveryTask = myDao.getTaskById(id)
            insertTaskLD.postValue(recoveryTask)
        }
    }

    fun delete(task:TaskEntity){
        CoroutineScope(Dispatchers.IO).launch {
            println("Parte2")

            var xd2 =myDao.getTotalPrecio()
            println("Precio total antes de resta = $xd2")
            var xd= (xd2-task.precio).toString()
            println("Precio total despues de resta = $xd")

            sumarTotal.postValue(xd  +"€")
            myDao.getTotalPrecio()
            println("Precio total despues de postvalue = $xd")

            if(task.cantidad==0) {
                val res = myDao.deleteTask(task)
                if (res > 0)
                    deleteTaskLD.postValue(task.id)
                else {
                    deleteTaskLD.postValue(-1)
                }

            }
            println("Precio total despues de mierda = $xd")




        }
    }

    fun update(task: TaskEntity){
        CoroutineScope(Dispatchers.IO).launch {
            task.isDone = !task.isDone
            val res = myDao.updateTask(task)
            if(res>0)
                updateTaskLD.postValue(task)
            else
                updateTaskLD.postValue(null)
        }
    }

    fun getSuma(){

        CoroutineScope(Dispatchers.IO).launch {
            sumarTotal.postValue(myDao.getTotalPrecio().toString() +"€")
        }
    }
}