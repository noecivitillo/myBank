package todo1.test.bank.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Account(val type: String, val number: String, val balance: String, val currency: String, val userId: Long) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}