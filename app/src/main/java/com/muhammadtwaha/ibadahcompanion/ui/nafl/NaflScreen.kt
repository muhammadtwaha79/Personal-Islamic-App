package com.muhammadtwaha.ibadahcompanion.ui.nafl

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.muhammadtwaha.ibadahcompanion.data.PRACTICES
import com.muhammadtwaha.ibadahcompanion.data.PracticeDefinition
import com.muhammadtwaha.ibadahcompanion.data.WorshipRepository
import com.muhammadtwaha.ibadahcompanion.data.db.PracticeRecord
import com.muhammadtwaha.ibadahcompanion.ui.shared.DaySwitcher
import com.muhammadtwaha.ibadahcompanion.ui.shared.rememberTrackingDate
import java.time.LocalDate
import kotlinx.coroutines.launch

@Composable
fun NaflScreen(repository: WorshipRepository) {
    val (date, setDate) = rememberTrackingDate()
    val records by remember(date) { repository.practices(date) }.collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()
    Column(Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        Text("Sunnah & Nafl — personal log")
        DaySwitcher(date, setDate)
        Text("Only completed prayers are counted. Rakah counts are self-reported; consult fiqh guidance.")
        if (date.isAfter(LocalDate.now())) {
            Text("Future date — recording unlocks on the selected date.")
        } else LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(PRACTICES) { practice ->
                NaflCard(date, practice, records.firstOrNull { it.practice == practice.key }) { rakahs, complete ->
                    scope.launch { repository.setPractice(date, practice.key, rakahs, complete) }
                }
            }
        }
    }
}

@Composable
private fun NaflCard(
    date: LocalDate,
    practice: PracticeDefinition,
    saved: PracticeRecord?,
    update: (Int, Boolean) -> Unit,
) {
    var rakahs by remember(date, practice.key, saved?.rakahs) {
        mutableIntStateOf(saved?.rakahs ?: practice.defaultRakahs)
    }
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(practice.label)
            Text(if (saved?.completed == true) "Recorded completed" else "Not recorded as completed")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(onClick = { if (rakahs > 1) rakahs-- }) { Text("−") }
                Text("$rakahs rakahs", modifier = Modifier.padding(top = 12.dp))
                OutlinedButton(onClick = { if (rakahs < 100) rakahs++ }) { Text("+") }
            }
            Button(onClick = { update(rakahs, saved?.completed != true) }) {
                Text(if (saved?.completed == true) "Undo record" else "Mark completed")
            }
        }
    }
}
