package com.policyreminder.app.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "call_logs",
    foreignKeys = [
        ForeignKey(
            entity = Customer::class,
            parentColumns = ["id"],
            childColumns = ["customerId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Policy::class,
            parentColumns = ["id"],
            childColumns = ["policyId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("customerId"), Index("policyId")]
)
data class CallLog(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val customerId: Long,
    val policyId: Long? = null,
    val callDate: Long,
    val outcome: CallOutcome,
    val followUpDate: Long? = null,
    val remarks: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

enum class CallOutcome {
    WILL_RENEW,
    INTERESTED,
    NO_RESPONSE,
    CALL_LATER,
    NOT_INTERESTED,
    ALREADY_RENEWED
}
