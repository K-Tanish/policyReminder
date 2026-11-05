package com.policyreminder.app.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "policies",
    foreignKeys = [
        ForeignKey(
            entity = Customer::class,
            parentColumns = ["id"],
            childColumns = ["customerId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("customerId")]
)
data class Policy(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val customerId: Long,
    val policyNumber: String,
    val insuranceCompany: String,
    val policyType: String, // Life, Health, Motor, etc.
    val policyholderName: String,
    val startDate: Long,
    val renewalDate: Long,
    val premiumAmount: Double,
    val paymentFrequency: String, // Yearly, Half-yearly, Quarterly, Monthly
    val status: PolicyStatus = PolicyStatus.ACTIVE,
    val notes: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) {
    fun getDaysUntilRenewal(): Long {
        val today = System.currentTimeMillis()
        val diff = renewalDate - today
        return diff / (1000 * 60 * 60 * 24)
    }

    fun getCurrentStatus(): PolicyStatus {
        val daysUntil = getDaysUntilRenewal()
        return when {
            daysUntil < 0 -> PolicyStatus.OVERDUE
            daysUntil <= 7 -> PolicyStatus.DUE_SOON
            status == PolicyStatus.RENEWED -> PolicyStatus.RENEWED
            status == PolicyStatus.LAPSED -> PolicyStatus.LAPSED
            else -> PolicyStatus.ACTIVE
        }
    }
}

enum class PolicyStatus {
    ACTIVE,
    DUE_SOON,
    OVERDUE,
    RENEWED,
    LAPSED
}
