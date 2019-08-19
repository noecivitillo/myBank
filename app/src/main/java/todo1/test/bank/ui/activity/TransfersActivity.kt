package todo1.test.bank.ui.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_transfers.*
import todo1.test.bank.R
import todo1.test.bank.model.User
import todo1.test.bank.ui.activity.vm.TransfersViewModel
import todo1.test.bank.util.doAfterTextChanged
import javax.inject.Inject


class TransfersActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: TransfersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfers)

        viewModel = ViewModelProviders.of(this, viewModelFactory)[TransfersViewModel::class.java]

        //Get user from userActivity and pass id to viewModel
        val user = intent.getParcelableExtra<User>("user")
        viewModel.setUserId(user.id)

        spinner_accounts.onItemSelectedListener = this
        editText_amount.doAfterTextChanged { text -> viewModel.amountToTransfer = text }

        //Observe accounts and fill spinner
        viewModel.spinnerAccounts.observe(this, Observer {
            val adapterSpinner = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, it)
            spinner_accounts.adapter = adapterSpinner
        })

        //Init spinner of destination accounts with no data
        initAccountDestinationSpinner(resources.getString(R.string.spinner_dest_account))

        //Handle click to generate qr code
        btn_generate_qr.setOnClickListener {
            val drawable = BitmapDrawable(resources, viewModel.generateQRBitmap())
            image_qr.visibility = View.VISIBLE
            image_qr.setImageDrawable(drawable)
        }

        //Handle click to scan qr code - check manifest permission first
        btn_scan_qr.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_PERMISSION_CAMERA)
            } else {
                initQrScannerActivity()
            }
        }
    }

    override fun onItemSelected(p0: AdapterView<*>, p1: View?, p2: Int, p3: Long) {
        viewModel.accountSelected = p0.getItemAtPosition(p2).toString()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == DESTINATION_ACCOUNT && resultCode == Activity.RESULT_OK) {

            val destinationAccount = data?.getStringExtra("DESTINATION_ACCOUNT")
            initAccountDestinationSpinner(destinationAccount)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_PERMISSION_CAMERA) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initQrScannerActivity()
            }
        }
    }

    private fun initQrScannerActivity() {
        val intent = Intent(this, QrScannerActivity::class.java)
        startActivityForResult(intent, DESTINATION_ACCOUNT)
    }

    private fun initAccountDestinationSpinner(values: String?) {
        val adapterSpinner = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listOf(values))
        spinner_dest_account.adapter = adapterSpinner
    }

    companion object {
        const val REQUEST_PERMISSION_CAMERA = 1
        const val DESTINATION_ACCOUNT = 2
    }
}