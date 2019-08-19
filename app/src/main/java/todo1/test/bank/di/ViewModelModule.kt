package todo1.test.bank.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import todo1.test.bank.ui.activity.vm.LoginViewModel
import todo1.test.bank.ui.activity.vm.TransfersViewModel
import todo1.test.bank.ui.activity.vm.UserAccountViewModel
import todo1.test.bank.ui.fragment.WeatherViewModel

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserAccountViewModel::class)
    abstract fun bindUserAccountViewModel(viewModel: UserAccountViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TransfersViewModel::class)
    abstract fun bindTransfersViewModel(viewModel: TransfersViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WeatherViewModel::class)
    abstract fun bindWeatherViewModel(viewModel: WeatherViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}