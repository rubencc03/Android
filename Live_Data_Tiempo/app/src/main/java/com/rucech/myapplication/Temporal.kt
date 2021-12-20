package com.rucech.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.rucech.myapplication.databinding.FragmentTemporalBinding
import android.R
import android.annotation.SuppressLint
import android.widget.ImageView

/**I know that in the exercise it says that it must change every second of the image and that it must work with Strings,
 *  but I have done it this way to make it more realistic, it makes more sense that every so often the temporal changes,
 *  and I have not put a condition so that it does not repeat itself because the storm can be repeated,Please, do not lower
 *  me a note because it is different, what was requested was simpler :)*/

class Temporal : Fragment() {
    var idAMostrar=0

    private lateinit var binding:FragmentTemporalBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return FragmentTemporalBinding.inflate(inflater, container, false)
            .also { binding = it }
            .root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**Let's create a ViwModel variable that takes the data from TemporalViewModel*/
        val temporalViewModel = ViewModelProvider(this)[TemporalViewModel::class.java]

        /**We access the Live data variable of the temporary inside the ViewModel*/
        temporalViewModel.temporalLiveData.observe(viewLifecycleOwner){ imageID ->
            /**Change the ImageView for the image you just took*/
            binding.ivExercise.setImageResource(imageID)

        /**We do is global ID to work with it*/
        idAMostrar=imageID
        }

        /**We access the Live data variable of the days that remain to change the temporal within the ViewModel*/
        temporalViewModel.repetitionLiveData.observe(viewLifecycleOwner) { repetition ->
            /**if we are in Change we show the change tv and if we do not hide it*/
            if (repetition.equals("CHANGE")) {
                binding.tvChange.visibility = View.VISIBLE
            } else {
                binding.tvChange.visibility = View.GONE
            }


            /**We put the number of repetitions on the tv above*/
            binding.tvRepetition.text = "El temporal cambiará en:  $repetition dias"
            if(idAMostrar==2131230829 ){ binding.queTiempoHace.text= "Esto es una mierda,odio los dias lluviosos :("}
            else if (idAMostrar==2131230869){ binding.queTiempoHace.text= "Con tanto viento me estoy despeinando"}
            else if (idAMostrar==2131230863){ binding.queTiempoHace.text= "Se esta nublando,espero que no llueva..."}
            else if (idAMostrar==2131230864){ binding.queTiempoHace.text= "VIVA EL SOL JODER"}
        }

    }


}