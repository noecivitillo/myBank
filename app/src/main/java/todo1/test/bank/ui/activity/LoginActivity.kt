package todo1.test.bank.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_login.*
import todo1.test.bank.R
import todo1.test.bank.model.User
import todo1.test.bank.ui.activity.vm.LoginViewModel
import todo1.test.bank.util.doAfterTextChanged
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)

        //Get text from fields using extension
        editText_user.doAfterTextChanged { text -> viewModel.userName = text }
        editText_pass.doAfterTextChanged { text -> viewModel.password = text }

        btn_login.setOnClickListener {
            val user = viewModel.initUser()
            if (user != null) {
                initUserActivity(user)
                Toast.makeText(
                    this,
                    resources.getString(R.string.login_toast_successful, user.name),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(this, resources.getString(R.string.login_toast_error), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        editText_user.text.clear()
        editText_pass.text.clear()
    }

    private fun initUserActivity(user: User) {
        val intent = Intent(this, UserAccountActivity::class.java)
        intent.putExtra("user", user)
        startActivity(intent)
    }
}
