package com.muhammadtwaha.ibadahcompanion.domain.time

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

/** Source of time and the user's local calendar date. Pure Kotlin so domain logic is testable. */
interface AppClock {
    fun now(): Instant
    fun zone(): ZoneId
    fun today(): LocalDate = now().atZone(zone()).toLocalDate()
}

class SystemAppClock : AppClock {
    override fun now(): Instant = Instant.now()
    override fun zone(): ZoneId = ZoneId.systemDefault()
}
