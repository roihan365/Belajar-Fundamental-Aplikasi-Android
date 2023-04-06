package com.example.mybroadcastreceiver

import android.Manifest.permission.RECEIVE_SMS
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.mybroadcastreceiver.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var binding: ActivityMainBinding?= null

    var requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(this, "Sms receiver permission diterima", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Sms receiver permission ditolak", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.btnPermission?.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.btn_permission -> requestPermissionLauncher.launch(RECEIVE_SMS)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}