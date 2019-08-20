package todo1.test.bank.ui.activity.vm

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import todo1.test.bank.data.UserRepository
import todo1.test.bank.model.Account
import javax.inject.Inject


class TransfersViewModel @Inject constructor(repository: UserRepository) : ViewModel() {

    var amountToTransfer = ""
    private val accountSelected = MutableLiveData<Account>()
    private val userId = MutableLiveData<Long>()
    val accounts: LiveData<List<Account>> = Transformations.switchMap(userId) {
        repository.getUserAccountsById(it)
    }

    fun setUserId(value: Long) {
        userId.postValue(value)
    }

    fun setAccountSelected(value: Account) {
        accountSelected.postValue(value)
    }

    fun isValidAmountToTransfer(): Boolean {
        val amount = accountSelected.value?.balance?.toInt()
        amount?.let {
            return when {
                amountToTransfer.isEmpty() -> false
                amount >= amountToTransfer.toInt() && amountToTransfer.isNotEmpty() -> true
                else -> false
            }
        }
        return false
    }

    fun generateQRBitmap(): Bitmap? {
        val contentToQr = "$accountSelected , $amountToTransfer"

        var bitmap: Bitmap? = null
        val width = 256
        val height = 256

        val writer = QRCodeWriter()
        try {
            val bitMatrix = writer.encode(contentToQr, BarcodeFormat.QR_CODE, width, height)
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap?.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
                }

            }
        } catch (e: WriterException) {
            Log.i("WriterException", "Exception QRCodeWriter")
            e.printStackTrace()
        }

        return bitmap
    }
}