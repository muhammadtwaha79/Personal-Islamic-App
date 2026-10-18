package com.muhammadtwaha.ibadahcompanion.domain.id

import java.util.UUID

/** Generates stable, globally unique identifiers for domain records. */
interface IdGenerator {
    fun newId(): String
}

class UuidIdGenerator : IdGenerator {
    override fun newId(): String = UUID.randomUUID().toString()
}
