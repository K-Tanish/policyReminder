package com.policyreminder.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronLeft
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.policyreminder.app.ui.theme.Foreground15
import java.text.SimpleDateFormat
import java.util.*

data class RenewalDate(
    val date: Calendar,
    val count: Int, // Number of renewals on this date
    val policyIds: List<Long>
)

@Composable
fun RenewalCalendar(
    renewalDates: List<RenewalDate>,
    onDateClick: (Calendar) -> Unit,
    modifier: Modifier = Modifier
) {
    var currentMonth by remember { mutableStateOf(Calendar.getInstance()) }
    
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Foreground15)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header with month navigation
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        currentMonth = currentMonth.clone() as Calendar
                        currentMonth.add(Calendar.MONTH, -1)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ChevronLeft,
                        contentDescription = "Previous month",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                
                Text(
                    text = SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(currentMonth.time),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
                
                IconButton(
                    onClick = {
                        currentMonth = currentMonth.clone() as Calendar
                        currentMonth.add(Calendar.MONTH, 1)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ChevronRight,
                        contentDescription = "Next month",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Weekday headers
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf("S", "M", "T", "W", "T", "F", "S").forEach { day ->
                    Text(
                        text = day,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Calendar grid
            CalendarGrid(
                month = currentMonth,
                renewalDates = renewalDates,
                onDateClick = onDateClick
            )
        }
    }
}

@Composable
private fun CalendarGrid(
    month: Calendar,
    renewalDates: List<RenewalDate>,
    onDateClick: (Calendar) -> Unit
) {
    val daysInMonth = month.getActualMaximum(Calendar.DAY_OF_MONTH)
    val firstDayOfMonth = (month.clone() as Calendar).apply {
        set(Calendar.DAY_OF_MONTH, 1)
    }
    val firstDayOfWeek = firstDayOfMonth.get(Calendar.DAY_OF_WEEK) - 1 // 0-based
    
    val today = Calendar.getInstance()
    
    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(2.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        userScrollEnabled = false
    ) {
        // Empty cells before first day
        items(firstDayOfWeek) {
            Box(modifier = Modifier.aspectRatio(1f))
        }
        
        // Days of the month
        items(daysInMonth) { dayIndex ->
            val day = dayIndex + 1
            val currentDate = (month.clone() as Calendar).apply {
                set(Calendar.DAY_OF_MONTH, day)
            }
            
            // Check if there are renewals on this date
            val renewalInfo = renewalDates.find { renewal ->
                renewal.date.get(Calendar.YEAR) == currentDate.get(Calendar.YEAR) &&
                renewal.date.get(Calendar.MONTH) == currentDate.get(Calendar.MONTH) &&
                renewal.date.get(Calendar.DAY_OF_MONTH) == currentDate.get(Calendar.DAY_OF_MONTH)
            }
            
            val isToday = currentDate.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                    currentDate.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
                    currentDate.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)
            
            CalendarDay(
                day = day,
                isToday = isToday,
                renewalCount = renewalInfo?.count ?: 0,
                onClick = { if (renewalInfo != null) onDateClick(currentDate) }
            )
        }
    }
}

@Composable
private fun CalendarDay(
    day: Int,
    isToday: Boolean,
    renewalCount: Int,
    onClick: () -> Unit
) {
    val backgroundColor = when {
        renewalCount > 0 -> Color(0xFFEF5350).copy(alpha = 0.15f) // Soft red for renewals
        isToday -> MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
        else -> Color.Transparent
    }
    
    val borderColor = when {
        renewalCount > 0 -> Color(0xFFEF5350).copy(alpha = 0.4f)
        isToday -> MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
        else -> Color.Transparent
    }
    
    val textColor = when {
        renewalCount > 0 -> Color(0xFFD32F2F) // Darker red for text
        isToday -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
    }
    
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .border(
                width = if (isToday || renewalCount > 0) 1.dp else 0.dp,
                color = borderColor,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(enabled = renewalCount > 0) { onClick() }
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = day.toString(),
                style = MaterialTheme.typography.bodySmall,
                color = textColor,
                fontWeight = if (renewalCount > 0 || isToday) FontWeight.Bold else FontWeight.Normal,
                fontSize = 13.sp
            )
            
            if (renewalCount > 0) {
                Box(
                    modifier = Modifier
                        .size(5.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFEF5350))
                )
            }
        }
    }
}
