package com.muhammadtwaha.ibadahcompanion.architecture

import java.io.File
import org.junit.Assert.assertTrue
import org.junit.Test

/** Architecture rule: domain code must be pure Kotlin/JVM with no Android or DI framework imports. */
class DomainPurityTest {

    private val forbiddenPrefixes = listOf("import android.", "import androidx.", "import dagger.", "import javax.inject")

    @Test
    fun domainPackageHasNoFrameworkImports() {
        val domainDir = File("src/main/java/com/muhammadtwaha/ibadahcompanion/domain")
        assertTrue("Domain directory not found at ${domainDir.absolutePath}", domainDir.isDirectory)
        val sources = domainDir.walkTopDown().filter { it.isFile && it.extension == "kt" }.toList()
        assertTrue("No domain sources found", sources.isNotEmpty())
        val offenders = sources.filter { file ->
            file.readLines().any { line -> forbiddenPrefixes.any { line.trim().startsWith(it) } }
        }.map { it.name }
        assertTrue("Framework imports found in domain files: $offenders", offenders.isEmpty())
    }
}
