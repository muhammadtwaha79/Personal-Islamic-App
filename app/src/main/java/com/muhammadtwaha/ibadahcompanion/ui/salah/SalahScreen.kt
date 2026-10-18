package com.muhammadtwaha.ibadahcompanion.ui.salah

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.muhammadtwaha.ibadahcompanion.data.WorshipRepository
import com.muhammadtwaha.ibadahcompanion.data.db.SalahRecord
import com.muhammadtwaha.ibadahcompanion.domain.worship.*
import com.muhammadtwaha.ibadahcompanion.ui.shared.DaySwitcher
import com.muhammadtwaha.ibadahcompanion.ui.shared.rememberTrackingDate
import java.time.DayOfWeek
import java.time.LocalDate
import kotlinx.coroutines.launch

@Composable
fun SalahScreen(repository: WorshipRepository) {
    val (date, setDate) = rememberTrackingDate()
    val stream = remember(date) { repository.salah(date) }
    val records by stream.collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()
    Column(Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        Text("Five daily Fard prayers — personal record")
        DaySwitcher(date, setDate)
        Text("Record only what you actually performed. Unanswered does not mean missed.")
        if (date.isAfter(LocalDate.now())) {
            Text("Future date — recording unlocks on the selected date.")
        } else LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(Prayer.entries) { prayer ->
                SalahPrayerCard(date, prayer, records.firstOrNull { it.prayer == prayer.name }) {
                    status, mode, takbeer, joining, midday ->
                    scope.launch { repository.setSalah(date, prayer, status, mode, takbeer, joining, midday) }
                }
            }
        }
    }
}

@Composable
private fun SalahPrayerCard(
    date: LocalDate, prayer: Prayer, saved: SalahRecord?,
    onRecord: (PrayerStatus, Congregation, Takbeer, Joining, MiddayMode) -> Unit,
) {
    val fridayMidday = prayer == Prayer.DHUHR && date.dayOfWeek == DayOfWeek.FRIDAY
    val previouslySelected = saved?.middayMode?.let { runCatching { MiddayMode.valueOf(it) }.getOrNull() }
    val midday = if (fridayMidday) previouslySelected ?: MiddayMode.UNKNOWN else MiddayMode.UNKNOWN
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(prayer.name.lowercase().replaceFirstChar { it.uppercase() })
            if (fridayMidday) Text("Friday: specify Jumu'ah or Dhuhr when recording; no ruling inferred.")
            Text("Status: ${saved?.status ?: "PENDING"}  |  Jamaat: ${saved?.congregation ?: "UNKNOWN"}")
            Text("Takbeer-e-Ula: ${saved?.takbeer ?: "UNKNOWN"}")
            if (fridayMidday) {
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    OutlinedButton(onClick = { onRecord(PrayerStatus.COMPLETED, Congregation.WITH_JAMAAT,
                        Takbeer.ACHIEVED, Joining.FROM_BEGINNING, MiddayMode.JUMUAH) }) {
                        Text("Jumu'ah + Takbeer")
                    }
                    OutlinedButton(onClick = { onRecord(PrayerStatus.COMPLETED, Congregation.WITH_JAMAAT,
                        Takbeer.ACHIEVED, Joining.FROM_BEGINNING, MiddayMode.DHUHR) }) {
                        Text("Dhuhr + Takbeer")
                    }
                }
            } else {
                Button(onClick = { onRecord(PrayerStatus.COMPLETED, Congregation.WITH_JAMAAT,
                    Takbeer.ACHIEVED, Joining.FROM_BEGINNING, MiddayMode.UNKNOWN) }) {
                    Text("Jamaat + opening Takbeer")
                }
            }
            OutlinedButton(onClick = { onRecord(PrayerStatus.COMPLETED, Congregation.WITH_JAMAAT,
                Takbeer.NOT_ACHIEVED, Joining.JOINED_LATE, midday) }) {
                Text("Jamaat — joined late")
            }
            OutlinedButton(onClick = { onRecord(PrayerStatus.COMPLETED, Congregation.INDIVIDUAL,
                Takbeer.NOT_APPLICABLE, Joining.UNKNOWN, midday) }) {
                Text("Prayed individually")
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(onClick = { onRecord(PrayerStatus.MISSED, Congregation.UNKNOWN,
                    Takbeer.UNKNOWN, Joining.UNKNOWN, MiddayMode.UNKNOWN) }) { Text("Missed") }
                OutlinedButton(onClick = { onRecord(PrayerStatus.UNKNOWN, Congregation.UNKNOWN,
                    Takbeer.UNKNOWN, Joining.UNKNOWN, MiddayMode.UNKNOWN) }) { Text("Undo / Unknown") }
            }
        }
    }
}
