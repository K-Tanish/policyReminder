package com.policyreminder.app.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "renewal_history",
    foreignKeys = [
        ForeignKey(
            entity = Policy::class,
            parentColumns = ["id"],
            childColumns = ["policyId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("policyId")]
)
data class RenewalHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val policyId: Long,
    val previousRenewalDate: Long,
    val newRenewalDate: Long,
    val previousPremium: Double,
    val newPremium: Double,
    val remarks: String = "",
    val renewedAt: Long = System.currentTimeMillis()
)
