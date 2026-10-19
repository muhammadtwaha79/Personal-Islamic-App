package com.muhammadtwaha.ibadahcompanion.ui.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.muhammadtwaha.ibadahcompanion.domain.worship.WorshipRules
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun rememberTrackingDate(): Pair<LocalDate, (LocalDate) -> Unit> {
    var dateText by rememberSaveable {
        mutableStateOf(LocalDate.now().toString())
    }
    return LocalDate.parse(dateText) to { dateText = it.toString() }
}

@Composable
fun DaySwitcher(date: LocalDate, onDateChange: (LocalDate) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        OutlinedButton(
            onClick = { onDateChange(date.minusDays(1)) },
            shape = RoundedCornerShape(14.dp)
        ) { Text("‹  Prev") }
        Text(
            date.format(DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH)),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
        )
        OutlinedButton(
            onClick = { onDateChange(date.plusDays(1)) },
            enabled = date.isBefore(LocalDate.now()),
            shape = RoundedCornerShape(14.dp)
        ) { Text("Next  ›") }
    }
}
