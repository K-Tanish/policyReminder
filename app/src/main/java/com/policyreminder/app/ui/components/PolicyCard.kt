package com.policyreminder.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.policyreminder.app.data.model.Policy
import com.policyreminder.app.ui.theme.Background30
import com.policyreminder.app.ui.theme.ForegroundPrimary
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun PolicyCard(
    policy: Policy,
    customerName: String,
    customerInitials: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(220.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(ForegroundPrimary)
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Customer info row
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Background30),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = customerInitials,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.background
                    )
                }
                
                Column {
                    Text(
                        text = customerName,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.background
                    )
                    Text(
                        text = policy.policyType,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.background.copy(alpha = 0.6f)
                    )
                }
            }
            
            // Divider
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(0.5.dp)
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.15f))
            )
            
            // Company and policy number
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = policy.insuranceCompany,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.background.copy(alpha = 0.5f)
                )
                Text(
                    text = policy.policyNumber,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.background.copy(alpha = 0.5f)
                )
            }
            
            // Premium and renewal date
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = formatCurrency(policy.premiumAmount) + "/${getFrequencyShort(policy.paymentFrequency)}",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.background
                )
                
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Background30)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = formatDate(policy.renewalDate),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.background
                    )
                }
            }
            
            // Status indicator
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.background.copy(alpha = 0.4f))
                )
                Text(
                    text = policy.getCurrentStatus().name.replace("_", " ").lowercase()
                        .replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.background.copy(alpha = 0.6f)
                )
            }
        }
    }
}

private fun formatCurrency(amount: Double): String {
    val format = NumberFormat.getCurrencyInstance(Locale("en", "IN"))
    return format.format(amount)
}

private fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

private fun getFrequencyShort(frequency: String): String {
    return when (frequency.lowercase()) {
        "yearly" -> "year"
        "half-yearly" -> "6mo"
        "quarterly" -> "qtr"
        "monthly" -> "mo"
        else -> frequency
    }
}
