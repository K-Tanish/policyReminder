package com.policyreminder.app.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "family_members",
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
data class FamilyMember(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val customerId: Long,
    val name: String,
    val relationship: String, // Father, Mother, Spouse, Child, Other
    val dateOfBirth: Long? = null,
    val anniversaryDate: Long? = null, // For spouse
    val createdAt: Long = System.currentTimeMillis()
)
