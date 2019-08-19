package todo1.test.bank.data.api

import android.app.Application
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import todo1.test.bank.R
import javax.inject.Singleton


@Module
class ApiModule {
    @Provides
    @Singleton
    fun provideRestAdapter(application: Application): Retrofit {
        val builder = Retrofit.Builder()
        builder.baseUrl(application.getString(R.string.weather_endpoint))
                .addConverterFactory(GsonConverterFactory.create())
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideWeatherApiService(restAdapter: Retrofit): WeatherApiService {
        return restAdapter.create(WeatherApiService::class.java)
    }
}