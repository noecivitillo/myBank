package todo1.test.bank.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import todo1.test.bank.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Query("select * from User")
    fun getAllUsers(): List<User>

    @Query("select * from User where name = :name")
    fun getUserByName(name: String): User
}