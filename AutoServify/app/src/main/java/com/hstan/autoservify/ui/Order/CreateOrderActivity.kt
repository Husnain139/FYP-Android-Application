
package com.hstan.autoservify.ui.main.Shops.SpareParts

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.gson.Gson
import com.hstan.autoservify.databinding.ActivityCreateOrderBinding
import com.hstan.autoservify.model.repositories.OrderRepository
import com.hstan.autoservify.ui.Order
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class CreateOrderActivity : AppCompatActivity() {
    lateinit var binding: ActivityCreateOrderBinding
    lateinit var part: PartsCraft
    private val orderRepository = OrderRepository()
    lateinit var progressDialog: ProgressDialog

    private var quantity = 1
    private var shippingFee = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Placing your order...")
        progressDialog.setCancelable(false)

        // Receive item to order
        part = Gson().fromJson(intent.getStringExtra("data"), PartsCraft::class.java)

        // Bind item info
        binding.itemTitle.text = part.title
        binding.itemPrice.text = "Rs. ${part.price}"
        binding.qtyValue.text = quantity.toString()

        // Initial totals
        updateTotals()

        // Quantity controls
        binding.qtyMinus.setOnClickListener {
            if (quantity > 1) {
                quantity -= 1
                binding.qtyValue.text = quantity.toString()
                updateTotals()
            }
        }
        binding.qtyPlus.setOnClickListener {
            quantity += 1
            binding.qtyValue.text = quantity.toString()
            updateTotals()
        }

        // Place order
        binding.placeOrderButton.setOnClickListener {
            val address = binding.postalAddress.text.toString().trim()
            val contact = binding.userContact.text.toString().trim()
            val notes = binding.specialRequirements.text.toString().trim()

            if (address.isEmpty() || contact.isEmpty()) {
                Toast.makeText(this, "Please fill address and contact", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = FirebaseAuth.getInstance().currentUser
            if (user == null) {
                Toast.makeText(this, "Please sign in first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val order = buildOrder(user, address, contact, notes)
            saveOrder(order)
        }
    }

    private fun updateTotals() {
        val subtotal = (part.price?.toDouble() ?: part.price.toDouble()) * quantity
        val total = subtotal + shippingFee
        binding.summarySubtotal.text = "Rs. ${subtotal}"
        binding.summaryShipping.text = "Rs. ${shippingFee}"
        binding.summaryTotal.text = "Rs. ${total}"
    }

    private fun buildOrder(
        user: FirebaseUser,
        address: String,
        contact: String,
        notes: String
    ): Order {
        val order = Order()
        order.item = part
        order.status = "Order Placed"
        order.quantity = quantity
        order.postalAddress = address
        order.specialRequirements = notes
        order.userContact = contact
        order.orderDate = SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.getDefault())
            .format(System.currentTimeMillis())
        order.userEmail = user.email ?: ""
        order.userName = user.displayName ?: ""
        order.userId = user.uid
        return order
    }

    private fun saveOrder(order: Order) {
        lifecycleScope.launch {
            try {
                progressDialog.show()
                val result = orderRepository.saveOrder(order)
                progressDialog.dismiss()

                result.onSuccess {
                    Toast.makeText(this@CreateOrderActivity, "Order placed successfully!", Toast.LENGTH_LONG).show()
                    finish()
                }.onFailure { e ->
                    Toast.makeText(this@CreateOrderActivity, e.message ?: "Failed to place order", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                progressDialog.dismiss()
                Toast.makeText(this@CreateOrderActivity, e.message ?: "Failed to place order", Toast.LENGTH_LONG).show()
            }
        }
    }
}

