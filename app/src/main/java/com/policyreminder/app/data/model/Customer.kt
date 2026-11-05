package com.policyreminder.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customers")
data class Customer(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val firstName: String,
    val middleName: String = "",
    val surname: String,
    val mobileNumber: String,
    val alternateMobile: String = "",
    val email: String = "",
    val address: String = "",
    val dateOfBirth: Long? = null, // Store as timestamp
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) {
    fun getFullName(): String {
        return if (middleName.isNotEmpty()) {
            "$firstName $middleName $surname"
        } else {
            "$firstName $surname"
        }
    }

    fun getInitials(): String {
        val first = firstName.firstOrNull()?.uppercaseChar() ?: ""
        val last = surname.firstOrNull()?.uppercaseChar() ?: ""
        return "$first$last"
    }
}
