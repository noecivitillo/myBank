package todo1.test.bank.ui.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import todo1.test.bank.data.WeatherRepository
import todo1.test.bank.data.api.response.WeatherResponse
import todo1.test.bank.model.GpsCoordinates
import todo1.test.bank.model.Weather
import javax.inject.Inject
import kotlin.math.roundToInt

class WeatherViewModel @Inject constructor(repository: WeatherRepository) : ViewModel() {

    private val coordinates = MutableLiveData<GpsCoordinates>()

    private val weatherResponse: LiveData<WeatherResponse> = Transformations.switchMap(coordinates) {
        repository.getCurrentWeather(it.lat.toDouble(), it.lgn.toDouble())
    }

    val weather: LiveData<Weather> = Transformations.map(weatherResponse) {
        it.currently?.let {
            Weather(
                it.temperature?.roundToInt(),
                it.precipProbability?.roundToInt(),
                it.humidity?.roundToInt(),
                it.summary
            )
        }
    }

    fun setCoordinates(values: GpsCoordinates) {
        coordinates.postValue(values)
    }

}