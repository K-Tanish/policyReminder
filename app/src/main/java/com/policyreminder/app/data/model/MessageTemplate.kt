package com.policyreminder.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message_templates")
data class MessageTemplate(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val type: TemplateType,
    val content: String,
    val isDefault: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

enum class TemplateType {
    BIRTHDAY,
    ANNIVERSARY,
    POLICY_RENEWAL,
    FOLLOW_UP,
    GENERAL
}
