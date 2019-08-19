package todo1.test.bank

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import todo1.test.bank.data.local.AccountDao
import todo1.test.bank.data.local.AppDatabase
import todo1.test.bank.data.local.UserDao
import todo1.test.bank.model.Account
import todo1.test.bank.model.User

/*
Room database test
 */

@RunWith(AndroidJUnit4::class)
class LocalDatabaseTest {
    private lateinit var accountDao: AccountDao
    private lateinit var userDao: UserDao
    private lateinit var db: AppDatabase

    @Before
    fun initDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()

        userDao = db.userDao()
        accountDao = db.accountDao()
    }

    @Test
    @Throws(Exception::class)
    fun addNewUser() {
        val user = User(1, "maria")
        userDao.insertUser(user)

        val amountUsers = userDao.getAllUsers()
        assertEquals(amountUsers.size, 1)
    }

    @Test
    @Throws(Exception::class)
    fun insertAccounts() {
        val account = Account("Caja de Ahorro", "247-456123789", "1500", "U$", 1)
        accountDao.insertDefaultAccounts(listOf(account))

        val accounts = accountDao.getAccounts()
        assertEquals(accounts.size, 1)
    }

}