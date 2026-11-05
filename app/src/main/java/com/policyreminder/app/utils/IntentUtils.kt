package com.policyreminder.app.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

object IntentUtils {
    
    /**
     * Open phone dialer with the given phone number
     */
    fun makePhoneCall(context: Context, phoneNumber: String) {
        try {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phoneNumber")
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Unable to open phone dialer", Toast.LENGTH_SHORT).show()
        }
    }
    
    /**
     * Open WhatsApp with Click-to-Chat for the given phone number
     */
    fun openWhatsApp(context: Context, phoneNumber: String, message: String = "") {
        try {
            // Remove any non-digit characters from phone number
            val cleanNumber = phoneNumber.replace(Regex("[^0-9]"), "")
            
            // Add country code if not present (assuming India +91)
            val fullNumber = if (cleanNumber.length == 10) {
                "91$cleanNumber"
            } else {
                cleanNumber
            }
            
            val url = if (message.isNotEmpty()) {
                "https://wa.me/$fullNumber?text=${Uri.encode(message)}"
            } else {
                "https://wa.me/$fullNumber"
            }
            
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "WhatsApp not installed or unable to open", Toast.LENGTH_SHORT).show()
        }
    }
    
    /**
     * Send email using default email client
     */
    fun sendEmail(context: Context, email: String, subject: String = "", body: String = "") {
        try {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$email")
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, body)
            }
            context.startActivity(Intent.createChooser(intent, "Send Email"))
        } catch (e: Exception) {
            Toast.makeText(context, "No email client found", Toast.LENGTH_SHORT).show()
        }
    }
    
    /**
     * Open maps with the given address
     */
    fun openMaps(context: Context, address: String) {
        try {
            val uri = Uri.parse("geo:0,0?q=${Uri.encode(address)}")
            val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                setPackage("com.google.android.apps.maps")
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            // If Google Maps is not installed, try with any map app
            try {
                val uri = Uri.parse("geo:0,0?q=${Uri.encode(address)}")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                context.startActivity(intent)
            } catch (e2: Exception) {
                Toast.makeText(context, "No maps application found", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
