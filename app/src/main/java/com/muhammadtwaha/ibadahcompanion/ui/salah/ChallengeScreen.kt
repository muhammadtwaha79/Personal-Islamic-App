package com.muhammadtwaha.ibadahcompanion.ui.salah

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.muhammadtwaha.ibadahcompanion.data.WorshipRepository
import com.muhammadtwaha.ibadahcompanion.domain.worship.*
import com.muhammadtwaha.ibadahcompanion.ui.design.ElitePageTitle
import com.muhammadtwaha.ibadahcompanion.ui.design.ElitePalette
import com.muhammadtwaha.ibadahcompanion.ui.design.ElitePill
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
    Column(Modifier.fillMaxSize().padding(horizontal = 18.dp)) {
        ElitePageTitle(
            "Opening Takbeer", "40-Day Journey",
            "20 October – 28 November 2026 · Five opportunities each day."
        )
        Card(shape = RoundedCornerShape(27.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
            Column(Modifier.fillMaxWidth().padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("PERSONAL CHALLENGE", color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelMedium)
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text("${summary.achievedOpportunities} / 200",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold)
                        Text("Confirmed opportunities",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodySmall)
                    }
                    ElitePill("${summary.fullSuccessDays} full days",
                        summary.fullSuccessDays > 0)
                }
                LinearProgressIndicator(
                    progress = { summary.achievedOpportunities / 200f },
                    modifier = Modifier.fillMaxWidth())
                Text("Current streak: ${summary.currentConsecutiveDays} days   ·   Longest: ${summary.longestConsecutiveDays}",
                    style = MaterialTheme.typography.bodySmall)
            }
        }
        Text("Your 40 days", style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = 20.dp, bottom = 12.dp),
            fontWeight = FontWeight.SemiBold)
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items((0 until 40).toList()) { offset ->
                val date = WorshipRules.trackingStart.plusDays(offset.toLong())
                val achieved = Prayer.entries.count { prayer ->
                    prayers.any { it.date == date && it.prayer == prayer && it.achieved }
                }
                val success = achieved == 5
                Card(
                    shape = RoundedCornerShape(19.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface),
                ) {
                    Row(
                        Modifier.fillMaxWidth().padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (success) ElitePalette.Sage else
                                    MaterialTheme.colorScheme.surfaceVariant),
                        ) {
                            Column(Modifier.padding(horizontal = 15.dp, vertical = 9.dp)) {
                                Text("${offset + 1}", fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary)
                                Text("DAY", style = MaterialTheme.typography.labelSmall)
                            }
                        }
                        Column(Modifier.weight(1f)) {
                            Text("$date", style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold)
                            Text("$achieved of 5 opening Takbeer confirmations",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        ElitePill(if (success) "Done ✓" else "$achieved/5", success)
                    }
                }
            }
            item {
                Text(
                    "Unknown does not count as success. These are self-reported personal metrics, not a religious ruling or reward guarantee.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(vertical = 22.dp))
            }
        }
    }
}
