package todo1.test.bank


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import todo1.test.bank.data.WeatherRepository
import todo1.test.bank.data.api.WeatherApiService
import todo1.test.bank.util.observeOnce

/*
Test api call using Mockito
 */
@RunWith(MockitoJUnitRunner::class)
class WeatherRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var apiService: WeatherApiService
    private lateinit var repository: WeatherRepository

    @Before
    fun init() {
        repository = WeatherRepository(apiService)
    }

    @Test
    fun getWeatherFromApi() {
        `when`(repository.getCurrentWeather(41.0, 41.2).observeOnce {
            assertEquals(null, it)
        })
    }
}