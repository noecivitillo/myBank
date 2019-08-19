package todo1.test.bank.data

import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import todo1.test.bank.data.api.WeatherApiService
import todo1.test.bank.data.api.response.WeatherResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(private val apiService: WeatherApiService) {

    fun getCurrentWeather(latitude: Double, longitude: Double): MutableLiveData<WeatherResponse> {
        val weather = MutableLiveData<WeatherResponse>()
        apiService.getWeatherResponse(latitude, longitude).enqueue(object : Callback<WeatherResponse> {
            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                weather.value = null
            }

            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    weather.value = response.body()
                }
            }

        })
        return weather
    }
}