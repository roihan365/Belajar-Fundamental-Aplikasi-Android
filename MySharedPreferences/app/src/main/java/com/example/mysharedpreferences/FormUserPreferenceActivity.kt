package com.example.mysharedpreferences

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.mysharedpreferences.databinding.ActivityFormUserPreferenceBinding

class FormUserPreferenceActivity : AppCompatActivity(), View.OnClickListener {
    companion object  {
        const val EXTRA_TYPE_FORM = "y"
        const val EXTRA_RESULT = "result"
        const val RESULT_CODE = 101

        const val TYPE_ADD = 1
        const val TYPE_EDIT = 2

        private const val FIELD_REQUIRED = "FIELD TIDAK BOLEH KOSONG"
        private const val FIELD_DIGIT_ONLY = "HANYA BOLEH DIISI ANGKA"
        private const val FIELD_IS_NOT_VALID = " EMAIL TIDAK VALID"
    }

    private lateinit var binding: ActivityFormUserPreferenceBinding
    private lateinit var userModel: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormUserPreferenceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener(this)
        userModel = intent.getParcelableExtra<UserModel>("USER") as UserModel
        val formType = intent.getIntExtra(EXTRA_TYPE_FORM, 0)

        var actionBarTittle = ""
        var btnTitle = ""

        when (formType) {
            TYPE_ADD -> {
                actionBarTittle = " Tambah Baru"
                btnTitle = "Simpan"
            }
            TYPE_EDIT -> {
                actionBarTittle = " Ubah"
                btnTitle = "Update"
                showPreferenceInfForm()
            }
        }

        supportActionBar?.title = actionBarTittle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showPreferenceInfForm() {
        binding.edtName.setText(userModel.name)
        binding.edtEmail.setText(userModel.email)
        binding.edtAge.setText(userModel.age.toString())
        binding.edtPhone.setText(userModel.phoneNumber)
        if (userModel.isLove) {
            binding.rbYes.isChecked = true
        }else {
            binding.rbNo.isChecked = true
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.btn_save -> {
                val name = binding.edtName.text.toString().trim()
                val email = binding.edtEmail.text.toString().trim()
                val age = binding.edtAge.text.toString().trim()
                val phone = binding.edtPhone.text.toString().trim()
                val isLoveMU = binding.rgLoveMu.checkedRadioButtonId == R.id.rb_yes

                if (name.isEmpty()) {
                    binding.edtName.error = FIELD_REQUIRED
                    return
                }
                if (email.isEmpty()) {
                    binding.edtEmail.error = FIELD_REQUIRED
                    return
                }
                if (!isValidEmail(email)) {
                    binding.edtEmail.error = FIELD_IS_NOT_VALID
                    return
                }
                if (age.isEmpty()) {
                    binding.edtAge.error = FIELD_REQUIRED
                    return
                }
                if (phone.isEmpty()) {
                    binding.edtPhone.error = FIELD_REQUIRED
                    return
                }
                if (!TextUtils.isDigitsOnly(phone)) {
                    binding.edtPhone.error = FIELD_DIGIT_ONLY
                    return
                }

                saveUser(name, email, age, phone, isLoveMU)

                val resultIntent = Intent()
                resultIntent.putExtra(EXTRA_RESULT, userModel)
                setResult(RESULT_CODE, resultIntent)

                finish()
            }
        }
    }
    private fun saveUser(name: String, email: String, age: String, phone: String, isLoveMU: Boolean) {
        val userPreference = UserPreference(this)

        userModel.name = name
        userModel.email = email
        userModel.age = Integer.parseInt(age)
        userModel.phoneNumber = phone
        userModel.isLove = isLoveMU

        userPreference.setUser(userModel)
        Toast.makeText(this, "Data Tersimpan", Toast.LENGTH_SHORT).show()
    }

    private fun isValidEmail(email: CharSequence): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}