package com.muhammadtwaha.ibadahcompanion.ui.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.muhammadtwaha.ibadahcompanion.domain.worship.WorshipRules
import java.time.LocalDate

@Composable
fun rememberTrackingDate(): Pair<LocalDate, (LocalDate) -> Unit> {
    var dateText by rememberSaveable {
        mutableStateOf(maxOf(LocalDate.now(), WorshipRules.trackingStart).toString())
    }
    return LocalDate.parse(dateText) to { dateText = it.toString() }
}

@Composable
fun DaySwitcher(date: LocalDate, onDateChange: (LocalDate) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Button(onClick = { onDateChange(date.minusDays(1)) }) { Text("Previous") }
        Text(date.toString())
        Button(onClick = { onDateChange(date.plusDays(1)) }) { Text("Next") }
    }
}
