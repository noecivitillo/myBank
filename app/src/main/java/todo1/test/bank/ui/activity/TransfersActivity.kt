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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_transfers.*
import todo1.test.bank.R
import todo1.test.bank.model.Account
import todo1.test.bank.model.User
import todo1.test.bank.ui.activity.vm.TransfersViewModel
import todo1.test.bank.util.doAfterTextChanged
import javax.inject.Inject


class TransfersActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: TransfersViewModel
    var listAccounts: List<Account> = emptyList()

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
        viewModel.accounts.observe(this, Observer {
            //create list to show in spinner
            val listOfAccounts = it.map { account -> "${account.type} , ${account.number} , ${account.currency}" }
            //fill spinner
            val adapterSpinner = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listOfAccounts)
            spinner_accounts.adapter = adapterSpinner
            //Store accounts in list(only to handle selected items)
            listAccounts = it
        })

        //Init spinner of destination accounts with no data
        initAccountDestinationSpinner(resources.getString(R.string.spinner_dest_account))

        //Handle click to generate qr code
        btn_generate_qr.setOnClickListener {
            if (viewModel.isValidAmountToTransfer()) {
                val drawable = BitmapDrawable(resources, viewModel.generateQRBitmap())
                image_qr.visibility = View.VISIBLE
                image_qr.setImageDrawable(drawable)
            } else {
                image_qr.visibility = View.GONE
                Toast.makeText(this, resources.getString(R.string.transfers_incorrect_amount), Toast.LENGTH_SHORT)
                    .show()
            }
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
        //Get position of selected account
        val account = listAccounts[p2]
        viewModel.setAccountSelected(account)
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