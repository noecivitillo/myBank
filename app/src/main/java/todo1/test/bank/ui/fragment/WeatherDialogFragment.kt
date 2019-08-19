package todo1.test.bank.ui.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.AndroidSupportInjection
import todo1.test.bank.R
import todo1.test.bank.databinding.FragmentWeatherBinding
import todo1.test.bank.model.GpsCoordinates
import javax.inject.Inject


class WeatherDialogFragment : DialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: WeatherViewModel
    private lateinit var binding: FragmentWeatherBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(activity), R.layout.fragment_weather,
            null,
            false
        )
        val builder = AlertDialog.Builder(activity).setView(binding.root)
        return builder.create()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        AndroidSupportInjection.inject(this)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(WeatherViewModel::class.java)

        val arguments = arguments
        var coordinates: GpsCoordinates? = null

        if (arguments != null) {
            coordinates = arguments.getParcelable("coordinates")
            coordinates?.let {
                viewModel.setCoordinates(it)
            }
        }

        viewModel.weather.observe(this, Observer {
            if (it != null) {
                binding.temperature.text = resources.getString(R.string.weather_temperature, it.temperature.toString())
                binding.probPrec.text = it.rains.toString()
                binding.humidity.text = it.humidity.toString()
                binding.summary.text = it.summary
            } else {
                Toast.makeText(activity, resources.getString(R.string.weather_toast_error), Toast.LENGTH_SHORT).show()
            }
        })

    }

}