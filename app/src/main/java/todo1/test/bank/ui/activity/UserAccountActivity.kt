package todo1.test.bank.ui.activity


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_user_account.*
import kotlinx.android.synthetic.main.toolbar.*
import todo1.test.bank.R
import todo1.test.bank.model.GpsCoordinates
import todo1.test.bank.model.User
import todo1.test.bank.ui.activity.vm.UserAccountViewModel
import todo1.test.bank.ui.adapter.AccountAdapter
import todo1.test.bank.ui.fragment.WeatherDialogFragment
import todo1.test.bank.util.LocationProvider
import javax.inject.Inject

class UserAccountActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: UserAccountViewModel

    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_account)

        setSupportActionBar(toolbar)
        configureToolbar()

        viewModel = ViewModelProviders.of(this, viewModelFactory)[UserAccountViewModel::class.java]
        navigation_user.setNavigationItemSelectedListener(this)

        //Get user from loginActivity
        user = intent.getParcelableExtra("user")
        viewModel.setUserId(user.id)

        val layoutManager = LinearLayoutManager(this)
        recycler.layoutManager = layoutManager
        recycler.setHasFixedSize(true)

        val accountAdapter = AccountAdapter()
        recycler.adapter = accountAdapter

        //Observe accounts and fill adapter
        viewModel.accounts.observe(this, Observer {
            accountAdapter.setData(it)
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                drawer_layout.openDrawer(GravityCompat.START)
            }
        }
        return true
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.transfers -> {
                val intent = Intent(this, TransfersActivity::class.java)
                intent.putExtra("user", user)
                startActivity(intent)
            }
            R.id.weather -> {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSION_GPS)
                } else {
                    initWeatherDialogFragment()
                }

            }
        }
        drawer_layout.closeDrawers()
        return false
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_PERMISSION_GPS) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initWeatherDialogFragment()
            }
        }
    }

    override fun onBackPressed() {
        initLogoutDialog()
    }

    private fun initWeatherDialogFragment() {
        //Get location using android api
        var location: GpsCoordinates? = null
        LocationProvider.requestSingleUpdate(this, object : LocationProvider.LocationCallback {
            override fun onNewLocationAvailable(coordinates: GpsCoordinates) {
                location = coordinates
            }
        })
        //Init weatherFragment
        val bundle = Bundle()
        bundle.apply { putParcelable("coordinates", location) }
        val fragment = WeatherDialogFragment()
        fragment.arguments = bundle
        fragment.show(supportFragmentManager, "WeatherDialogFragment")

    }

    private fun initLogoutDialog() {
        val alertBuilder = AlertDialog.Builder(ContextThemeWrapper(this, R.style.DialogTheme))

        alertBuilder.setTitle(getString(R.string.logout_alert_title))
        alertBuilder.setMessage(R.string.logout_alert_message)
        alertBuilder.setNegativeButton(getString(R.string.alert_cancel)) { dialog, which ->
            dialog.cancel()
        }
        alertBuilder.setPositiveButton(getString(R.string.alert_yes)) { dialog, which ->
            finish()
        }
        val alert = alertBuilder.create()
        alert.show()
    }

    private fun configureToolbar() {
        supportActionBar.apply {
            supportActionBar?.title = resources.getString(R.string.app_name)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu_24dp)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    companion object {
        const val REQUEST_PERMISSION_GPS = 2
    }
}