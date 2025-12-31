package dev.nordix.irbridge.export.domain

import android.content.ContentResolver
import android.net.Uri

interface ExportManager {
    suspend fun export(contentResolver: ContentResolver, uri: Uri) : Boolean
    suspend fun import(contentResolver: ContentResolver, uri: Uri) : Result<Unit>
}
