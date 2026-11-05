package com.policyreminder.app.data.dao

import androidx.room.*
import com.policyreminder.app.data.model.MessageTemplate
import com.policyreminder.app.data.model.TemplateType
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageTemplateDao {
    
    @Query("SELECT * FROM message_templates ORDER BY name ASC")
    fun getAllTemplates(): Flow<List<MessageTemplate>>
    
    @Query("SELECT * FROM message_templates WHERE id = :templateId")
    fun getTemplateById(templateId: Long): Flow<MessageTemplate?>
    
    @Query("SELECT * FROM message_templates WHERE type = :type ORDER BY isDefault DESC, name ASC")
    fun getTemplatesByType(type: TemplateType): Flow<List<MessageTemplate>>
    
    @Query("SELECT * FROM message_templates WHERE type = :type AND isDefault = 1 LIMIT 1")
    fun getDefaultTemplateByType(type: TemplateType): Flow<MessageTemplate?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTemplate(template: MessageTemplate): Long
    
    @Update
    suspend fun updateTemplate(template: MessageTemplate)
    
    @Delete
    suspend fun deleteTemplate(template: MessageTemplate)
}
