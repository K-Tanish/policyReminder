package com.policyreminder.app.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Customers : Screen("customers")
    object CustomerDetail : Screen("customer/{customerId}") {
        fun createRoute(customerId: Long) = "customer/$customerId"
    }
    object AddCustomer : Screen("add_customer")
    object Policies : Screen("policies")
    object PolicyDetail : Screen("policy/{policyId}") {
        fun createRoute(policyId: Long) = "policy/$policyId"
    }
    object AddPolicy : Screen("add_policy")
    object Reminders : Screen("reminders")
    object ReminderDetail : Screen("reminder/{reminderId}") {
        fun createRoute(reminderId: Long) = "reminder/$reminderId"
    }
    object Settings : Screen("settings")
    object SendMessage : Screen("send_message/{customerName}/{phoneNumber}/{message}") {
        fun createRoute(customerName: String, phoneNumber: String, message: String): String {
            // URL encode the parameters
            val encodedName = java.net.URLEncoder.encode(customerName, "UTF-8")
            val encodedPhone = java.net.URLEncoder.encode(phoneNumber, "UTF-8")
            val encodedMessage = java.net.URLEncoder.encode(message, "UTF-8")
            return "send_message/$encodedName/$encodedPhone/$encodedMessage"
        }
    }
}
