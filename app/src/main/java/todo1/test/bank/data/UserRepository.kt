package todo1.test.bank.data


import androidx.lifecycle.LiveData
import todo1.test.bank.data.local.AccountDao
import todo1.test.bank.data.local.UserDao
import todo1.test.bank.model.Account
import todo1.test.bank.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val userDao: UserDao, private val accountDao: AccountDao) {

    fun saveUser(userName: String): User {
        val user = User(System.currentTimeMillis(), userName)
        userDao.insertUser(user)
        saveUserAccounts(user.id)
        return user
    }

    private fun saveUserAccounts(userId: Long) {
        val account1 = Account("Caja de Ahorro", "247-456123789", "1500", "U$", userId)
        val account2 = Account("Cuenta corriente", "247-567891234", "10000", "COL$", userId)
        accountDao.insertDefaultAccounts(listOf(account1, account2))
    }

    fun getUserAccountsById(id: Long): LiveData<List<Account>> = accountDao.getAllAccounts(id)

}