package com.example.myreadwritefile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.myreadwritefile.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNew.setOnClickListener(this)
        binding.btnOpen.setOnClickListener(this)
        binding.btnSave.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.btn_new -> newFile()
            R.id.btn_open -> openFile()
            R.id.btn_save -> saveFile()

        }
    }

    private fun newFile() {
        binding.edtTitle.setText("")
        binding.edtFile.setText("")
        Toast.makeText(this, "Clearing File", Toast.LENGTH_SHORT).show()
    }

    private fun openFile() {
        val items = fileList()
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Pilih File")
        builder.setItems(items) { dialog, item -> loadData(items[item].toString())}
        val alert = builder.create()
        alert.show()
    }

    private fun loadData(title: String) {
        val fileModel = FileHelper.readFromFile(this, title)
        binding.edtTitle.setText(fileModel.fileName)
        binding.edtFile.setText(fileModel.data)
        Toast.makeText(this, "Loading" + fileModel.fileName + " data", Toast.LENGTH_SHORT).show()
    }

    private fun saveFile() {
        when {
            binding.edtTitle.text.toString().isEmpty() -> Toast.makeText(this,"Isi title dulu", Toast.LENGTH_SHORT).show()
            binding.edtFile.text.toString().isEmpty() -> Toast.makeText(this,"Isi data dulu", Toast.LENGTH_SHORT).show()
            else -> {
                val title = binding.edtTitle.text.toString()
                val text = binding.edtFile.text.toString()
                val fileModel = FileModel()
                fileModel.fileName = title
                fileModel.data = text
                FileHelper.writeToFile(fileModel, this)
                Toast.makeText(this, "Saving" + fileModel.fileName + " file", Toast.LENGTH_SHORT).show()
            }
        }
    }

}