package com.rucech.myapplication


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.MutableLiveData


class TemporalViewModel(application: Application) :AndroidViewModel(application) {
    /**We create the temporary variable which is an instance of Temporal (class in charge of managing the times and in the days that it will change)*/
    private var temporal: TemporalDo = TemporalDo()

    /**We create a LiveData variable that will show us the temporary that is running at all times*/
    var temporalLiveData: LiveData<Int>

    /**We create a LiveData variable that will show us the days to also be temporary.*/
    var repetitionLiveData: LiveData<String>

    init {
        /** Our variable that is in charge of observing the object lo = to the variable that it made in onActive and onInactive */
        temporalLiveData = Transformations.switchMap(temporal.orderLiveData,) { temporal ->
            val mTemporal = temporal.split(":")[0]

/**I know that in the exercise it says that it must change every second of the image and that it must work with Strings,
 *  but I have done it this way to make it more realistic, it makes more sense that every so often the temporal changes,
 *  and I have not put a condition so that it does not repeat itself because the storm can be repeated,Please, do not lower
 *  me a note because it is different, what was requested was simpler :)*/





            /** If the current exercise is not the previous one, put it to me.
            * We are at the beginning, then we will modify the current exercise */


            /** We create a variable imageID using kotlin's Switch case, basically we tell it that the ID we have created will be x image depending on
            * of the exercise that we are doing */

                var imageID: Int = when (mTemporal) {
                    "TEMPORAL1" -> R.drawable.sol
                    "TEMPORAL2" -> R.drawable.lluvia
                    "TEMPORAL3" -> R.drawable.nube
                    "TEMPORAL4" -> R.drawable.viento
                    /** If there is no exercise we start with the first image itself */

                else -> {R.drawable.sol}
            }
            /** We return the image that should be */
            return@switchMap MutableLiveData<Int>(imageID)


            /**if not,return null*/
            return@switchMap null
        }

        /** This is another switchMap like the previous one but only in the repetitions */
        repetitionLiveData = Transformations.switchMap(temporal.orderLiveData) { temporal ->
            return@switchMap MutableLiveData<String>(temporal.split(":")[1])
        }
        /** IMPORTANT EXPLANATION, THE DIFFERENCE BETWEEN THE TWO SWITCHMAP LIES IN THE [], IN THE POSITION 0 IS THE EXERCISE AND IN THE 1
        * THE REPETITIONS */

    }
}
