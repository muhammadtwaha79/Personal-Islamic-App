package com.muhammadtwaha.ibadahcompanion.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muhammadtwaha.ibadahcompanion.data.WorshipRepository
import com.muhammadtwaha.ibadahcompanion.domain.worship.PrayerStatus
import com.muhammadtwaha.ibadahcompanion.domain.worship.WorshipRules
import com.muhammadtwaha.ibadahcompanion.ui.design.ElitePalette
import com.muhammadtwaha.ibadahcompanion.ui.design.EliteSectionTitle
import com.muhammadtwaha.ibadahcompanion.ui.design.WorshipArtwork
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

private data class Module(
    val route: String,
    val eyebrow: String,
    val title: String,
    val subtitle: String,
    val artwork: String,
    val color: Color,
    val accent: Color,
)

private val modules = listOf(
    Module("salah", "01 • PRAYER", "Daily Salah", "Five prayers & Jamaat",
        "mosque", Color(0xFF1B7563), Color(0xFFDFF2E8)),
    Module("quran", "02 • READING", "Al-Quran", "Half para & Surahs",
        "quran", Color(0xFF3D6685), Color(0xFFE5F0F7)),
    Module("nafl", "03 • DEVOTION", "Sunnah & Nafl", "Tahajjud, Ishraq & more",
        "nafl", Color(0xFF816349), Color(0xFFF4EADD)),
    Module("dhikr", "04 • REMEMBRANCE", "Salawat & Dhikr", "Your personal daily goals",
        "dhikr", Color(0xFF555B82), Color(0xFFECEBFA)),
    Module("challenge", "05 • CONSISTENCY", "40-Day Journey", "Opening Takbeer tracker",
        "challenge", Color(0xFF366D60), Color(0xFFE8F1E8)),
    Module("manzils", "06 • DAILY READING", "Manzils", "Dala'il & Munajat",
        "books", Color(0xFF8A6958), Color(0xFFF4E8E0)),
)

@Composable
fun HomeScreen(repository: WorshipRepository, onOpen: (String) -> Unit) {
    val today = remember { LocalDate.now() }
    val prayerRecords by remember(today) { repository.salah(today) }
        .collectAsState(initial = emptyList())
    val completed = prayerRecords.count { it.status == PrayerStatus.COMPLETED.name }
    val dateText = remember(today) {
        today.format(DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy", Locale.ENGLISH))
    }

    Column(
        Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp),
    ) {
        Spacer(Modifier.height(24.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text("IBADAH COMPANION", color = MaterialTheme.colorScheme.primary,
                    letterSpacing = 2.4.sp, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Text("Assalamu Alaikum", style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top = 7.dp))
                Text(dateText, color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp))
            }
            Box(
                Modifier.size(50.dp).background(ElitePalette.Sage, RoundedCornerShape(18.dp)),
                contentAlignment = Alignment.Center
            ) { Text("☪", color = ElitePalette.Pine, fontSize = 27.sp) }
        }
        Spacer(Modifier.height(25.dp))
        Box(
            modifier = Modifier.fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        listOf(ElitePalette.PineDeep, ElitePalette.Pine, ElitePalette.Emerald)),
                    RoundedCornerShape(30.dp))
                .clickable { onOpen("salah") },
        ) {
            Row(
                Modifier.fillMaxWidth().padding(start = 22.dp, top = 25.dp, bottom = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(Modifier.weight(1.25f)) {
                    Text("YOUR DAILY JOURNEY", color = ElitePalette.Gold,
                        fontSize = 11.sp, letterSpacing = 1.5.sp, fontWeight = FontWeight.Bold)
                    Text("A day of intention\nand worship",
                        color = Color.White,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 32.sp,
                        modifier = Modifier.padding(top = 12.dp, bottom = 10.dp))
                    Text("$completed of 5 Fard prayers recorded",
                        color = Color.White.copy(alpha = .84f),
                        style = MaterialTheme.typography.bodySmall)
                    LinearProgressIndicator(
                        progress = { completed / 5f },
                        modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                        color = ElitePalette.Gold,
                        trackColor = Color.White.copy(alpha = .22f),
                    )
                    Text("Open Salah tracker  →", color = Color.White,
                        fontWeight = FontWeight.Medium, fontSize = 12.sp,
                        modifier = Modifier.padding(top = 15.dp))
                }
                WorshipArtwork(
                    "mosque", ElitePalette.Pine, Modifier.weight(1f).height(170.dp))
            }
        }
        Spacer(Modifier.height(14.dp))
        if (today.isBefore(WorshipRules.trackingStart)) {
            Text(
                "Your tracking start is 20 October. Record only prayers you actually perform.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 3.dp, vertical = 5.dp)
            )
        }
        Spacer(Modifier.height(18.dp))
        EliteSectionTitle("Your sacred spaces", "EXPLORE")
        Text("A quiet place for every practice.",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 14.dp))

        modules.chunked(2).forEach { pair ->
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                pair.forEach { item ->
                    ModuleTile(
                        module = item,
                        modifier = Modifier.weight(1f),
                        onClick = { onOpen(item.route) },
                    )
                }
                if (pair.size == 1) Spacer(Modifier.weight(1f))
            }
            Spacer(Modifier.height(12.dp))
        }
        Spacer(Modifier.height(8.dp))
        Text(
            "Every entry is your private self-report. Progress is not a measure of spiritual worth.",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(bottom = 28.dp, top = 8.dp)
        )
    }
}

@Composable
private fun ModuleTile(
    module: Module,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .background(module.accent, RoundedCornerShape(26.dp))
            .clickable(onClick = onClick)
            .padding(14.dp),
    ) {
        Box(Modifier.fillMaxWidth().height(106.dp)) {
            WorshipArtwork(module.artwork, module.color,
                Modifier.fillMaxWidth().height(110.dp))
        }
        Spacer(Modifier.height(6.dp))
        Text(module.eyebrow, color = module.color,
            fontSize = 9.sp, letterSpacing = 1.0.sp,
            fontWeight = FontWeight.Bold, maxLines = 1)
        Text(module.title, color = ElitePalette.Ink,
            fontSize = 17.sp, fontWeight = FontWeight.SemiBold,
            maxLines = 2, overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 5.dp))
        Text(module.subtitle, color = ElitePalette.Muted,
            fontSize = 11.sp, maxLines = 2,
            lineHeight = 15.sp,
            modifier = Modifier.padding(top = 4.dp))
        Text("Open  ↗", color = module.color,
            fontWeight = FontWeight.SemiBold, fontSize = 12.sp,
            modifier = Modifier.padding(top = 12.dp, bottom = 2.dp))
    }
}
