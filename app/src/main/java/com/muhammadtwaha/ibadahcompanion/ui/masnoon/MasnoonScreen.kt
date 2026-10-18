package com.muhammadtwaha.ibadahcompanion.ui.masnoon

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.selection.SelectionContainer
import com.muhammadtwaha.ibadahcompanion.data.MasnoonRepository
import com.muhammadtwaha.ibadahcompanion.data.db.DailySunnahCheck
import com.muhammadtwaha.ibadahcompanion.data.db.MunajatManzil
import com.muhammadtwaha.ibadahcompanion.data.db.RecitationTotal
import com.muhammadtwaha.ibadahcompanion.domain.masnoon.Evidence
import com.muhammadtwaha.ibadahcompanion.domain.masnoon.MasnoonCatalog
import com.muhammadtwaha.ibadahcompanion.domain.masnoon.Practice
import com.muhammadtwaha.ibadahcompanion.domain.masnoon.SunnahHabit
import com.muhammadtwaha.ibadahcompanion.domain.masnoon.Window
import com.muhammadtwaha.ibadahcompanion.domain.worship.Prayer
import com.muhammadtwaha.ibadahcompanion.ui.shared.DaySwitcher
import com.muhammadtwaha.ibadahcompanion.ui.shared.rememberTrackingDate
import java.time.LocalDate
import kotlinx.coroutines.launch

private enum class Section(val title: String) {
    MORNING("Morning"), EVENING("Evening"), OCCASION("Daily duas"),
    AFTER_SALAH("After Salah"), SUNNAH("Sunnah habits"), MUNAJAT("Munajat")
}

@Composable
fun MasnoonScreen(repository: MasnoonRepository) {
    val (date, changeDate) = rememberTrackingDate()
    val totals by remember(repository, date) { repository.totals(date) }
        .collectAsState(initial = emptyList())
    val habits by remember(repository, date) { repository.habits(date) }
        .collectAsState(initial = emptyList())
    val munajat by remember(repository, date) { repository.munajat(date) }
        .collectAsState(initial = null)

    var section by remember { mutableStateOf(Section.MORNING) }
    var prayer by remember { mutableStateOf(Prayer.FAJR) }
    var selectedText by remember { mutableStateOf<Practice?>(null) }
    var error by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val editable = !date.isAfter(LocalDate.now())

    fun adjust(item: Practice, slot: Window, difference: Int) {
        if (!editable) return
        scope.launch {
            try {
                repository.adjust(date, item.id, slot, difference,
                    if (slot == Window.AFTER_SALAH) prayer else null)
                error = ""
            } catch (e: IllegalArgumentException) {
                error = e.message ?: "Correction rejected"
            }
        }
    }

    Column(Modifier.fillMaxSize().padding(horizontal = 12.dp)) {
        Text("Masnoon Duas & Sunnah", style = MaterialTheme.typography.titleLarge)
        DaySwitcher(date, changeDate)
        if (!editable) Text("Future-date recording is disabled.")
        Text("Source-linked starter collection, not a complete list of every Prophetic practice.",
            style = MaterialTheme.typography.bodySmall)
        if (error.isNotBlank()) Text(error, color = MaterialTheme.colorScheme.error)

        Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            Section.entries.forEach { option ->
                OutlinedButton(onClick = { section = option }) {
                    Text((if (section == option) "✓ " else "") + option.title)
                }
            }
        }

        when (section) {
            Section.MORNING, Section.EVENING, Section.OCCASION, Section.AFTER_SALAH -> {
                val window = when (section) {
                    Section.MORNING -> Window.MORNING
                    Section.EVENING -> Window.EVENING
                    Section.OCCASION -> Window.OCCASION
                    else -> Window.AFTER_SALAH
                }
                val source = MasnoonCatalog.forWindow(window)
                val safe = source.filterNot { it.disputed }
                val completed = safe.count { item ->
                    val key = if (window == Window.AFTER_SALAH)
                        "AFTER_SALAH_${prayer.name}" else window.name
                    total(totals, item.id, key) >= item.target
                }
                if (window == Window.AFTER_SALAH) {
                    Row(Modifier.horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                        Prayer.entries.forEach { p ->
                            OutlinedButton(onClick = { prayer = p }) {
                                Text((if (prayer == p) "✓ " else "") + p.name)
                            }
                        }
                    }
                }
                Text("$completed / ${safe.size} self-confirmed entries",
                    style = MaterialTheme.typography.titleMedium)
                Text(if (window == Window.OCCASION)
                    "Only tick the dua after its actual occasion. A daily occurrence is not presumed."
                    else if (window == Window.AFTER_SALAH)
                    "Independent after-${prayer.name} adhkar; not added to your separate Salawat/1,200 Istighfar counters."
                    else "These reminders are optional personal check-ins, never a measure of spiritual standing.",
                    style = MaterialTheme.typography.bodySmall)

                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(source, key = { it.id }) { item ->
                        val key = if (window == Window.AFTER_SALAH)
                            "AFTER_SALAH_${prayer.name}" else window.name
                        PracticeCard(item, total(totals, item.id, key), editable,
                            onRead = { selectedText = item },
                            onAdjust = { step -> adjust(item, window, step) })
                    }
                }
            }
            Section.SUNNAH -> {
                Text("Habits are opportunity-based, not daily obligatory debts.",
                    style = MaterialTheme.typography.bodySmall)
                val practiced = habits.filter { it.completed }.map { it.habitId }.toSet()
                Text("${practiced.size} voluntarily logged habits")
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(MasnoonCatalog.habits, key = { it.id }) { item ->
                        val yes = item.id in practiced
                        Card(Modifier.fillMaxWidth()) {
                            Column(Modifier.padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                Text(item.title, style = MaterialTheme.typography.titleMedium)
                                Text(item.reference, style = MaterialTheme.typography.bodySmall)
                                Text(item.note, style = MaterialTheme.typography.bodySmall)
                                Text(if (yes) "Recorded practiced" else "Not recorded")
                                OutlinedButton(enabled = editable,
                                    onClick = {
                                        scope.launch {
                                            try {
                                                repository.saveHabit(date, item.id, !yes)
                                                error = ""
                                            } catch (e: IllegalArgumentException) {
                                                error = e.message ?: "Invalid habit edit"
                                            }
                                        }
                                    }) { Text(if (yes) "Undo" else "Record practiced") }
                            }
                        }
                    }
                }
            }
            Section.MUNAJAT -> {
                MunajatCard(date, munajat, editable) { manzil, edition, completed ->
                    scope.launch {
                        try {
                            repository.saveMunajat(date, manzil, edition, completed)
                            error = ""
                        } catch (e: IllegalArgumentException) {
                            error = e.message ?: "Invalid manzil"
                        }
                    }
                }
            }
        }
    }

    selectedText?.let { item ->
        AlertDialog(
            onDismissRequest = { selectedText = null },
            title = { Text(item.title) },
            text = {
                Column(Modifier.verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    ArabicBody(item.arabic)
                    Text("Source: ${item.reference}")
                    SelectionContainer { Text(item.sourceUrl) }
                    Text("Source status: ${item.evidence}")
                    Text(item.note)
                    if (item.disputed) {
                        Text("CAUTION: the cited 7× report is graded fabricated by one named critic. Do not treat it as an authenticated Prophetic instruction.",
                            color = MaterialTheme.colorScheme.error)
                    }
                    Text("Arabic spelling and variations need independent qualified editorial review.",
                        style = MaterialTheme.typography.bodySmall)
                }
            },
            confirmButton = { Button(onClick = { selectedText = null }) { Text("Close") } },
        )
    }
}

private fun total(totals: List<RecitationTotal>, itemId: String, slot: String): Long =
    totals.firstOrNull { it.itemId == itemId && it.prayer == slot }?.total ?: 0L

@Composable
private fun ArabicBody(arabic: String) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        SelectionContainer {
            Text(arabic,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Right,
                modifier = Modifier.fillMaxWidth().padding(6.dp))
        }
    }
}

@Composable
private fun PracticeCard(
    item: Practice, done: Long, editable: Boolean, onRead: () -> Unit,
    onAdjust: (Int) -> Unit,
) {
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(item.title, style = MaterialTheme.typography.titleMedium)
            Text("$done / ${item.target} · ${item.reference}",
                style = MaterialTheme.typography.bodySmall)
            if (item.disputed) {
                Text("DISPUTED REPORT — optional, not counted in core checklist",
                    color = MaterialTheme.colorScheme.error)
            }
            LinearProgressIndicator(
                progress = { (done.toFloat() / item.target).coerceIn(0f, 1f) },
                modifier = Modifier.fillMaxWidth(),
            )
            Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                OutlinedButton(onClick = onRead) { Text("Read Arabic / source") }
                Button(enabled = editable, onClick = { onAdjust(1) }) { Text("+1") }
                Button(enabled = editable, onClick = { onAdjust(item.target) }) {
                    Text("+${item.target}")
                }
                OutlinedButton(enabled = editable && done > 0,
                    onClick = { onAdjust(-1) }) { Text("−1") }
                OutlinedButton(enabled = editable && done >= item.target,
                    onClick = { onAdjust(-item.target) }) { Text("−${item.target}") }
            }
        }
    }
}

@Composable
private fun MunajatCard(
    date: LocalDate, saved: MunajatManzil?, editable: Boolean,
    onSave: (Int, String, Boolean) -> Unit,
) {
    var manzil by remember(date, saved?.manzil) { mutableIntStateOf(saved?.manzil ?: 0) }
    var edition by remember(date, saved?.edition) { mutableStateOf(saved?.edition ?: "") }
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Munajat-e-Maqbool — daily manzil", style = MaterialTheme.typography.titleLarge)
        Text("Select one of your edition's 7 daily sections. Manzil labels/divisions may vary. No book text is included or inferred.")
        Text("Today: ${if (saved?.completed == true) "Completed" else "Not recorded"}")
        Row(Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            (1..7).forEach { day ->
                OutlinedButton(onClick = { manzil = day }) {
                    Text(if (manzil == day) "✓ $day" else "$day")
                }
            }
        }
        OutlinedTextField(edition,
            onValueChange = { if (it.length <= 100) edition = it },
            label = { Text("Edition / weekly schedule (optional)") },
            modifier = Modifier.fillMaxWidth(),
        )
        Button(enabled = editable && manzil > 0,
            onClick = { onSave(manzil, edition, true) }) {
            Text("Mark manzil completed")
        }
        OutlinedButton(enabled = editable && manzil > 0 && saved?.completed == true,
            onClick = { onSave(manzil, edition, false) }) { Text("Undo completion") }
        HorizontalDivider()
        Text("This tracks your reading from your own edition. It does not claim a special reward or impose a binding daily obligation.")
    }
}
