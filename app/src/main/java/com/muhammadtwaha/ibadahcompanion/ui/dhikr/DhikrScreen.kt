package com.muhammadtwaha.ibadahcompanion.ui.dhikr

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import com.muhammadtwaha.ibadahcompanion.data.DhikrRepository
import com.muhammadtwaha.ibadahcompanion.data.MasnoonRepository
import com.muhammadtwaha.ibadahcompanion.ui.masnoon.MasnoonScreen
import com.muhammadtwaha.ibadahcompanion.data.db.DalailDaily
import com.muhammadtwaha.ibadahcompanion.data.db.RecitationTotal
import com.muhammadtwaha.ibadahcompanion.domain.dhikr.SalawatCatalog
import com.muhammadtwaha.ibadahcompanion.domain.dhikr.SalawatText
import com.muhammadtwaha.ibadahcompanion.domain.worship.Prayer
import com.muhammadtwaha.ibadahcompanion.ui.shared.DaySwitcher
import com.muhammadtwaha.ibadahcompanion.ui.shared.rememberTrackingDate
import java.time.LocalDate
import kotlinx.coroutines.launch

private enum class DhikrTab { DAILY, AFTER_FARD, DALAIL, MASNOON }

@Composable
fun DhikrScreen(repository: DhikrRepository, masnoonRepository: MasnoonRepository) {
    val (date, setDate) = rememberTrackingDate()
    val totals by remember(repository, date) {
        repository.totals(date)
    }.collectAsState(initial = emptyList())
    val dalail by remember(repository, date) {
        repository.dalail(date)
    }.collectAsState(initial = null)
    val scope = rememberCoroutineScope()
    var currentTab by remember { mutableStateOf(DhikrTab.DAILY) }
    var prayer by remember { mutableStateOf(Prayer.FAJR) }
    var openText by remember { mutableStateOf<SalawatText?>(null) }
    var error by remember { mutableStateOf<String?>(null) }
    val enabled = !date.isAfter(LocalDate.now())

    fun adjust(itemId: String, prayerKey: Prayer?, delta: Int) {
        if (!enabled) return
        scope.launch {
            try {
                if (prayerKey == null) repository.addDaily(date, itemId, delta)
                else repository.addAfterPrayer(date, prayerKey, itemId, delta)
                error = null
            } catch (e: IllegalArgumentException) {
                error = e.message ?: "Invalid correction"
            }
        }
    }

    // Give the new catalog the full screen instead of nesting scrollable screens
    // inside the old daily-counter column (which otherwise clips on small phones).
    if (currentTab == DhikrTab.MASNOON) {
        Column(Modifier.fillMaxSize()) {
            OutlinedButton(onClick = { currentTab = DhikrTab.DAILY }) {
                Text("← Back to Salawat counters")
            }
            Box(Modifier.weight(1f)) { MasnoonScreen(masnoonRepository) }
        }
        return
    }

    Column(Modifier.fillMaxSize().padding(horizontal = 14.dp)) {
        Text("Salawat & Adhkar", style = MaterialTheme.typography.titleLarge)
        DaySwitcher(date, setDate)
        if (!enabled) Text("Future dates can be viewed, but cannot be recorded.")
        if (error != null) Text(error!!, color = MaterialTheme.colorScheme.error)

        Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(7.dp)) {
            listOf(
                DhikrTab.DAILY to "Daily goals",
                DhikrTab.AFTER_FARD to "After each Fard",
                DhikrTab.DALAIL to "Dala'il manzil",
                DhikrTab.MASNOON to "Masnoon & Munajat",
            ).forEach { (tab, label) ->
                OutlinedButton(onClick = { currentTab = tab }) {
                    Text((if (currentTab == tab) "✓ " else "") + label)
                }
            }
        }

        when (currentTab) {
            DhikrTab.DAILY -> {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    item {
                        Text("Independent personal goals. After-Fard Salawat counters are not automatically added to the daily short-Salawat count.")
                        Text("Tap + only after reciting. Corrections subtract previously recorded repetitions.")
                    }
                    item {
                        DailyCard(
                            "Daily Salawat", SalawatCatalog.DAILY_SHORT_ARABIC,
                            SalawatCatalog.DAILY_SHORT_ID,
                            SalawatCatalog.DAILY_SHORT_TARGET,
                            count(totals, SalawatCatalog.DAILY_SHORT_ID),
                            enabled, { id, delta -> adjust(id, null, delta) }
                        )
                    }
                    item {
                        SimpleDailyCounter(
                            "Istighfar", SalawatCatalog.ISTIGHFAR_ARABIC,
                            DhikrRepository.DAILY_ISTIGHFAR, SalawatCatalog.DAILY_ISTIGHFAR_TARGET,
                            count(totals, DhikrRepository.DAILY_ISTIGHFAR), enabled,
                        ) { id, delta -> adjust(id, null, delta) }
                    }
                    item {
                        SimpleDailyCounter(
                            "Bismillahir-Rahmanir-Rahim", SalawatCatalog.BISMILLAH_ARABIC,
                            DhikrRepository.DAILY_BISMILLAH, SalawatCatalog.DAILY_BISMILLAH_TARGET,
                            count(totals, DhikrRepository.DAILY_BISMILLAH), enabled,
                        ) { id, delta -> adjust(id, null, delta) }
                    }
                }
            }
            DhikrTab.AFTER_FARD -> {
                val achievements = SalawatCatalog.afterFard.count {
                    count(totals, it.id, prayer.name) >= SalawatCatalog.AFTER_FARD_TARGET_PER_TEXT
                }
                Column {
                    Text("Each supplied Salawat: personal target 10 after each Fard. User-supplied text; sources and historical attributions are not yet authenticated.")
                    Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                        Prayer.entries.forEach { candidate ->
                            OutlinedButton(onClick = { prayer = candidate }) {
                                Text(if (prayer == candidate) "✓ " + candidate.name else candidate.name)
                            }
                        }
                    }
                    Text("$achievements / ${SalawatCatalog.afterFard.size} formulations at target for ${prayer.name}")
                    val total = Prayer.entries.sumOf { p ->
                        SalawatCatalog.afterFard.count {
                            count(totals, it.id, p.name) >= SalawatCatalog.AFTER_FARD_TARGET_PER_TEXT
                        }
                    }
                    Text("All five prayer sessions: $total / ${SalawatCatalog.afterFard.size * 5} targets")
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(SalawatCatalog.afterFard, key = { it.id }) { item ->
                            SalawatCard(item, prayer, count(totals, item.id, prayer.name),
                                enabled, onRead = { openText = item }) { delta ->
                                adjust(item.id, prayer, delta)
                            }
                        }
                    }
                }
            }
            DhikrTab.MASNOON -> { /* full-screen view handled above */ }
            DhikrTab.DALAIL -> {
                DalailManzilCard(date, dalail, enabled,
                    onSave = { manzil, edition, completed ->
                        scope.launch {
                            try {
                                repository.setDalail(date, manzil, completed, edition)
                                error = null
                            } catch (e: IllegalArgumentException) {
                                error = e.message
                            }
                        }
                    })
            }
        }
    }

    openText?.let { selected ->
        AlertDialog(
            onDismissRequest = { openText = null },
            title = { Text(selected.title) },
            text = {
                Column(Modifier.verticalScroll(rememberScrollState())) {
                    Text("User-supplied text • attribution/wording not yet verified",
                        style = MaterialTheme.typography.bodySmall)
                    ArabicText(selected.arabic)
                }
            },
            confirmButton = {
                Button(onClick = { openText = null }) { Text("Close") }
            },
        )
    }
}

private fun count(totals: List<RecitationTotal>, itemId: String, prayer: String = ""): Long =
    totals.firstOrNull { it.itemId == itemId && it.prayer == prayer }?.total ?: 0

@Composable
private fun ArabicText(text: String) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Text(
            text, modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Right,
        )
    }
}

@Composable
private fun SimpleDailyCounter(
    title: String, arabic: String, id: String, target: Int, current: Long,
    enabled: Boolean, onAdjust: (String, Int) -> Unit,
) {
    DailyCard(title, arabic, id, target, current, enabled, onAdjust)
}

@Composable
private fun DailyCard(
    title: String, arabic: String, id: String, target: Int, current: Long,
    enabled: Boolean, onAdjust: (String, Int) -> Unit,
) {
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            ArabicText(arabic)
            Text("$current / $target completed")
            LinearProgressIndicator(
                progress = { (current.toFloat() / target).coerceIn(0f, 1f) },
                modifier = Modifier.fillMaxWidth(),
            )
            Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                listOf(1, 10, 100).forEach { step ->
                    Button(enabled = enabled, onClick = { onAdjust(id, step) }) { Text("+$step") }
                }
                listOf(1, 10).forEach { step ->
                    OutlinedButton(enabled = enabled && current >= step,
                        onClick = { onAdjust(id, -step) }) { Text("−$step") }
                }
            }
        }
    }
}

@Composable
private fun SalawatCard(
    text: SalawatText, prayer: Prayer, count: Long,
    enabled: Boolean, onRead: () -> Unit, onAdjust: (Int) -> Unit,
) {
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(7.dp)) {
            Text(text.title, style = MaterialTheme.typography.titleMedium)
            Text("$count / ${SalawatCatalog.AFTER_FARD_TARGET_PER_TEXT} • after ${prayer.name}")
            LinearProgressIndicator(
                progress = { (count / SalawatCatalog.AFTER_FARD_TARGET_PER_TEXT.toFloat()).coerceIn(0f, 1f) },
                modifier = Modifier.fillMaxWidth(),
            )
            Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(onClick = onRead) { Text("Read Arabic") }
                Button(enabled = enabled, onClick = { onAdjust(1) }) { Text("+1") }
                Button(enabled = enabled, onClick = { onAdjust(10) }) { Text("+10") }
                OutlinedButton(enabled = enabled && count > 0, onClick = { onAdjust(-1) }) { Text("−1") }
                OutlinedButton(enabled = enabled && count >= 10, onClick = { onAdjust(-10) }) { Text("−10") }
            }
        }
    }
}

@Composable
private fun DalailManzilCard(
    date: LocalDate, saved: DalailDaily?, enabled: Boolean,
    onSave: (Int, String, Boolean) -> Unit,
) {
    var selected by remember(date, saved?.manzil) { mutableIntStateOf(saved?.manzil ?: 0) }
    var edition by remember(date, saved?.edition) { mutableStateOf(saved?.edition ?: "") }
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Dala'il al-Khayrat — daily manzil", style = MaterialTheme.typography.titleLarge)
        Text("Choose the manzil from your own edition. Different editions may divide the weekly readings differently. This tracker does not reproduce the book's text.")
        Text("Saved for $date: ${if (saved?.completed == true) "Completed" else "Not yet completed"}")
        Text("Select manzil 1–7:")
        Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            (1..7).forEach { n ->
                OutlinedButton(onClick = { selected = n }) {
                    Text(if (selected == n) "✓ $n" else "$n")
                }
            }
        }
        OutlinedTextField(value = edition, onValueChange = { if (it.length <= 100) edition = it },
            label = { Text("Edition / schedule (optional)") }, modifier = Modifier.fillMaxWidth())
        Button(enabled = enabled && selected != 0,
            onClick = { onSave(selected, edition, true) }) { Text("Record today's manzil completed") }
        OutlinedButton(enabled = enabled && saved?.completed == true && selected != 0,
            onClick = { onSave(selected, edition, false) }) { Text("Correct: mark incomplete") }
        Text("This is a voluntary personal checklist; no religious obligation or special guaranteed merit is inferred.")
    }
}
