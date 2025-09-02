package com.hstan.autoservify.ui.main.Shops.SpareParts

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.hstan.autoservify.databinding.ActivityAddpartscraftBinding
import com.hstan.autoservify.ui.main.Shops.SpareParts.PartsCraft
import kotlinx.coroutines.launch

class Addpartscraft : AppCompatActivity() {
    private var uri: Uri? = null
    lateinit var binding: ActivityAddpartscraftBinding;
    lateinit var viewModel: AddpartcraftViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddpartscraftBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = AddpartcraftViewModel()

        lifecycleScope.launch {
            viewModel.isSuccessfullySaved.collect {
                it?.let {
                    if (it == true) {
                        Toast.makeText(
                            this@Addpartscraft,
                            "Successfully saved",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        finish()
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewModel.failureMessage.collect {
                it?.let {
                    Toast.makeText(this@Addpartscraft, it, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.submitButton.setOnClickListener {
            val title = binding.ename.text.toString().trim()
            val description = binding.editTextText3.text.toString().trim()
            val priceText = binding.editTextText4.text.toString().trim()

            // Validate the input fields
            if (title.isEmpty() || description.isEmpty() || priceText.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val price = priceText.toIntOrNull()

            if (price == null) {
                Toast.makeText(this, "Please enter a valid price", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create a Handcraft object with the entered data
            val partsCraft = PartsCraft()
            partsCraft.title = title
            partsCraft.price = price
            partsCraft.description = description

            if (uri == null)
                viewModel.saveHandCraft(partsCraft)
            else
                viewModel.uploadImageAndSaveHandCraft(getRealPathFromURI(uri!!)!!, partsCraft)

            Toast.makeText(this, "Handcraft Added Successfully!", Toast.LENGTH_SHORT).show()
        }

        binding.spImage.setOnClickListener {
            chooseImageFromGallery()
        }

    }

    private fun chooseImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(intent)
    }

    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            uri = result.data?.data
            if (uri != null) {
                binding.spImage.setImageURI(uri)
            } else {
                Log.e("Gallery", "No image selected")
            }
        }
    }

    private fun getRealPathFromURI(uri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            if (cursor.moveToFirst()) {
                return cursor.getString(columnIndex)
            }
        }
        return null
    }
}
