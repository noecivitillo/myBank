package todo1.test.bank

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import todo1.test.bank.di.AppComponent
import todo1.test.bank.di.DaggerAppComponent
import javax.inject.Inject

class MyBankApplication : Application(), HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>


    lateinit var appComponent: AppComponent
        private set


    companion object {
        lateinit var app: MyBankApplication
            private set

    }

    override fun onCreate() {
        super.onCreate()
        app = this
        appComponent = DaggerAppComponent
            .builder()
            .application(this)
            .build()
        appComponent.inject(this)

    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityDispatchingAndroidInjector
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentDispatchingAndroidInjector
    }

}