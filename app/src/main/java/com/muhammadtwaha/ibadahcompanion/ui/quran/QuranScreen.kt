package com.muhammadtwaha.ibadahcompanion.ui.quran

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.muhammadtwaha.ibadahcompanion.data.WorshipRepository
import com.muhammadtwaha.ibadahcompanion.domain.worship.QuranRules
import com.muhammadtwaha.ibadahcompanion.ui.design.ElitePageTitle
import com.muhammadtwaha.ibadahcompanion.ui.design.ElitePill
import com.muhammadtwaha.ibadahcompanion.ui.design.EliteSectionTitle
import com.muhammadtwaha.ibadahcompanion.ui.shared.DaySwitcher
import com.muhammadtwaha.ibadahcompanion.ui.shared.rememberTrackingDate
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlinx.coroutines.launch

@Composable
fun QuranScreen(repository: WorshipRepository) {
    val (date, setDate) = rememberTrackingDate()
    val reading by remember(date) { repository.quran(date) }.collectAsState(initial = null)
    val scope = rememberCoroutineScope()
    var fromText by remember { mutableStateOf("") }
    var untilText by remember { mutableStateOf("") }
    var range by remember { mutableStateOf<Pair<LocalDate, LocalDate>?>(null) }
    var error by remember { mutableStateOf("") }
    var askKhatam by remember { mutableStateOf(false) }
    val eventList by remember(date) { repository.khatamPeriod(date, date) }
        .collectAsState(initial = emptyList())
    val khatamsToday = eventList.sumOf { it.delta }
    val editable = !date.isAfter(LocalDate.now())

    LaunchedEffect(repository) {
        repository.ramadanRange()?.let {
            fromText = it.first.toString()
            untilText = it.second.toString()
            range = it
        }
    }

    fun save(action: suspend () -> Unit) {
        scope.launch {
            try { action(); error = "" }
            catch (e: Exception) { error = e.message ?: "Could not save. Please retry." }
        }
    }

    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState())
            .padding(horizontal = 18.dp),
        verticalArrangement = Arrangement.spacedBy(13.dp),
    ) {
        ElitePageTitle("Daily Tilawat", "Your Quran journey",
            "A peaceful rhythm of reading, one day at a time.")
        DaySwitcher(date, setDate)
        if (!editable) Text("Future date — recording unlocks on that day.",
            color = MaterialTheme.colorScheme.onSurfaceVariant)
        if (error.isNotBlank()) Text(error, color = MaterialTheme.colorScheme.error)

        val units = reading?.halfJuzUnits ?: 0
        Card(
            shape = RoundedCornerShape(26.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(Modifier.fillMaxWidth().padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("HALF-PARA DAILY GOAL", color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelMedium)
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text("${units / 2.0} juz",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold)
                        Text("Read today", style = MaterialTheme.typography.bodySmall)
                    }
                    ElitePill(if (units >= 1) "Goal reached" else "In progress", units >= 1)
                }
                LinearProgressIndicator(
                    progress = { units.toFloat().coerceIn(0f, 1f) },
                    modifier = Modifier.fillMaxWidth(),
                )
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedButton(
                        enabled = editable && units > 0,
                        shape = RoundedCornerShape(15.dp),
                        onClick = { save { repository.setQuran(date, deltaHalfUnits = -1) } },
                    ) { Text("− ½ para") }
                    Button(
                        enabled = editable && units < 60,
                        shape = RoundedCornerShape(15.dp),
                        onClick = { save { repository.setQuran(date, deltaHalfUnits = 1) } },
                    ) { Text("+ ½ para read") }
                }
            }
        }

        EliteSectionTitle("Your daily Surahs")
        listOf(
            Triple("Surah Yaseen", reading?.yaseen ?: false, "yaseen"),
            Triple("Surah Waqiah", reading?.waqiah ?: false, "waqiah"),
            Triple("Surah Mulk", reading?.mulk ?: false, "mulk"),
        ).forEachIndexed { index, (name, completed, key) ->
            Card(
                shape = RoundedCornerShape(21.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Row(
                    Modifier.fillMaxWidth().padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Text("0${index + 1}", color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold)
                    Column(Modifier.weight(1f)) {
                        Text(name, fontWeight = FontWeight.SemiBold,
                            style = MaterialTheme.typography.titleMedium)
                        Text(if (completed) "Recorded completed" else "Not recorded",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodySmall)
                    }
                    OutlinedButton(
                        enabled = editable,
                        shape = RoundedCornerShape(14.dp),
                        onClick = {
                            save {
                                when (key) {
                                    "yaseen" -> repository.setQuran(date, yaseen = !completed)
                                    "waqiah" -> repository.setQuran(date, waqiah = !completed)
                                    else -> repository.setQuran(date, mulk = !completed)
                                }
                            }
                        },
                    ) { Text(if (completed) "Undo" else "✓  Confirm") }
                }
            }
        }

        EliteSectionTitle("Complete Quran")
        Card(shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
            Column(Modifier.fillMaxWidth().padding(17.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Full Khatam", style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold)
                    ElitePill("$khatamsToday recorded today", khatamsToday > 0)
                }
                Text("Only confirm after personally completing all 30 juz.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
                Button(
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    enabled = editable, onClick = { askKhatam = true },
                    shape = RoundedCornerShape(15.dp)
                ) { Text("Confirm full Quran completion") }
                if (khatamsToday > 0) {
                    OutlinedButton(
                        enabled = editable,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { save { repository.recordKhatam(date, completed = false) } },
                    ) { Text("Undo one khatam confirmation") }
                }
            }
        }

        EliteSectionTitle("Ramadan reading plan", "5 KHATAMS")
        Card(shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
            Column(Modifier.fillMaxWidth().padding(17.dp),
                verticalArrangement = Arrangement.spacedBy(9.dp)) {
                Text("Set the start and end dates from your confirmed calendar. No Hijri date is assumed.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
                OutlinedTextField(fromText, onValueChange = { fromText = it },
                    singleLine = true, shape = RoundedCornerShape(15.dp),
                    modifier = Modifier.fillMaxWidth(), label = { Text("Start  YYYY-MM-DD") })
                OutlinedTextField(untilText, onValueChange = { untilText = it },
                    singleLine = true, shape = RoundedCornerShape(15.dp),
                    modifier = Modifier.fillMaxWidth(), label = { Text("End  YYYY-MM-DD") })
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(15.dp),
                    onClick = {
                        val from = runCatching { LocalDate.parse(fromText.trim()) }.getOrNull()
                        val end = runCatching { LocalDate.parse(untilText.trim()) }.getOrNull()
                        if (from == null || end == null || end.isBefore(from) ||
                            end.isAfter(from.plusDays(35))) {
                            error = "Enter a valid date range (maximum 36 days)."
                        } else {
                            save { repository.setRamadanRange(from, end); range = from to end }
                        }
                    },
                ) { Text("Save Ramadan dates") }
                val currentRange = range
                if (currentRange != null) {
                    RamadanProgress(repository, currentRange)
                }
            }
        }
        Text("Reading volume and confirmed full khatams are separate records.",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(bottom = 25.dp))
    }

    if (askKhatam) {
        AlertDialog(
            onDismissRequest = { askKhatam = false },
            title = { Text("Confirm full Quran completion") },
            text = { Text("Have you personally completed all 30 juz? Reading-volume totals alone are not evidence of a complete khatam.") },
            confirmButton = {
                Button(onClick = {
                    askKhatam = false
                    save { repository.recordKhatam(date, completed = true) }
                }) { Text("Yes, confirm") }
            },
            dismissButton = {
                OutlinedButton(onClick = { askKhatam = false }) { Text("Cancel") }
            },
            shape = RoundedCornerShape(26.dp)
        )
    }
}

@Composable
private fun RamadanProgress(
    repository: WorshipRepository, range: Pair<LocalDate, LocalDate>,
) {
    val reading by remember(range) { repository.quranPeriod(range.first, range.second) }
        .collectAsState(initial = emptyList())
    val confirmed by remember(range) { repository.khatamPeriod(range.first, range.second) }
        .collectAsState(initial = emptyList())
    val units = reading.sumOf { it.halfJuzUnits }
    val khatams = confirmed.sumOf { it.delta }
    val days = ChronoUnit.DAYS.between(range.first, range.second).toInt() + 1
    val pace = (QuranRules.ramadanTargetHalfJuzUnits + days - 1) / days
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Your Ramadan progress", style = MaterialTheme.typography.titleMedium)
        Text("${units / 2.0} of 150 juz volume logged",
            style = MaterialTheme.typography.bodyMedium)
        LinearProgressIndicator(
            progress = { (units / 300f).coerceIn(0f, 1f) },
            modifier = Modifier.fillMaxWidth())
        ElitePill("$khatams / 5 confirmed full khatams", khatams >= 5)
        Text("Planning pace: ${pace / 2.0} juz/day over $days days. Volume alone does not verify a khatam.",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodySmall)
    }
}
