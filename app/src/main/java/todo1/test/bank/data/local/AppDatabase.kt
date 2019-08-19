package todo1.test.bank.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import todo1.test.bank.model.Account
import todo1.test.bank.model.User

@Database(entities = [User::class, Account::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun accountDao(): AccountDao
}