package com.muhammadtwaha.ibadahcompanion.ui.manzils

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
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
import androidx.compose.ui.unit.dp
import com.muhammadtwaha.ibadahcompanion.data.DhikrRepository
import com.muhammadtwaha.ibadahcompanion.data.ManzilsRepository
import com.muhammadtwaha.ibadahcompanion.ui.design.ElitePageTitle
import com.muhammadtwaha.ibadahcompanion.ui.design.ElitePalette
import com.muhammadtwaha.ibadahcompanion.ui.design.ElitePill
import com.muhammadtwaha.ibadahcompanion.ui.design.WorshipArtwork
import com.muhammadtwaha.ibadahcompanion.ui.shared.DaySwitcher
import com.muhammadtwaha.ibadahcompanion.ui.shared.rememberTrackingDate
import java.time.LocalDate
import kotlinx.coroutines.launch

@Composable
fun ManzilsScreen(dhikrRepository: DhikrRepository, manzilsRepository: ManzilsRepository) {
    val (date, setDate) = rememberTrackingDate()
    val dalail by remember(date) { dhikrRepository.dalail(date) }.collectAsState(initial = null)
    val munajat by remember(date) { manzilsRepository.munajat(date) }.collectAsState(initial = null)
    val scope = rememberCoroutineScope()
    var error by remember { mutableStateOf("") }
    val editable = !date.isAfter(LocalDate.now())
    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 18.dp),
        verticalArrangement = Arrangement.spacedBy(13.dp),
    ) {
        ElitePageTitle(
            "Your reading path", "Daily Manzils",
            "Keep Dala'il al-Khayrat and Munajat-e-Maqbool beautifully organized.",
        )
        DaySwitcher(date, setDate)
        if (error.isNotEmpty()) Text(error, color = MaterialTheme.colorScheme.error)

        ReadingManzilCard(
            title = "Dala'il al-Khayrat",
            eyebrow = "SALAWAT READING",
            description = "Select the section from your own edition.",
            date = date, savedManzil = dalail?.manzil ?: 0,
            savedEdition = dalail?.edition.orEmpty(), completed = dalail?.completed == true,
            color = ElitePalette.Pine, editable = editable,
        ) { manzil, edition, done ->
            scope.launch {
                runCatching { dhikrRepository.setDalail(date, manzil, done, edition) }
                    .onFailure { error = it.message ?: "Unable to record this manzil" }
            }
        }
        ReadingManzilCard(
            title = "Munajat-e-Maqbool",
            eyebrow = "DAILY MUNAJAT",
            description = "One chosen manzil, with optional edition information.",
            date = date, savedManzil = munajat?.manzil ?: 0,
            savedEdition = munajat?.edition.orEmpty(), completed = munajat?.completed == true,
            color = ElitePalette.Indigo, editable = editable,
        ) { manzil, edition, done ->
            scope.launch {
                runCatching { manzilsRepository.saveMunajat(date, manzil, edition, done) }
                    .onFailure { error = it.message ?: "Unable to record this manzil" }
            }
        }
        Text(
            "Read from your own copy. Editions can divide manzils differently; the app never assumes a weekday mapping or includes book text.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 24.dp)
        )
    }
}

@Composable
private fun ReadingManzilCard(
    title: String,
    eyebrow: String,
    description: String,
    date: LocalDate,
    savedManzil: Int,
    savedEdition: String,
    completed: Boolean,
    color: androidx.compose.ui.graphics.Color,
    editable: Boolean,
    save: (Int, String, Boolean) -> Unit,
) {
    var selected by remember(date, savedManzil) { mutableIntStateOf(savedManzil) }
    var edition by remember(date, savedEdition) { mutableStateOf(savedEdition) }
    Card(
        shape = RoundedCornerShape(25.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column(Modifier.fillMaxWidth().padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                Box(Modifier.weight(1f)) {
                    Column {
                        Text(eyebrow, color = color, style = MaterialTheme.typography.labelSmall)
                        Text(title, style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(top = 5.dp))
                        Text(description, color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 4.dp))
                    }
                }
                Box(Modifier.background(color, RoundedCornerShape(19.dp))) {
                    WorshipArtwork("books", color, Modifier.padding(3.dp)
                        .height(79.dp).fillMaxWidth(.24f))
                }
            }
            ElitePill(if (completed) "Completed today" else "Not recorded", completed)
            Text("CHOOSE MANZIL", color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.labelSmall)
            Row(Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                (1..7).forEach { n ->
                    FilterChip(
                        selected = selected == n,
                        onClick = { selected = n },
                        label = { Text("$n") }
                    )
                }
            }
            OutlinedTextField(
                edition, onValueChange = { if (it.length <= 100) edition = it },
                label = { Text("Edition (optional)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
            )
            Button(
                enabled = editable && selected > 0,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(16.dp),
                onClick = { save(selected, edition, true) },
            ) { Text(if (completed) "Save changes" else "Confirm reading completed") }
            if (completed) {
                OutlinedButton(
                    onClick = { save(selected, edition, false) },
                    enabled = editable, modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                ) { Text("Undo completion") }
            }
        }
    }
}
