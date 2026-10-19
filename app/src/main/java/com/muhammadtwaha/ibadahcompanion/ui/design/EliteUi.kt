package com.muhammadtwaha.ibadahcompanion.ui.design

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object ElitePalette {
    val Pine = Color(0xFF123F36)
    val PineDeep = Color(0xFF082921)
    val Emerald = Color(0xFF167864)
    val Sage = Color(0xFFE1EEE7)
    val Ivory = Color(0xFFF8F6EF)
    val Cream = Color(0xFFEEE7D6)
    val Ink = Color(0xFF20322D)
    val Muted = Color(0xFF62766C)
    val Gold = Color(0xFFDBB46B)
    val NightBlue = Color(0xFF203F59)
    val Indigo = Color(0xFF555780)
    val Blush = Color(0xFFF1DCCB)
    val White = Color(0xFFFFFFFF)
}

@Composable
fun ElitePageTitle(eyebrow: String, title: String, subtitle: String = "") {
    Column(Modifier.fillMaxWidth().padding(vertical = 12.dp)) {
        Text(
            eyebrow.uppercase(), color = MaterialTheme.colorScheme.primary,
            fontSize = 11.sp, letterSpacing = 2.sp, fontWeight = FontWeight.Bold
        )
        Text(
            title, style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 5.dp)
        )
        if (subtitle.isNotBlank()) {
            Text(subtitle, color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 5.dp))
        }
    }
}

@Composable
fun EliteSectionTitle(title: String, trailing: String = "") {
    Row(
        Modifier.fillMaxWidth().padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
        if (trailing.isNotBlank()) {
            Text(trailing, color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelMedium)
        }
    }
}

@Composable
fun EliteCard(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Box(Modifier.padding(16.dp)) { content() }
    }
}

@Composable
fun ElitePill(text: String, success: Boolean = false) {
    Surface(
        shape = RoundedCornerShape(50),
        color = if (success) ElitePalette.Sage else MaterialTheme.colorScheme.surfaceVariant,
    ) {
        Text(
            text, color = if (success) ElitePalette.Pine else MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp)
        )
    }
}

/** Compact large-touch-target selection row. One intentional record at a time. */
@Composable
fun EliteChoice(
    label: String,
    detail: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val foreground = if (selected) MaterialTheme.colorScheme.primary else
        MaterialTheme.colorScheme.onSurface
    Surface(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.fillMaxWidth().border(
            width = if (selected) 1.5.dp else 1.dp,
            color = if (selected) MaterialTheme.colorScheme.primary else
                MaterialTheme.colorScheme.outlineVariant,
            shape = RoundedCornerShape(17.dp)
        ),
        shape = RoundedCornerShape(17.dp),
        color = if (selected) MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.surface,
    ) {
        Row(
            Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(13.dp),
        ) {
            Surface(
                modifier = Modifier.border(
                    1.5.dp, foreground.copy(alpha = if (selected) 1f else 0.45f),
                    RoundedCornerShape(50)
                ),
                shape = RoundedCornerShape(50),
                color = if (selected) foreground else Color.Transparent,
            ) {
                Box(Modifier.padding(5.dp)) {
                    Text(if (selected) "✓" else "○", fontSize = 15.sp,
                        color = if (selected) MaterialTheme.colorScheme.onPrimary else foreground)
                }
            }
            Column(Modifier.weight(1f)) {
                Text(label, color = foreground, fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.bodyLarge)
                Text(detail, style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

/**
 * Original, resolution-independent illustrations made from Compose Canvas primitives.
 * No photographs, internet access, or extra dependencies needed.
 */
@Composable
fun WorshipArtwork(
    kind: String,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val white = Color.White.copy(alpha = .88f)
        val faded = Color.White.copy(alpha = .17f)
        drawCircle(faded, radius = w * .39f, center = Offset(w * .70f, h * .47f))
        drawCircle(faded, radius = w * .21f, center = Offset(w * .22f, h * .84f))
        when (kind) {
            "mosque", "challenge" -> {
                val baseY = h * .85f
                drawRoundRect(white, Offset(w * .19f, baseY - h * .25f),
                    Size(w * .62f, h * .25f), CornerRadius(w * .035f))
                drawCircle(white, radius = w * .20f, center = Offset(w * .50f, baseY - h * .25f))
                drawRoundRect(color, Offset(w * .445f, baseY - h * .16f),
                    Size(w * .11f, h * .16f), CornerRadius(w * .055f))
                listOf(.16f, .80f).forEach { x ->
                    drawRoundRect(white, Offset(w * x, h * .28f),
                        Size(w * .07f, h * .58f), CornerRadius(w * .025f))
                    val point = Path().apply {
                        moveTo(w * (x - .018f), h * .31f)
                        lineTo(w * (x + .035f), h * .15f)
                        lineTo(w * (x + .088f), h * .31f)
                        close()
                    }
                    drawPath(point, white)
                }
                drawCircle(white, radius = w * .045f, center = Offset(w * .73f, h * .17f))
                drawCircle(color, radius = w * .043f, center = Offset(w * .75f, h * .14f))
                if (kind == "challenge") {
                    drawCircle(ElitePalette.Gold, radius = w * .065f,
                        center = Offset(w * .83f, h * .72f))
                }
            }
            "quran" -> {
                val left = Path().apply {
                    moveTo(w * .50f,h * .36f)
                    quadraticTo(w * .31f,h * .23f,w * .14f,h * .36f)
                    lineTo(w * .14f,h * .79f)
                    quadraticTo(w * .30f,h * .68f,w * .50f,h * .82f)
                    close()
                }
                val right = Path().apply {
                    moveTo(w * .50f,h * .36f)
                    quadraticTo(w * .70f,h * .23f,w * .86f,h * .36f)
                    lineTo(w * .86f,h * .79f)
                    quadraticTo(w * .70f,h * .68f,w * .50f,h * .82f)
                    close()
                }
                drawPath(left, white); drawPath(right, white.copy(alpha = .75f))
                drawLine(color, Offset(w * .5f,h * .37f), Offset(w * .5f,h * .80f),w * .015f)
                for (i in 0..2) {
                    val y=h * (.47f+i*.09f)
                    drawLine(color.copy(alpha=.45f),Offset(w*.23f,y),Offset(w*.43f,y),w*.01f)
                    drawLine(color.copy(alpha=.45f),Offset(w*.58f,y),Offset(w*.78f,y),w*.01f)
                }
            }
            "nafl" -> {
                drawRoundRect(white,Offset(w*.19f,h*.51f),Size(w*.62f,h*.28f),
                    CornerRadius(w*.045f))
                drawCircle(color,radius=w*.16f,center=Offset(w*.5f,h*.51f))
                drawCircle(white,radius=w*.135f,center=Offset(w*.5f,h*.47f))
                drawCircle(color,radius=w*.13f,center=Offset(w*.55f,h*.44f))
                drawLine(white,Offset(w*.23f,h*.80f),Offset(w*.77f,h*.80f),w*.03f)
            }
            "dhikr" -> {
                for (i in 0..7) {
                    val a=(i*0.78f).toDouble()
                    val cx=(.5+ .26 * kotlin.math.cos(a)).toFloat()*w
                    val cy=(.53+ .23 * kotlin.math.sin(a)).toFloat()*h
                    drawCircle(white,radius=w*.066f,center=Offset(cx,cy))
                }
                drawCircle(ElitePalette.Gold,radius=w*.11f,center=Offset(w*.5f,h*.53f))
                drawLine(white,Offset(w*.5f,h*.67f),Offset(w*.47f,h*.9f),w*.025f)
            }
            "books" -> {
                for (i in 0..2) {
                    drawRoundRect(white.copy(alpha=1f-i*.18f),
                        Offset(w*(.24f+i*.07f),h*(.28f+i*.16f)),
                        Size(w*.54f,h*.15f),CornerRadius(w*.025f))
                    drawLine(color,Offset(w*(.32f+i*.07f),h*(.30f+i*.16f)),
                        Offset(w*(.32f+i*.07f),h*(.40f+i*.16f)),w*.017f)
                }
            }
            else -> {
                drawCircle(white,radius=w*.18f,center=Offset(w*.5f,h*.47f),
                    style=Stroke(width=w*.042f))
                drawLine(white,Offset(w*.5f,h*.27f),Offset(w*.5f,h*.67f),w*.036f)
                drawLine(white,Offset(w*.3f,h*.47f),Offset(w*.7f,h*.47f),w*.036f)
            }
        }
    }
}
