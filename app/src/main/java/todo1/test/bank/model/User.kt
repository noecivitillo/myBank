package todo1.test.bank.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class User(@PrimaryKey val id: Long, val name: String) : Parcelable