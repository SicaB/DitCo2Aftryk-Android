package com.example.ditco2aftryk.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.ditco2aftryk.R
import com.example.ditco2aftryk.databinding.ActivityFlightBinding
import com.example.ditco2aftryk.utils.hideKeyboard
import com.example.ditco2aftryk.utils.toast
import com.example.ditco2aftryk.view.viewmodel.FlightViewModel
import kotlinx.android.synthetic.main.activity_flight.*
import kotlinx.android.synthetic.main.activity_flight.back
import kotlinx.android.synthetic.main.activity_flight.home

class FlightActivity : AppCompatActivity(), Listener, Actionbar{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // create the viewModel
        val viewModel = ViewModelProvider(this).get(
            FlightViewModel::class.java)

        // Bind this activity to the layout xml file using databinding
        val binding : ActivityFlightBinding = DataBindingUtil.setContentView(this, R.layout.activity_flight)

        // bind this activity to the viewModel
        binding.viewmodel = viewModel

        // define listener to the viewModel
        viewModel.listener = this

        // Actionbar
        home.setNavigationIcon(R.drawable.ic_home_black_24dp)
        back.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)

        enterHoursFlown.text = null
        calculatedCo2TextField.text = null
        enterHoursFlown.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (enterHoursFlown.text.isNotEmpty() && enterHoursFlown.text.toString() != ".") {
                    val hoursFlown = enterHoursFlown.text.toString().toDouble()
                    val calculatedFlightCo2 = hoursFlown * 92
                    calculatedCo2TextField.text = String.format("%.1f", calculatedFlightCo2)
                } else {
                    calculatedCo2TextField.text = null
                }
            }
        })
    }

    override fun onBackButtonClicked(v: View?){
        startActivity(Intent(this, EnterCo2Activity::class.java))
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        finish()
    }

    override fun onHomeButtonClicked(v: View?){
        startActivity(Intent(this, HomeScreenActivity::class.java))
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        finish()
    }

    override fun onSuccess() {
        enterHoursFlown.hideKeyboard()

        toast("Dit co2 aftryk er gemt")
        // intent is used to start a new activity
        val intent = Intent(this, HomeScreenActivity::class.java)
        // start activity
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        finish()
    }

    override fun onFailure(message: String) {
        toast(message)
    }
}
