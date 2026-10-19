package com.muhammadtwaha.ibadahcompanion.ui.nafl

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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muhammadtwaha.ibadahcompanion.data.PRACTICES
import com.muhammadtwaha.ibadahcompanion.data.PracticeDefinition
import com.muhammadtwaha.ibadahcompanion.data.WorshipRepository
import com.muhammadtwaha.ibadahcompanion.data.db.PracticeRecord
import com.muhammadtwaha.ibadahcompanion.ui.design.ElitePageTitle
import com.muhammadtwaha.ibadahcompanion.ui.design.ElitePalette
import com.muhammadtwaha.ibadahcompanion.ui.design.ElitePill
import com.muhammadtwaha.ibadahcompanion.ui.shared.DaySwitcher
import com.muhammadtwaha.ibadahcompanion.ui.shared.rememberTrackingDate
import java.time.LocalDate
import kotlinx.coroutines.launch

@Composable
fun NaflScreen(repository: WorshipRepository) {
    val (date, setDate) = rememberTrackingDate()
    val records by remember(date) { repository.practices(date) }
        .collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()
    var error by remember { mutableStateOf("") }
    val editable = !date.isAfter(LocalDate.now())
    val completed = records.count { it.completed }

    Column(Modifier.fillMaxSize().padding(horizontal = 18.dp)) {
        ElitePageTitle("Voluntary worship", "Sunnah & Nawafil",
            "Make space for the prayers you choose to record.")
        DaySwitcher(date, setDate)
        Card(
            shape = RoundedCornerShape(25.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Row(
                Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(Modifier.weight(1f)) {
                    Text("Your personal practice", fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.titleMedium)
                    Text("Nothing is marked complete automatically.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 4.dp))
                }
                ElitePill("$completed recorded", completed > 0)
            }
        }
        if (error.isNotBlank()) {
            Text(error, color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(vertical = 6.dp))
        }
        Spacer(Modifier.height(16.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(11.dp)) {
            items(PRACTICES, key = { it.key }) { practice ->
                val saved = records.firstOrNull { it.practice == practice.key }
                NaflCard(practice, saved, editable) { rakahs, done ->
                    scope.launch {
                        try {
                            repository.setPractice(date, practice.key, rakahs, done)
                            error = ""
                        } catch (e: Exception) {
                            error = e.message ?: "Unable to save this prayer"
                        }
                    }
                }
            }
            item {
                Text(
                    "Rakah counts are self-reported. These voluntary practices do not automatically create Qaza obligations.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(vertical = 20.dp))
            }
        }
    }
}

@Composable
private fun NaflCard(
    practice: PracticeDefinition,
    saved: PracticeRecord?,
    editable: Boolean,
    update: (Int, Boolean) -> Unit,
) {
    var rakahs by remember(practice.key, saved?.rakahs) {
        mutableIntStateOf(saved?.rakahs ?: practice.defaultRakahs)
    }
    val completed = saved?.completed == true
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Column(Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(11.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text(practice.label, style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold)
                    Text("Choose rakahs you actually prayed",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 4.dp))
                }
                ElitePill(if (completed) "Completed ✓" else "Not recorded", completed)
            }
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Rakahs", color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium)
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    OutlinedButton(
                        enabled = editable && rakahs > 1,
                        onClick = { rakahs-- },
                        shape = RoundedCornerShape(13.dp),
                    ) { Text("−", fontSize = 18.sp) }
                    Text("$rakahs", style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold)
                    OutlinedButton(
                        enabled = editable && rakahs < 100,
                        onClick = { rakahs++ },
                        shape = RoundedCornerShape(13.dp),
                    ) { Text("+", fontSize = 18.sp) }
                }
            }
            Button(
                enabled = editable,
                modifier = Modifier.fillMaxWidth().height(49.dp),
                shape = RoundedCornerShape(15.dp),
                onClick = { update(rakahs, !completed) }
            ) { Text(if (completed) "Undo completion" else "✓  Confirm completed") }
        }
    }
}
