package com.muhammadtwaha.ibadahcompanion.domain

import com.muhammadtwaha.ibadahcompanion.domain.dhikr.RecitationRules
import com.muhammadtwaha.ibadahcompanion.domain.masnoon.*
import org.junit.Assert.*
import org.junit.Test

class MasnoonCatalogTest {
    @Test fun catalogHasUniqueStableIdentifiersAndSourceLinks() {
        assertEquals(MasnoonCatalog.practices.size,
            MasnoonCatalog.practices.map { it.id }.toSet().size)
        assertEquals(MasnoonCatalog.habits.size,
            MasnoonCatalog.habits.map { it.id }.toSet().size)
        assertTrue(MasnoonCatalog.practices.size >= 25)
        assertTrue(MasnoonCatalog.habits.size >= 8)
        assertTrue(MasnoonCatalog.practices.all {
            it.target > 0 && it.arabic.isNotBlank() && it.sourceUrl.startsWith("https://sunnah.com/")
        })
    }

    @Test fun morningAndEveningThreeQulsHaveThreeRepetitionsEach() {
        for (window in listOf(Window.MORNING, Window.EVENING)) {
            for (chapter in listOf("IKHLAS", "FALAQ", "NAS")) {
                val item = MasnoonCatalog.forWindow(window).single { it.id == chapter }
                assertEquals(3, item.target)
                assertTrue(item.reference.contains("5082"))
                assertFalse(item.disputed)
            }
        }
    }

    @Test fun sayyidulIstighfarHasIndependentMorningEveningScopes() {
        val item = MasnoonCatalog.practices.single { it.id == "SAYYID_ISTIGHFAR" }
        assertEquals(setOf(Window.MORNING, Window.EVENING), item.windows)
        assertEquals(1, item.target)
        assertEquals(Evidence.SAHIH, item.evidence)
        assertEquals("MORNING", MasnoonCatalog.scope(Window.MORNING))
        assertEquals("EVENING", MasnoonCatalog.scope(Window.EVENING))
    }

    @Test fun weakSevenHasbiyallahuReportCannotCountInCoreCompletion() {
        val disputed = MasnoonCatalog.practices.single { it.id == "HASBI_SEVEN" }
        assertEquals(7, disputed.target)
        assertTrue(disputed.disputed)
        assertTrue(disputed.reference.contains("mawdu'"))
        for (window in listOf(Window.MORNING, Window.EVENING)) {
            assertTrue(disputed in MasnoonCatalog.forWindow(window))
            assertEquals(MasnoonCatalog.forWindow(window).size - 1,
                MasnoonCatalog.targetCount(window))
            assertEquals(MasnoonCatalog.forWindow(window).size,
                MasnoonCatalog.targetCount(window, includeDisputed = true))
        }
    }

    @Test fun afterSalahAndOccasionAreNotTreatedAsDailyPrescribedGoals() {
        val after = MasnoonCatalog.forWindow(Window.AFTER_SALAH)
        assertTrue(after.any { it.id == "AFTER_PRAYER_TASBIH" && it.target == 33 })
        assertTrue(after.any { it.id == "AFTER_PRAYER_ISTIGHFAR" && it.target == 3 })
        assertFalse(after.any { it.id == "SAYYID_ISTIGHFAR" })
        assertTrue(MasnoonCatalog.forWindow(Window.OCCASION).any { it.id == "WAKE_DUA" })
        assertFalse(MasnoonCatalog.forWindow(Window.MORNING).any { it.id == "WAKE_DUA" })
    }

    @Test fun negativeAdjustmentsCannotCreateFabricatedHistory() {
        assertEquals(3L, RecitationRules.nextCount(0, 3))
        assertEquals(2L, RecitationRules.nextCount(3, -1))
        try {
            RecitationRules.nextCount(0, -3)
            fail("must not create negative completion")
        } catch (_: IllegalArgumentException) {
            // The audited Dhikr DAO calls this exact pure guard inside its transaction.
        }
    }
}
