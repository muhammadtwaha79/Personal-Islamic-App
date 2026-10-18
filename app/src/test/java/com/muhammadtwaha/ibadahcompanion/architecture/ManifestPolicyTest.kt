package com.muhammadtwaha.ibadahcompanion.architecture

import java.io.File
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/** Privacy baseline (ADR-0012): offline-first, no network permission, no cloud backup. */
class ManifestPolicyTest {

    private fun manifestText(): String {
        val manifest = File("src/main/AndroidManifest.xml")
        assertTrue("Manifest not found at ${manifest.absolutePath}", manifest.isFile)
        return manifest.readText()
    }

    @Test
    fun manifestDeclaresNoNetworkPermission() {
        assertFalse(manifestText().contains("android.permission.INTERNET"))
    }

    @Test
    fun manifestDisablesBackup() {
        assertTrue(manifestText().contains("android:allowBackup=\"false\""))
    }
}
