package com.hstan.autoservify.ui.main.Shops.Services

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.hstan.autoservify.R

class Add_Service_Activity : AppCompatActivity() {

    private val viewModel: ServiceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_service)

        val name = findViewById<EditText>(R.id.name)
        val desc = findViewById<EditText>(R.id.descript)
        val price = findViewById<EditText>(R.id.price1)
        val addBtn = findViewById<Button>(R.id.AddService_button)

        addBtn.setOnClickListener {
            if (name.text.isNullOrBlank() || desc.text.isNullOrBlank() || price.text.isNullOrBlank()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val service = Service(
                id = FirebaseFirestore.getInstance().collection("services").document().id,
                name = name.text.toString(),
                description = desc.text.toString(),
                price = price.text.toString().toDoubleOrNull() ?: 0.0,
                rating = 0.0
            )

            viewModel.addService(service)
        }

        // ✅ Observe save result
        viewModel.isSuccessfullySaved.observe(this) { success ->
            if (success == true) {
                Toast.makeText(this, "Service Added", Toast.LENGTH_SHORT).show()
                finish()
            } else if (success == false) {
                Toast.makeText(this, "Failed to add service", Toast.LENGTH_SHORT).show()
            }
        }

        // ✅ Observe errors
        viewModel.failureMessage.observe(this) { error ->
            error?.let {
                Toast.makeText(this, "Error: $it", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
