package com.rucech.listacompra.database.entities
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_entity")
data class TaskEntity (
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0,
    var name:String = "",
    var precio : Double=0.00,
    var cantidad :Int =0,
    var imagen :String="",
    var isDone:Boolean = false
)