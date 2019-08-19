package todo1.test.bank.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import todo1.test.bank.ui.activity.LoginActivity
import todo1.test.bank.ui.activity.TransfersActivity
import todo1.test.bank.ui.activity.UserAccountActivity
import todo1.test.bank.ui.fragment.WeatherDialogFragment

@Suppress("unused")
@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector
    abstract fun bindUserAccountActivity(): UserAccountActivity

    @ContributesAndroidInjector
    abstract fun bindTransfersActivity(): TransfersActivity

    @ContributesAndroidInjector
    abstract fun bindWeatherDialogFragment(): WeatherDialogFragment
}