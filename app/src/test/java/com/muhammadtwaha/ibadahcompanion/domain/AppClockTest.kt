package com.muhammadtwaha.ibadahcompanion.domain

import com.muhammadtwaha.ibadahcompanion.domain.time.AppClock
import com.muhammadtwaha.ibadahcompanion.domain.time.SystemAppClock
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AppClockTest {

    private class FixedClock(private val instant: Instant, private val zoneId: ZoneId) : AppClock {
        override fun now(): Instant = instant
        override fun zone(): ZoneId = zoneId
    }

    @Test
    fun todayUsesLocalZoneNotUtc() {
        val instant = Instant.parse("2026-10-19T23:30:00Z")
        assertEquals(LocalDate.of(2026, 10, 19), FixedClock(instant, ZoneId.of("UTC")).today())
        assertEquals(LocalDate.of(2026, 10, 20), FixedClock(instant, ZoneId.of("Asia/Karachi")).today())
    }

    @Test
    fun todayChangesAtLocalMidnightBoundary() {
        val zone = ZoneId.of("Asia/Karachi")
        val beforeMidnight = Instant.parse("2026-10-20T18:59:59Z")
        val atMidnight = Instant.parse("2026-10-20T19:00:00Z")
        assertEquals(LocalDate.of(2026, 10, 20), FixedClock(beforeMidnight, zone).today())
        assertEquals(LocalDate.of(2026, 10, 21), FixedClock(atMidnight, zone).today())
    }

    @Test
    fun systemClockReturnsCurrentTime() {
        val before = Instant.now()
        val now = SystemAppClock().now()
        val after = Instant.now()
        assertTrue(!now.isBefore(before) && !now.isAfter(after))
    }
}
