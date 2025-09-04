package com.hstan.autoservify.ui

import com.hstan.autoservify.ui.main.Shops.SpareParts.PartsCraft

class Order {
    var id: String = ""
    var quantity: Int = 0
    var specialRequirements: String = ""
    var postalAddress: String = ""
    var item: PartsCraft? = null
    var userContact: String = ""
    var userId: String = ""
    var userName: String = ""
    var userEmail: String = ""
    var userFCMToken: String = ""
    var status: String = ""        // e.g., "pending", "paid", "shipped", "delivered", "cancelled"
    var orderDate: String = ""     // store as ISO string or display-ready date
}