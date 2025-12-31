package dev.nordix.irbridge.export.data

import android.content.ContentResolver
import android.net.Uri
import dev.nordix.irbridge.export.domain.ExportManager
import dev.nordix.irbridge.remotes.data.REMOTES_DB_SCHEMA_VERSION
import dev.nordix.irbridge.remotes.domain.RemotesRepository
import dev.nordix.irbridge.remotes.domain.model.Remote
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

internal class ExportManagerImpl(
    private val remotesRepository: RemotesRepository,
) : ExportManager {

    val json = Json {
        prettyPrint = true
        encodeDefaults = true
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    override suspend fun export(
        contentResolver: ContentResolver,
        uri: Uri
    ): Boolean {
        val remotes = remotesRepository.getAll()
        val backup = DbBackup(
            schemaVersion = REMOTES_DB_SCHEMA_VERSION,
            exportedAt = System.currentTimeMillis(),
            remotes = remotes,
        )
        contentResolver.openOutputStream(uri).use { os ->
            requireNotNull(os) { "Can't open output stream for $uri" }
            val payload = json.encodeToString(backup).encodeToByteArray()
            os.write(payload)
            os.flush()
        }
        return true
    }

    override suspend fun import(
        contentResolver: ContentResolver,
        uri: Uri
    ): Result<Unit> {
        val text = contentResolver.openInputStream(uri).use { ins ->
            requireNotNull(ins) { "Can't open input stream for $uri" }
            ins.readBytes().decodeToString()
        }
        val backup = json.decodeFromString<DbBackup>(text)
        if (backup.schemaVersion != REMOTES_DB_SCHEMA_VERSION) {
            return Result.failure(SchemaMismatchException(backup.schemaVersion, REMOTES_DB_SCHEMA_VERSION))
        }
        remotesRepository.saveAll(*backup.remotes.toTypedArray())
        return Result.success(Unit)
    }

    @Serializable
    data class DbBackup(
        val schemaVersion: Int,
        val exportedAt: Long,
        val remotes: List<Remote>,
    )

    data class SchemaMismatchException(
        val got: Int,
        val expected: Int,
    ) : Exception("Schema version mismatch: got $got, expected $expected")
}
