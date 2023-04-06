package com.example.myunittest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.example.myunittest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        mainViewModel = MainViewModel(CuboIdModel())

        activityMainBinding.btnSave.setOnClickListener(this)
        activityMainBinding.btnCalculateCircumference.setOnClickListener(this)
        activityMainBinding.btnCalculateVolume.setOnClickListener(this)
        activityMainBinding.btnCalculateSurfaceArea.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        val length = activityMainBinding.edtLength.text.toString().trim()
        val width = activityMainBinding.edtWidth.text.toString().trim()
        val height = activityMainBinding.edtHeight.text.toString().trim()

        when {
            TextUtils.isEmpty(length) -> {
                activityMainBinding.edtLength.error = "Field ini tidak boleh kosong"
            }
            TextUtils.isEmpty(width) -> {
                activityMainBinding.edtWidth.error = "Field ini tidak boleh kosong"
            }
            TextUtils.isEmpty(height) -> {
                activityMainBinding.edtHeight.error = "Field ini tidak boleh kosong"
            }
            else -> {
                val valueLength = length.toDouble()
                val valueWidth = length.toDouble()
                val valueHeight = length.toDouble()

                when(p0?.id) {
                    R.id.btn_save -> {
                        mainViewModel.save(valueLength, valueWidth, valueHeight)
                        visible()
                    }
                    R.id.btn_calculate_circumference -> {
                        activityMainBinding.tvResult.text = mainViewModel.getCircumference().toString()
                        gone()
                    }
                    R.id.btn_calculate_volume -> {
                        activityMainBinding.tvResult.text = mainViewModel.getVolume().toString()
                        gone()
                    }
                    R.id.btn_calculate_surface_area -> {
                        activityMainBinding.tvResult.text = mainViewModel.getSurfaceArea().toString()
                        gone()
                    }
                }
            }
        }
    }

    private fun visible() {
        activityMainBinding.btnCalculateVolume.visibility = View.VISIBLE
        activityMainBinding.btnCalculateCircumference.visibility = View.VISIBLE
        activityMainBinding.btnCalculateSurfaceArea.visibility = View.VISIBLE
        activityMainBinding.btnSave.visibility = View.GONE
    }
    private fun gone() {
        activityMainBinding.btnCalculateVolume.visibility = View.GONE
        activityMainBinding.btnCalculateCircumference.visibility = View.GONE
        activityMainBinding.btnCalculateSurfaceArea.visibility = View.GONE
        activityMainBinding.btnSave.visibility = View.VISIBLE
    }
}