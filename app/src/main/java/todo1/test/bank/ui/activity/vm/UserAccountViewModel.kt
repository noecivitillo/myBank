package todo1.test.bank.ui.activity.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import todo1.test.bank.data.UserRepository
import todo1.test.bank.model.Account
import javax.inject.Inject

class UserAccountViewModel @Inject constructor(repository: UserRepository) : ViewModel() {

    private val userId = MutableLiveData<Long>()
    val accounts: LiveData<List<Account>> = Transformations.switchMap(userId) {
        repository.getUserAccountsById(it)
    }

    fun setUserId(value: Long) {
        userId.postValue(value)
    }

}