package todo1.test.bank.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class GpsCoordinates(val lat: String, val lgn: String) : Parcelable