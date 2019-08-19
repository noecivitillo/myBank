package todo1.test.bank


import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import todo1.test.bank.data.UserRepository
import todo1.test.bank.data.local.AppDatabase
import todo1.test.bank.util.observeOnce


@RunWith(AndroidJUnit4::class)
class UserRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: UserRepository

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()

        repository = UserRepository(db.userDao(), db.accountDao())
    }
    @Test
    fun createUser() {
        val user = repository.saveUser("new user")

        assertEquals("new user", user.name)
    }

    @Test
    fun insertUserAndGetAccounts() {
        val user = repository.saveUser("new user")
        repository.getUserAccountsById(user.id).observeOnce {
            assertEquals(2, it.size)
        }
    }
}
