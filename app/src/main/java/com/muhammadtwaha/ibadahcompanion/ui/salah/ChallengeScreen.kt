package com.muhammadtwaha.ibadahcompanion.ui.salah

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.muhammadtwaha.ibadahcompanion.data.WorshipRepository
import com.muhammadtwaha.ibadahcompanion.domain.worship.*
import java.time.LocalDate

@Composable
fun ChallengeScreen(repository: WorshipRepository) {
    val allRecords by remember(repository) { repository.challenge() }.collectAsState(initial = emptyList())
    val today = LocalDate.now()
    val prayers = allRecords.mapNotNull { record ->
        val date = runCatching { LocalDate.parse(record.date) }.getOrNull() ?: return@mapNotNull null
        val prayer = runCatching { Prayer.valueOf(record.prayer) }.getOrNull() ?: return@mapNotNull null
        ChallengePrayer(date, prayer, record.status == PrayerStatus.COMPLETED.name &&
            record.congregation == Congregation.WITH_JAMAAT.name &&
            record.takbeer == Takbeer.ACHIEVED.name)
    }
    val summary = ChallengeCalculator.calculate(prayers, today)
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("40 Days of Takbeer-e-Ula")
        Text("20 Oct – 28 Nov 2026 • 5 prayers per day • strict opening Takbeer self-report")
        Text("Achieved: ${summary.achievedOpportunities} / 200")
        Text("Full days: ${summary.fullSuccessDays} • Current streak: ${summary.currentConsecutiveDays}")
        Text("Longest streak: ${summary.longestConsecutiveDays} days")
        Text("Unknown is not success. These are personal metrics, not a ruling or reward guarantee.")
        LazyColumn(verticalArrangement = Arrangement.spacedBy(7.dp), modifier = Modifier.padding(top = 12.dp)) {
            items((0 until 40).toList()) { offset ->
                val date = WorshipRules.trackingStart.plusDays(offset.toLong())
                val success = Prayer.entries.count { prayer ->
                    prayers.any { it.date == date && it.prayer == prayer && it.achieved }
                }
                Card(Modifier.fillMaxWidth()) {
                    Text("Day ${offset + 1} — $date  |  $success / 5 confirmed",
                        modifier = Modifier.padding(12.dp))
                }
            }
        }
    }
}
