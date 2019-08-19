package todo1.test.bank.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.Result
import kotlinx.android.synthetic.main.activity_scanner.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import todo1.test.bank.R


class QrScannerActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    private lateinit var mScannerView: ZXingScannerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)

        mScannerView = ZXingScannerView(this)
        content_frame.addView(mScannerView)
    }

    override fun onResume() {
        super.onResume()
        mScannerView.setResultHandler(this)
        mScannerView.startCamera()
    }

    override fun onPause() {
        super.onPause()
        mScannerView.stopCamera()
    }

    override fun handleResult(rawResult: Result) {

        if (rawResult.text.isNotEmpty()) {
            val intent = Intent()
            intent.putExtra("DESTINATION_ACCOUNT", rawResult.text)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        val handler = Handler()
        handler.postDelayed({ mScannerView.resumeCameraPreview(this@QrScannerActivity) }, 2000)
    }
}