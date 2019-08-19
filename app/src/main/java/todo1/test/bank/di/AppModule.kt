package todo1.test.bank.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import todo1.test.bank.data.UserRepository
import todo1.test.bank.data.WeatherRepository
import todo1.test.bank.data.api.ApiModule
import todo1.test.bank.data.api.WeatherApiService
import todo1.test.bank.data.local.AccountDao
import todo1.test.bank.data.local.AppDatabase
import todo1.test.bank.data.local.UserDao
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class, ApiModule::class])
class AppModule {
    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application

    @Singleton
    @Provides
    fun provideAppDatabase(context: Context): AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "users-db").allowMainThreadQueries().build()

    @Singleton
    @Provides
    fun provideUserDao(database: AppDatabase) = database.userDao()

    @Singleton
    @Provides
    fun providesAccountDao(database: AppDatabase) = database.accountDao()

    @Singleton
    @Provides
    fun provideUserRepository(userDao: UserDao, accountDao: AccountDao): UserRepository = UserRepository(userDao, accountDao)

    @Singleton
    @Provides
    fun provideWeatherRepository(apiService: WeatherApiService): WeatherRepository = WeatherRepository(apiService)

}