package todo1.test.bank.data.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import todo1.test.bank.data.api.response.WeatherResponse

interface WeatherApiService {
    @GET("{latitude},{longitude}?units=si")
    fun getWeatherResponse(@Path("latitude") latitude: Double, @Path("longitude") longitude: Double): Call<WeatherResponse>
}