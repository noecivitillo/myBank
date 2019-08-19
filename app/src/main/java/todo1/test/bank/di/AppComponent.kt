package todo1.test.bank.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import todo1.test.bank.MyBankApplication
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, AndroidInjectionModule::class, AndroidSupportInjectionModule::class, ActivityBuilder::class])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application (application: Application) : Builder
        fun build(): AppComponent
    }
    fun inject(app: MyBankApplication)
}