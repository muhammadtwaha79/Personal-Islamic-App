package com.muhammadtwaha.ibadahcompanion.ui.dhikr

import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.item
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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.muhammadtwaha.ibadahcompanion.data.DhikrRepository
import com.muhammadtwaha.ibadahcompanion.data.db.RecitationTotal
import com.muhammadtwaha.ibadahcompanion.domain.dhikr.SalawatCatalog
import com.muhammadtwaha.ibadahcompanion.domain.dhikr.SalawatText
import com.muhammadtwaha.ibadahcompanion.domain.worship.Prayer
import com.muhammadtwaha.ibadahcompanion.ui.design.ElitePageTitle
import com.muhammadtwaha.ibadahcompanion.ui.design.ElitePill
import com.muhammadtwaha.ibadahcompanion.ui.shared.DaySwitcher
import com.muhammadtwaha.ibadahcompanion.ui.shared.rememberTrackingDate
import java.time.LocalDate
import kotlinx.coroutines.launch

private enum class CounterSection { DAILY, AFTER_FARD }

@Composable
fun DhikrScreen(repository: DhikrRepository) {
    val (date, setDate) = rememberTrackingDate()
    val totals by remember(repository, date) { repository.totals(date) }
        .collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()
    var section by remember { mutableStateOf(CounterSection.DAILY) }
    var prayer by remember { mutableStateOf(Prayer.FAJR) }
    var opened by remember { mutableStateOf<SalawatText?>(null) }
    var error by remember { mutableStateOf("") }
    val editable = !date.isAfter(LocalDate.now())

    fun adjust(id: String, selectedPrayer: Prayer?, delta: Int) {
        if (!editable) return
        scope.launch {
            try {
                if (selectedPrayer == null) repository.addDaily(date, id, delta)
                else repository.addAfterPrayer(date, selectedPrayer, id, delta)
                error = ""
            } catch (e: Exception) {
                error = e.message ?: "Unable to save correction"
            }
        }
    }

    Column(Modifier.fillMaxSize().padding(horizontal = 18.dp)) {
        ElitePageTitle(
            "Quiet remembrance", "Salawat & Dhikr",
            "Count only what you actually recite. Your progress remains private."
        )
        DaySwitcher(date, setDate)
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            FilterChip(
                selected = section == CounterSection.DAILY,
                onClick = { section = CounterSection.DAILY },
                label = { Text("Daily goals") },
            )
            FilterChip(
                selected = section == CounterSection.AFTER_FARD,
                onClick = { section = CounterSection.AFTER_FARD },
                label = { Text("After each Farz") },
            )
        }
        if (error.isNotBlank()) Text(error, color = MaterialTheme.colorScheme.error)
        if (section == CounterSection.DAILY) {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(13.dp)) {
                item {
                    Text(
                        "Each goal is independent. After-Farz Salawat do not automatically increase your short-Salawat daily count.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 8.dp))
                }
                item {
                    CounterCard(
                        "Daily Salawat",
                        SalawatCatalog.DAILY_SHORT_ARABIC,
                        SalawatCatalog.DAILY_SHORT_TARGET,
                        count(totals, SalawatCatalog.DAILY_SHORT_ID), editable,
                        onAdjust = { delta -> adjust(SalawatCatalog.DAILY_SHORT_ID, null, delta) }
                    )
                }
                item {
                    CounterCard(
                        "Istighfar", SalawatCatalog.ISTIGHFAR_ARABIC,
                        SalawatCatalog.DAILY_ISTIGHFAR_TARGET,
                        count(totals, DhikrRepository.DAILY_ISTIGHFAR), editable,
                        onAdjust = { delta -> adjust(DhikrRepository.DAILY_ISTIGHFAR, null, delta) }
                    )
                }
                item {
                    CounterCard(
                        "Bismillahir-Rahmanir-Rahim",
                        SalawatCatalog.BISMILLAH_ARABIC,
                        SalawatCatalog.DAILY_BISMILLAH_TARGET,
                        count(totals, DhikrRepository.DAILY_BISMILLAH), editable,
                        onAdjust = { delta -> adjust(DhikrRepository.DAILY_BISMILLAH, null, delta) }
                    )
                }
                item { Spacer(Modifier.height(18.dp)) }
            }
        } else {
            val reached = SalawatCatalog.afterFard.count {
                count(totals, it.id, prayer.name) >= SalawatCatalog.AFTER_FARD_TARGET_PER_TEXT
            }
            Text(
                "$reached / ${SalawatCatalog.afterFard.size} selected Salawat goals reached for ${prayer.name}.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(vertical = 8.dp))
            Row(Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Prayer.entries.forEach { name ->
                    FilterChip(
                        selected = prayer == name,
                        onClick = { prayer = name },
                        label = { Text(name.name.lowercase().replaceFirstChar { it.uppercase() }) }
                    )
                }
            }
            LazyColumn(verticalArrangement = Arrangement.spacedBy(11.dp)) {
                items(SalawatCatalog.afterFard, key = { it.id }) { text ->
                    RecitationCard(
                        name = text.title,
                        current = count(totals, text.id, prayer.name),
                        target = SalawatCatalog.AFTER_FARD_TARGET_PER_TEXT,
                        editable = editable,
                        onRead = { opened = text },
                        onAdjust = { delta -> adjust(text.id, prayer, delta) }
                    )
                }
                item {
                    Text(
                        "Formulations are user-supplied. Arabic wording and historical attributions need scholarly review.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(vertical = 18.dp))
                }
            }
        }
    }

    opened?.let { selected ->
        AlertDialog(
            onDismissRequest = { opened = null },
            title = { Text(selected.title) },
            text = {
                Column(Modifier.verticalScroll(rememberScrollState())) {
                    Text("User-provided wording · source review pending",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                    ArabicReading(selected.arabic)
                }
            },
            confirmButton = {
                Button(onClick = { opened = null }) { Text("Done") }
            },
            shape = RoundedCornerShape(26.dp)
        )
    }
}

private fun count(
    totals: List<RecitationTotal>, itemId: String, scope: String = ""
): Long = totals.firstOrNull { it.itemId == itemId && it.prayer == scope }?.total ?: 0L

@Composable
private fun ArabicReading(text: String) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Text(
            text, modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Right,
        )
    }
}

@Composable
private fun CounterCard(
    title: String, arabic: String, target: Int, current: Long,
    editable: Boolean, onAdjust: (Int) -> Unit,
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Column(Modifier.fillMaxWidth().padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(title, fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.titleLarge)
                ElitePill(if (current >= target) "Goal reached" else "In progress", current >= target)
            }
            ArabicReading(arabic)
            Text("$current / $target recitations",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant)
            LinearProgressIndicator(
                progress = { (current / target.toFloat()).coerceIn(0f, 1f) },
                modifier = Modifier.fillMaxWidth(),
            )
            CountControls(current, editable, onAdjust)
        }
    }
}

@Composable
private fun RecitationCard(
    name: String, current: Long, target: Int, editable: Boolean,
    onRead: () -> Unit, onAdjust: (Int) -> Unit,
) {
    Card(shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
        Column(Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(name, modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold)
                ElitePill("$current / $target", current >= target)
            }
            LinearProgressIndicator(
                progress = { (current / target.toFloat()).coerceIn(0f, 1f) },
                modifier = Modifier.fillMaxWidth())
            OutlinedButton(onClick = onRead, shape = RoundedCornerShape(15.dp)) {
                Text("Read full Arabic text ↗")
            }
            CountControls(current, editable, onAdjust, short = true)
        }
    }
}

@Composable
private fun CountControls(
    current: Long, editable: Boolean, adjust: (Int) -> Unit, short: Boolean = false,
) {
    Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(7.dp)) {
        listOf(1, 10).plus(if (short) emptyList() else listOf(100)).forEach { step ->
            Button(
                onClick = { adjust(step) }, enabled = editable,
                shape = RoundedCornerShape(14.dp)
            ) { Text("+$step") }
        }
        listOf(1, 10).forEach { step ->
            OutlinedButton(
                onClick = { adjust(-step) }, enabled = editable && current >= step,
                shape = RoundedCornerShape(14.dp)
            ) { Text("−$step") }
        }
    }
}
