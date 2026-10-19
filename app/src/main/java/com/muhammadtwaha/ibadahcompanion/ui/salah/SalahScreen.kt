package com.muhammadtwaha.ibadahcompanion.ui.salah

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.sp
import com.muhammadtwaha.ibadahcompanion.data.WorshipRepository
import com.muhammadtwaha.ibadahcompanion.data.db.SalahRecord
import com.muhammadtwaha.ibadahcompanion.domain.worship.*
import com.muhammadtwaha.ibadahcompanion.ui.design.EliteChoice
import com.muhammadtwaha.ibadahcompanion.ui.design.ElitePageTitle
import com.muhammadtwaha.ibadahcompanion.ui.design.ElitePalette
import com.muhammadtwaha.ibadahcompanion.ui.design.ElitePill
import com.muhammadtwaha.ibadahcompanion.ui.shared.DaySwitcher
import com.muhammadtwaha.ibadahcompanion.ui.shared.rememberTrackingDate
import java.time.DayOfWeek
import java.time.LocalDate
import kotlinx.coroutines.launch

private enum class PrayerChoice(
    val label: String,
    val detail: String,
) {
    FIRST("With Jamaat · opening Takbeer", "Joined from the beginning and personally caught Takbeer-e-Ula."),
    LATE("With Jamaat · joined late", "Completed in congregation, without opening Takbeer."),
    UNSURE("With Jamaat · not sure", "Prayed with Jamaat; Takbeer detail is unknown."),
    ALONE("Prayed individually", "Completed without congregation."),
    MISSED("Missed prayer", "Record a prayer that was actually missed."),
    UNKNOWN("Clear / not recorded", "Return this prayer to unknown."),
}

@Composable
fun SalahScreen(repository: WorshipRepository) {
    val (date, setDate) = rememberTrackingDate()
    val records by remember(repository, date) { repository.salah(date) }
        .collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()
    var editing by remember { mutableStateOf<Prayer?>(null) }
    var saving by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    val editable = !date.isAfter(LocalDate.now())
    val completed = records.count { it.status == PrayerStatus.COMPLETED.name }

    Column(Modifier.fillMaxSize().padding(horizontal = 18.dp)) {
        ElitePageTitle(
            "Your five daily prayers", "Daily Salah",
            "Record what you actually performed, with confidence and clarity."
        )
        DaySwitcher(date, setDate)
        Card(
            shape = RoundedCornerShape(25.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(Modifier.fillMaxWidth().padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text("Today's prayer journal",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold)
                    ElitePill("$completed / 5 recorded", completed == 5)
                }
                LinearProgressIndicator(
                    progress = { completed / 5f },
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
                Text("Not recorded ≠ missed. Your entries are always editable.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall)
            }
        }
        if (!editable) Text(
            "Future date — recording is disabled.",
            modifier = Modifier.padding(vertical = 12.dp),
            color = MaterialTheme.colorScheme.error
        )
        if (error != null) Text(
            error.orEmpty(), modifier = Modifier.padding(vertical = 8.dp),
            color = MaterialTheme.colorScheme.error
        )
        Spacer(Modifier.height(14.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(Prayer.entries, key = { it.name }) { prayer ->
                val record = records.firstOrNull { it.prayer == prayer.name }
                PrayerSummary(
                    prayer = prayer,
                    date = date,
                    record = record,
                    editable = editable,
                    onEdit = { editing = prayer }
                )
            }
            item { Spacer(Modifier.height(28.dp)) }
        }
    }

    val editPrayer = editing
    if (editPrayer != null) {
        val current = records.firstOrNull { it.prayer == editPrayer.name }
        PrayerRecordDialog(
            prayer = editPrayer,
            date = date,
            current = current,
            saving = saving,
            onDismiss = { if (!saving) editing = null },
            onSave = { choice, midday ->
                if (!saving) {
                    saving = true
                    scope.launch {
                        try {
                            val status = when (choice) {
                                PrayerChoice.MISSED -> PrayerStatus.MISSED
                                PrayerChoice.UNKNOWN -> PrayerStatus.UNKNOWN
                                else -> PrayerStatus.COMPLETED
                            }
                            val mode = when (choice) {
                                PrayerChoice.FIRST, PrayerChoice.LATE, PrayerChoice.UNSURE -> Congregation.WITH_JAMAAT
                                PrayerChoice.ALONE -> Congregation.INDIVIDUAL
                                else -> Congregation.UNKNOWN
                            }
                            val takbeer = when (choice) {
                                PrayerChoice.FIRST -> Takbeer.ACHIEVED
                                PrayerChoice.LATE -> Takbeer.NOT_ACHIEVED
                                PrayerChoice.ALONE -> Takbeer.NOT_APPLICABLE
                                else -> Takbeer.UNKNOWN
                            }
                            val joining = when (choice) {
                                PrayerChoice.FIRST -> Joining.FROM_BEGINNING
                                PrayerChoice.LATE -> Joining.JOINED_LATE
                                else -> Joining.UNKNOWN
                            }
                            repository.setSalah(
                                date, editPrayer, status, mode, takbeer, joining,
                                if (status == PrayerStatus.COMPLETED) midday else MiddayMode.UNKNOWN
                            )
                            error = null
                            editing = null
                        } catch (e: Exception) {
                            error = "Unable to save: ${e.message ?: "please try again"}"
                        } finally {
                            saving = false
                        }
                    }
                }
            },
        )
    }
}

@Composable
private fun PrayerSummary(
    prayer: Prayer,
    date: LocalDate,
    record: SalahRecord?,
    editable: Boolean,
    onEdit: () -> Unit,
) {
    val number = Prayer.entries.indexOf(prayer) + 1
    val complete = record?.status == PrayerStatus.COMPLETED.name
    val missed = record?.status == PrayerStatus.MISSED.name
    val label = when {
        complete && record?.congregation == Congregation.WITH_JAMAAT.name ->
            if (record?.takbeer == Takbeer.ACHIEVED.name) "Jamaat · Takbeer-e-Ula"
            else if (record?.joining == Joining.JOINED_LATE.name) "Jamaat · joined late"
            else "Jamaat recorded"
        complete -> "Prayed individually"
        missed -> "Marked missed"
        else -> "Awaiting your record"
    }
    val name = if (prayer == Prayer.DHUHR &&
        date.dayOfWeek == DayOfWeek.FRIDAY &&
        record?.middayMode == MiddayMode.JUMUAH.name) "Jumu'ah"
        else prayer.name.lowercase().replaceFirstChar { it.uppercase() }
    val arabicName = when (prayer) {
        Prayer.FAJR -> "الفجر"
        Prayer.DHUHR -> "الظهر"
        Prayer.ASR -> "العصر"
        Prayer.MAGHRIB -> "المغرب"
        Prayer.ISHA -> "العشاء"
    }
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Row(
            Modifier.fillMaxWidth().padding(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Column(
                Modifier.background(
                    if (complete) ElitePalette.Sage else MaterialTheme.colorScheme.surfaceVariant,
                    RoundedCornerShape(17.dp)
                ).padding(horizontal = 13.dp, vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text("$number", fontSize = 17.sp, fontWeight = FontWeight.Bold,
                    color = if (complete) ElitePalette.Pine else MaterialTheme.colorScheme.primary)
                Text("FARD", fontSize = 8.sp, letterSpacing = 1.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Column(Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(name, style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold)
                    Text(arabicName, style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Text(label, style = MaterialTheme.typography.bodySmall,
                    color = if (missed) MaterialTheme.colorScheme.error else
                        MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp))
            }
            OutlinedButton(
                enabled = editable,
                onClick = onEdit,
                shape = RoundedCornerShape(13.dp),
            ) {
                Text(if (record == null || record.status == PrayerStatus.UNKNOWN.name)
                    "Record" else "Edit")
            }
        }
    }
}

@Composable
private fun PrayerRecordDialog(
    prayer: Prayer,
    date: LocalDate,
    current: SalahRecord?,
    saving: Boolean,
    onDismiss: () -> Unit,
    onSave: (PrayerChoice, MiddayMode) -> Unit,
) {
    var chosen by remember(prayer, date) { mutableStateOf<PrayerChoice?>(null) }
    var midday by remember(prayer, date) {
        mutableStateOf(
            if (prayer == Prayer.DHUHR)
                runCatching { MiddayMode.valueOf(current?.middayMode ?: "") }
                    .getOrDefault(MiddayMode.UNKNOWN)
            else MiddayMode.UNKNOWN
        )
    }
    val friday = prayer == Prayer.DHUHR && date.dayOfWeek == DayOfWeek.FRIDAY
    val isCompletion = chosen != null &&
        chosen != PrayerChoice.MISSED && chosen != PrayerChoice.UNKNOWN
    val allowed = chosen != null && (!friday || !isCompletion || midday != MiddayMode.UNKNOWN)
    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(28.dp),
        title = {
            Column {
                Text("Record ${prayer.name.lowercase().replaceFirstChar { it.uppercase() }}",
                    style = MaterialTheme.typography.headlineSmall)
                Text("Choose what really happened — you can correct it later.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 5.dp))
            }
        },
        text = {
            Column(
                Modifier.fillMaxWidth().verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                if (friday) {
                    Text("Friday midday: choose what you performed",
                        style = MaterialTheme.typography.labelMedium)
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        FilterChip(selected = midday == MiddayMode.JUMUAH,
                            onClick = { midday = MiddayMode.JUMUAH },
                            label = { Text("Jumu'ah") })
                        FilterChip(selected = midday == MiddayMode.DHUHR,
                            onClick = { midday = MiddayMode.DHUHR },
                            label = { Text("Dhuhr") })
                    }
                    if (midday == MiddayMode.UNKNOWN) {
                        Text("Please select a Friday prayer before confirming completion.",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodySmall)
                    }
                }
                PrayerChoice.entries.forEach { option ->
                    EliteChoice(
                        label = option.label,
                        detail = option.detail,
                        selected = chosen == option,
                        onClick = { chosen = option },
                        enabled = !saving,
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { chosen?.let { onSave(it, midday) } },
                enabled = allowed && !saving,
                shape = RoundedCornerShape(15.dp),
            ) { Text(if (saving) "Saving..." else "Confirm record") }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                enabled = !saving,
                shape = RoundedCornerShape(15.dp),
            ) { Text("Cancel") }
        },
    )
}
