package com.muhammadtwaha.ibadahcompanion.domain

import com.muhammadtwaha.ibadahcompanion.domain.id.UuidIdGenerator
import java.util.UUID
import org.junit.Assert.assertEquals
import org.junit.Test

class IdGeneratorTest {

    @Test
    fun generatedIdsAreUnique() {
        val generator = UuidIdGenerator()
        val ids = (1..5000).map { generator.newId() }
        assertEquals(ids.size, ids.toSet().size)
    }

    @Test
    fun generatedIdsAreValidUuids() {
        val id = UuidIdGenerator().newId()
        assertEquals(id, UUID.fromString(id).toString())
    }
}
