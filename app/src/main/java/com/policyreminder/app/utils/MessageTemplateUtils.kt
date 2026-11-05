package com.policyreminder.app.utils

import com.policyreminder.app.data.model.*
import java.text.SimpleDateFormat
import java.util.*

object MessageTemplateUtils {
    
    /**
     * Get default birthday message template
     */
    fun getBirthdayTemplate(): String {
        return """
            🎂 Happy Birthday {customer_name}! 🎉
            
            Wishing you a wonderful day filled with joy and happiness!
            
            Best regards,
            {agent_name}
            Insurance Advisor
        """.trimIndent()
    }
    
    /**
     * Get default anniversary message template
     */
    fun getAnniversaryTemplate(): String {
        return """
            💐 Happy Anniversary {customer_name}! 🎊
            
            Wishing you both many more years of happiness together!
            
            Warm regards,
            {agent_name}
            Insurance Advisor
        """.trimIndent()
    }
    
    /**
     * Get default policy renewal message template
     */
    fun getPolicyRenewalTemplate(): String {
        return """
            Dear {customer_name},
            
            This is a friendly reminder that your {policy_type} policy (No: {policy_number}) with {insurance_company} is due for renewal on {renewal_date}.
            
            Premium Amount: {premium_amount}
            
            Please contact me to process the renewal.
            
            Best regards,
            {agent_name}
            Insurance Advisor
            📞 {agent_phone}
        """.trimIndent()
    }
    
    /**
     * Get default follow-up message template
     */
    fun getFollowUpTemplate(): String {
        return """
            Hello {customer_name},
            
            I wanted to follow up regarding your {policy_type} policy.
            
            Please let me know if you have any questions or need assistance.
            
            Best regards,
            {agent_name}
            Insurance Advisor
            📞 {agent_phone}
        """.trimIndent()
    }
    
    /**
     * Get default general message template
     */
    fun getGeneralTemplate(): String {
        return """
            Hello {customer_name},
            
            {message}
            
            Best regards,
            {agent_name}
            Insurance Advisor
        """.trimIndent()
    }
    
    /**
     * Replace placeholders in template with actual data
     */
    fun fillTemplate(
        template: String,
        customer: Customer,
        policy: Policy? = null,
        agentName: String = "Tanish Patil",
        agentPhone: String = "",
        customMessage: String = ""
    ): String {
        var message = template
        
        // Customer placeholders
        message = message.replace("{customer_name}", customer.getFullName())
        message = message.replace("{first_name}", customer.firstName)
        
        // Policy placeholders
        policy?.let {
            message = message.replace("{policy_type}", it.policyType)
            message = message.replace("{policy_number}", it.policyNumber)
            message = message.replace("{insurance_company}", it.insuranceCompany)
            message = message.replace("{renewal_date}", formatDate(it.renewalDate))
            message = message.replace("{premium_amount}", formatCurrency(it.premiumAmount))
        }
        
        // Agent placeholders
        message = message.replace("{agent_name}", agentName)
        message = message.replace("{agent_phone}", agentPhone)
        
        // Custom message
        message = message.replace("{message}", customMessage)
        
        return message
    }
    
    /**
     * Generate birthday message for a customer
     */
    fun generateBirthdayMessage(
        customer: Customer,
        agentName: String = "Tanish Patil"
    ): String {
        return fillTemplate(
            template = getBirthdayTemplate(),
            customer = customer,
            agentName = agentName
        )
    }
    
    /**
     * Generate anniversary message for a customer
     */
    fun generateAnniversaryMessage(
        customer: Customer,
        agentName: String = "Tanish Patil"
    ): String {
        return fillTemplate(
            template = getAnniversaryTemplate(),
            customer = customer,
            agentName = agentName
        )
    }
    
    /**
     * Generate policy renewal message
     */
    fun generatePolicyRenewalMessage(
        customer: Customer,
        policy: Policy,
        agentName: String = "Tanish Patil",
        agentPhone: String = ""
    ): String {
        return fillTemplate(
            template = getPolicyRenewalTemplate(),
            customer = customer,
            policy = policy,
            agentName = agentName,
            agentPhone = agentPhone
        )
    }
    
    /**
     * Generate follow-up message
     */
    fun generateFollowUpMessage(
        customer: Customer,
        policy: Policy? = null,
        agentName: String = "Tanish Patil",
        agentPhone: String = ""
    ): String {
        return fillTemplate(
            template = getFollowUpTemplate(),
            customer = customer,
            policy = policy,
            agentName = agentName,
            agentPhone = agentPhone
        )
    }
    
    /**
     * Get message for reminder type
     */
    fun getMessageForReminder(
        reminder: Reminder,
        customer: Customer,
        policy: Policy? = null,
        agentName: String = "Tanish Patil",
        agentPhone: String = ""
    ): String {
        return when (reminder.type) {
            ReminderType.BIRTHDAY -> generateBirthdayMessage(customer, agentName)
            ReminderType.ANNIVERSARY -> generateAnniversaryMessage(customer, agentName)
            ReminderType.POLICY_RENEWAL -> {
                policy?.let {
                    generatePolicyRenewalMessage(customer, it, agentName, agentPhone)
                } ?: getGeneralTemplate()
            }
            ReminderType.FOLLOW_UP_CALL -> generateFollowUpMessage(customer, policy, agentName, agentPhone)
        }
    }
    
    private fun formatDate(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }
    
    private fun formatCurrency(amount: Double): String {
        return "₹${String.format("%,.2f", amount)}"
    }
}
