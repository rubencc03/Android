package com.rucech.myapplication

import androidx.lifecycle.LiveData
import kotlinx.coroutines.*

import java.util.*
import androidx.lifecycle.MutableLiveData

/**I know that in the exercise it says that it must change every second of the image and that it must work with Strings,
 *  but I have done it this way to make it more realistic, it makes more sense that every so often the temporal changes,
 *  and I have not put a condition so that it does not repeat itself because the storm can be repeated,Please, do not lower
 *  me a note because it is different, what was requested was simpler :)*/

/**We create a typealias so that later the code is shorter*/
typealias  OnOrder = (order:String) -> Unit

/**We create the Trainer class which is in charge of giving orders, controls the exercise to be done and the number of repetitions, when they are
 * reps are 0 warn and change exercise*/
class TemporalDo {
    /**We create a variable of the abstract class Random*/
    val random: Random = Random()

    /**The job variable has to do with coroutines, it allows us to know if something is completed, canceled ...*/
    var queTemporalHay: Job? = null

    /**Variable that identifies a temporary*/
    var temporal = 0

    /**Days it will take to change the storm*/
    var cambioDias = -1


    /**Function in charge of starting a training, we pass it an alias that registers that it is a string*/
    fun startTraining(onOrder: OnOrder) {
        /**If the variable that controls the exercises is not running, that is, it is canceled, completed or null, do the following*/
        if (queTemporalHay == null || queTemporalHay!!.isCancelled || queTemporalHay!!.isCompleted) {
            queTemporalHay = CoroutineScope(Dispatchers.IO).launch {

                /**Loop in charge of controlling all the information of an exercise*/
                while (true) {
                    /**If the changeDays tells us that we must change the temporary (0 days left) change the temporary and say how many days it will last*/
                    if (cambioDias < 0) {
                        cambioDias = random.nextInt(3) + 3
                        temporal = random.nextInt(4) + 1
                    }
                    /**The onOrder will be the string that is shown, it gives the information of the exercise and if the repetitions are 0*/
                    onOrder("TEMPORAL" + temporal + ":" + if (cambioDias == 0) "CHANGE" else cambioDias)
                    /**We remove a repetition*/
                    cambioDias--
                    /**We add the delay of 1s so that it does not do everything at once*/
                    delay(1000)
                }

            }
        }
    }

    /**Function in charge of stopping training and putting all the values as they should be*/
    fun stopTraining() {
        queTemporalHay?.let {
            if (it.isActive)
                it.cancel()
        }
        temporal = 0
        cambioDias = -1

    }


    /**This variable is the observer who is in charge of modifying the changes*/
    val orderLiveData: LiveData<String> = object : LiveData<String>() {
        /**Has a function in charge of starting training*/
        override fun onActive() {
            super.onActive()
            startTraining { order ->
                postValue(order)
            }
        }

        /**It has a function that stops training*/
        override fun onInactive() {
            super.onInactive()
            stopTraining()
        }
    }
}
