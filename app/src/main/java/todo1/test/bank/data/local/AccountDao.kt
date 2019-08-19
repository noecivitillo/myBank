package todo1.test.bank.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import todo1.test.bank.model.Account


@Dao
interface AccountDao {
    @Query("select * from Account where userId = :id ")
    fun getAllAccounts(id: Long): LiveData<List<Account>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDefaultAccounts(list: List<Account>)

    @Query("select * from Account")
    fun getAccounts(): List<Account>
}