package com.muhammadtwaha.ibadahcompanion.domain

import com.muhammadtwaha.ibadahcompanion.domain.dhikr.RecitationRules
import com.muhammadtwaha.ibadahcompanion.domain.dhikr.SalawatCatalog
import org.junit.Assert.*
import org.junit.Test

class SalawatCounterTest {
    @Test fun allTwentyUserSuppliedSalawatsAreDistinct() {
        val forms = SalawatCatalog.afterFard
        assertEquals(20, forms.size)
        assertEquals(forms.size, forms.map { it.id }.distinct().size)
        assertTrue(forms.all { it.arabic.isNotBlank() && it.title.isNotBlank() })
        assertEquals(100, RecitationRules.prayerFormulationGoals())
        assertNotEquals(
            forms.first { it.id == "FATIH" }.arabic,
            forms.first { it.id == "FATH_AL_MUBIN" }.arabic
        )
        assertTrue(forms.first { it.id == "SPECIAL_CHISHTI" }.arabic.contains("الْفَاتِحَةُ"))
    }

    @Test fun dailyGoalsAreIndependentAndExact() {
        assertEquals(1200, SalawatCatalog.DAILY_SHORT_TARGET)
        assertEquals(1200, SalawatCatalog.DAILY_ISTIGHFAR_TARGET)
        assertEquals(1000, SalawatCatalog.DAILY_BISMILLAH_TARGET)
        assertEquals(10, SalawatCatalog.AFTER_FARD_TARGET_PER_TEXT)
        assertEquals("اللَّهُمَّ صَلِّ عَلَى مُحَمَّدٍ وَعَلَى آلِ مُحَمَّدٍ وَسَلِّمْ",
            SalawatCatalog.DAILY_SHORT_ARABIC)
        assertEquals(50, RecitationRules.completionPercent(600, 1200))
        assertEquals(100, RecitationRules.completionPercent(1400, 1200))
    }

    @Test fun negativeOrExcessiveCorrectionsFailClosed() {
        assertEquals(10L, RecitationRules.nextCount(0L, 10))
        assertEquals(9L, RecitationRules.nextCount(10L, -1))
        assertEquals(0L, RecitationRules.nextCount(10L, -10))
        expectInvalid { RecitationRules.nextCount(0L, -1) }
        expectInvalid { RecitationRules.nextCount(9L, -10) }
        expectInvalid { RecitationRules.nextCount(5L, 0) }
        expectInvalid { RecitationRules.nextCount(5L, 1001) }
        expectInvalid { RecitationRules.nextCount(RecitationRules.MAX_STORED_COUNT, 1) }
        expectInvalid { RecitationRules.nextCount(-1, 1) }
    }

    private fun expectInvalid(action: () -> Unit) {
        try {
            action()
            fail("Expected an invalid recitation correction to be rejected")
        } catch (_: IllegalArgumentException) {
            // Expected.
        }
    }
}
