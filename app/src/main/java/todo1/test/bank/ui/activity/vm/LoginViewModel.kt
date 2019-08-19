package todo1.test.bank.ui.activity.vm

import androidx.lifecycle.ViewModel
import todo1.test.bank.data.UserRepository
import todo1.test.bank.model.User
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {
    var userName = ""
    var password = ""
    private val defaultPass = "123456"

    fun initUser(): User? =
        when {
            password != defaultPass -> null
            password.isEmpty() && userName.isEmpty() -> null
            else -> repository.saveUser(userName)
        }
}