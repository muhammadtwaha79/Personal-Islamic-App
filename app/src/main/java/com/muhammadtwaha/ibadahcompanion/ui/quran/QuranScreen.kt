package com.muhammadtwaha.ibadahcompanion.ui.quran

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.muhammadtwaha.ibadahcompanion.data.WorshipRepository
import com.muhammadtwaha.ibadahcompanion.domain.worship.QuranRules
import com.muhammadtwaha.ibadahcompanion.ui.shared.DaySwitcher
import com.muhammadtwaha.ibadahcompanion.ui.shared.rememberTrackingDate
import java.time.LocalDate
import kotlinx.coroutines.launch

@Composable
fun QuranScreen(repository: WorshipRepository) {
    val (date, setDate) = rememberTrackingDate()
    val day by remember(date) { repository.quran(date) }.collectAsState(initial = null)
    val scope = rememberCoroutineScope()
    var fromText by remember { mutableStateOf("") }
    var untilText by remember { mutableStateOf("") }
    var range by remember { mutableStateOf<Pair<LocalDate, LocalDate>?>(null) }
    var error by remember { mutableStateOf("") }
    var confirmKhatam by remember { mutableStateOf(false) }
    val dayKhatams by remember(date) {
        repository.khatamPeriod(date, date)
    }.collectAsState(initial = emptyList())
    val dayKhatamCount = dayKhatams.sumOf { it.delta }
    val canRecord = !date.isAfter(LocalDate.now())
    LaunchedEffect(repository) {
        repository.ramadanRange()?.let {
            fromText = it.first.toString()
            untilText = it.second.toString()
            range = it
        }
    }

    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text("Quran Tilawat — 20 October 2026 onward")
        DaySwitcher(date, setDate)
        if (!canRecord) Text("Future date — recording unlocks on that day.")
        Text("Daily target: half para (0.5 juz)")
        val halfUnits = day?.halfJuzUnits ?: 0
        Text("Recorded reading: ${halfUnits / 2.0} juz")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(enabled = canRecord, onClick = { scope.launch { repository.setQuran(date,
                newHalfUnits = (halfUnits - 1).coerceAtLeast(0)) } }) { Text("− ½ para") }
            Button(enabled = canRecord, onClick = { scope.launch { repository.setQuran(date,
                newHalfUnits = (halfUnits + 1).coerceAtMost(60)) } }) { Text("+ ½ para") }
        }
        Text(if (halfUnits >= 1) "Daily reading target reached" else "Daily reading target pending")
        listOf(
            Triple("Surah Yaseen", day?.yaseen ?: false, "yaseen"),
            Triple("Surah Waqiah", day?.waqiah ?: false, "waqiah"),
            Triple("Surah Mulk", day?.mulk ?: false, "mulk"),
        ).forEach { (title, checked, key) ->
            Card(Modifier.fillMaxWidth()) {
                Row(Modifier.fillMaxWidth().padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(title + if (checked) " — completed" else " — pending")
                    OutlinedButton(enabled = canRecord, onClick = {
                        scope.launch {
                            when (key) {
                                "yaseen" -> repository.setQuran(date, yaseen = !checked)
                                "waqiah" -> repository.setQuran(date, waqiah = !checked)
                                else -> repository.setQuran(date, mulk = !checked)
                            }
                        }
                    }) { Text(if (checked) "Undo" else "Record") }
                }
            }
        }
        Text("Full Quran completions logged on $date: $dayKhatamCount")
        Button(enabled = canRecord, onClick = { confirmKhatam = true }) { Text("Record full Quran khatam") }
        OutlinedButton(enabled = canRecord && dayKhatamCount > 0,
            onClick = { scope.launch { repository.recordKhatam(date, completed = false) } }) {
            Text("Undo one khatam record")
        }
        if (confirmKhatam) {
            AlertDialog(
                onDismissRequest = { confirmKhatam = false },
                title = { Text("Confirm full Quran completion") },
                text = { Text("Only confirm after personally finishing all 30 juz of a full Quran. Reading-volume totals alone are not a khatam.") },
                confirmButton = { Button(onClick = {
                    confirmKhatam = false
                    scope.launch { repository.recordKhatam(date, completed = true) }
                }) { Text("Confirm completed") } },
                dismissButton = { OutlinedButton(onClick = { confirmKhatam = false }) { Text("Cancel") } },
            )
        }
        Text("Ramadan planner — 5 Quran khatams (150 juz)")
        Text("Set Ramadan dates manually after confirming the local lunar calendar. No dates assumed.")
        OutlinedTextField(fromText, onValueChange = { fromText = it }, label = { Text("Start: YYYY-MM-DD") },
            modifier = Modifier.fillMaxWidth(), singleLine = true)
        OutlinedTextField(untilText, onValueChange = { untilText = it }, label = { Text("End: YYYY-MM-DD") },
            modifier = Modifier.fillMaxWidth(), singleLine = true)
        Button(onClick = {
            val first = runCatching { LocalDate.parse(fromText.trim()) }.getOrNull()
            val last = runCatching { LocalDate.parse(untilText.trim()) }.getOrNull()
            if (first == null || last == null || last.isBefore(first) || last.isAfter(first.plusDays(35))) {
                error = "Enter a valid manually verified range (maximum 36 days)."
            } else {
                error = ""
                scope.launch { repository.setRamadanRange(first, last); range = first to last }
            }
        }) { Text("Save Ramadan dates") }
        if (error.isNotBlank()) Text(error)
        if (range != null) {
            val currentRange = range!!
            val daily by remember(currentRange) {
                repository.quranPeriod(currentRange.first, currentRange.second)
            }.collectAsState(initial = emptyList())
            val units = daily.sumOf { it.halfJuzUnits }
            val percent = QuranRules.progressPercent(units, QuranRules.ramadanTargetHalfJuzUnits)
            val khatams by remember(currentRange) {
                repository.khatamPeriod(currentRange.first, currentRange.second)
            }.collectAsState(initial = emptyList())
            val verifiedKhatams = khatams.sumOf { it.delta }
            val days = java.time.temporal.ChronoUnit.DAYS.between(
                currentRange.first, currentRange.second).toInt() + 1
            val dailyRamadanHalfUnits = (QuranRules.ramadanTargetHalfJuzUnits + days - 1) / days
            Text("Ramadan range: ${currentRange.first} – ${currentRange.second}")
            Text("Reading: ${units / 2.0} / 150 juz ($percent%)")
            Text("Confirmed full khatams: $verifiedKhatams / 5")
            Text("Planning pace: ${dailyRamadanHalfUnits / 2.0} juz per day across $days days.")
            Text("Reading-volume equivalents: ${units / 60} full Quran lengths; not a confirmed khatam.")
            Text("Progress uses your manual date range; no moon sighting is inferred.")
        }
        Text("These are personal reading logs, not a claim about spiritual rank or guaranteed reward.")
    }
}
