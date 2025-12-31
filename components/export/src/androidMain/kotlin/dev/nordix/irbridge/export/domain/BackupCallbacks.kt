package dev.nordix.irbridge.export.domain

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import java.time.Instant

data class BackupCallbacks(
    val onExportClick: () -> Unit,
    val onImportClick: () -> Unit,
)

@Composable
fun rememberBackupCallbacks(
    onError: (Throwable) -> Unit = {},
    onDone: (String) -> Unit = {},
    exportFileName: () -> String = { "backup_${Instant.now().epochSecond}.json" },
): BackupCallbacks {
    val contentResolver = LocalContext.current.contentResolver
    val exportManager = koinInject<ExportManager>()
    val scope = rememberCoroutineScope()

    val createDocLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/json")
    ) { uri: Uri? ->
        if (uri == null) return@rememberLauncherForActivityResult
        scope.launch(Dispatchers.IO) {
            runCatching {
                exportManager.export(contentResolver, uri)
            }.onSuccess {
                onDone("Exported")
            }.onFailure(onError)
        }
    }

    val openDocLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        if (uri == null) return@rememberLauncherForActivityResult
        scope.launch(Dispatchers.IO) {
            exportManager.import(contentResolver, uri)
                .onSuccess {
                    onDone("Imported")
                }.onFailure(onError)
        }
    }

    return remember(createDocLauncher, openDocLauncher) {
        BackupCallbacks(
            onExportClick = { createDocLauncher.launch(exportFileName()) },
            onImportClick = { openDocLauncher.launch(arrayOf("application/json", "text/json", "*/*")) },
        )
    }
}
