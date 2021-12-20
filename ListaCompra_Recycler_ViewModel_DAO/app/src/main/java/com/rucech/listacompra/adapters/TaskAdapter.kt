package com.rucech.listacompra.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rucech.listacompra.R
import com.rucech.listacompra.database.entities.TaskEntity

class TaskAdapter(
    val tasks: List<TaskEntity>,
    val checkTask: (TaskEntity) -> Unit,
    val deleteTask: (TaskEntity) -> Unit) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_task, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = tasks[position]
        holder.bind(item, checkTask, deleteTask)
    }


    override fun getItemCount() = tasks.size


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvTask = view.findViewById<TextView>(R.id.tvTask)
        val tvPrecio =view.findViewById<TextView>(R.id.tvPrecio)
        val tvCantidad =view.findViewById<TextView>(R.id.tvCantidad)
        val tvTotal = view.findViewById<TextView>(R.id.tvTotal)
        val buton = view.findViewById<ImageButton>(R.id.imgBtn)
        val imagen = view.findViewById<ImageView>(R.id.iv1)

        fun bind(task: TaskEntity, checkTask: (TaskEntity) -> Unit, deleteTask: (TaskEntity) -> Unit) {
            //Asignamos los datos del item que se repite
            tvTask.text = task.name
            tvPrecio.text= task.precio.toString()
            tvCantidad.text = task.cantidad.toString()
            tvTotal.text=   String.format("%.2f", task.precio*task.cantidad).toString() +"€" //Le damos formato al precio total de dos decimales solo

            //Cogemos los datos seleccionados en el Spiner y dependiendo de este asignamos una imagen
            if(task.imagen.equals("Carne")){imagen.setImageResource(R.drawable.imgcarne)}
            else if (task.imagen.equals("Verdura")){imagen.setImageResource(R.drawable.imgverdura)}
            else if (task.imagen.equals("Congelado")){imagen.setImageResource(R.drawable.imgcongelado)}
            else if (task.imagen.equals("Muebles")){imagen.setImageResource(R.drawable.imgmuebles)}
            else if (task.imagen.equals("Medicinas")){imagen.setImageResource(R.drawable.imgmedicinas)}
            else if (task.imagen.equals("Herramientas")){imagen.setImageResource(R.drawable.imherramientas)}







            buton.setOnClickListener {
                println("SIGUIENTE")
                println("Parte1")
                println("Cantidad antes de resta : ${task.cantidad}")

                task.cantidad--
                println("Cantidad despues de resta : ${task.cantidad}")
                tvCantidad.text=task.cantidad.toString()

                tvTotal.text=   String.format("%.2f", task.precio*task.cantidad).toString() +"€"

                deleteTask(task) }
        }
    }
}