package com.hstan.autoservify.ui.main.Shops

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.hstan.autoservify.databinding.ActivityAddShopBinding
import com.hstan.autoservify.ui.main.Shops.Shop
import kotlinx.coroutines.launch

class AddShopActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddShopBinding
    private lateinit var viewModel: AddShopViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddShopBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = AddShopViewModel()

        lifecycleScope.launch {
            viewModel.isSuccessfullySaved.collect {
                it?.let {
                    if (it) {
                        Toast.makeText(this@AddShopActivity, "Shop added successfully!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.failureMessage.collect {
                it?.let {
                    Toast.makeText(this@AddShopActivity, it, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.submitButton.setOnClickListener {
            val title = binding.titleInput.text.toString().trim()
            val description = binding.descriptionInput.text.toString().trim()
            val address = binding.addressInput.text.toString().trim()
            val phone = binding.phoneInput.text.toString().trim()
            val email = binding.emailInput.text.toString().trim()

            if (title.isEmpty() || description.isEmpty() || address.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val shop = Shop().apply {
                this.title = title
                this.description = description
                this.address = address
                this.phone = phone
                this.email = email
            }

            viewModel.saveShop(shop)
        }
    }
}