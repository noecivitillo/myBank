package todo1.test.bank.util

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.LiveData


/*
Kotlin extensions
 */

fun ViewGroup.inflate(layoutRes: Int): View = LayoutInflater.from(context).inflate(layoutRes, this, false)

fun EditText.doAfterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterTextChanged.invoke(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

    })
}

fun <T> LiveData<T>.observeOnce(onChangeHandler: (T) -> Unit) {
    val observer = TestObserverUtil(handler = onChangeHandler)
    observe(observer, observer)
}