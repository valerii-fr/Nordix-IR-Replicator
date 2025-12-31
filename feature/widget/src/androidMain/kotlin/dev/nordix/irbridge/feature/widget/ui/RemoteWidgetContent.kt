package dev.nordix.irbridge.feature.widget.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.Button
import androidx.glance.GlanceModifier
import androidx.glance.LocalContext
import androidx.glance.appwidget.cornerRadius
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.text.FontFamily
import androidx.glance.text.FontWeight
import androidx.glance.text.TextStyle
import dev.nordix.irbridge.feature.widget.domain.RemoteDialogContract
import dev.nordix.irbridge.remotes.domain.model.Remote

@Composable
internal fun RemoteWidgetContent(
    remotes: List<Remote> = emptyList()
) {

    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = GlanceModifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.2f))
            .cornerRadius(8.dp)
    ) {
        remotes.forEach { r ->
            Column(
                modifier = GlanceModifier.padding(16.dp)
            ) {
                Button(
                    modifier = GlanceModifier.fillMaxWidth(),
                    text = r.name,
                    onClick = {
                        val i = RemoteDialogContract.createIntent(
                            context = context,
                            remoteId = r.id.value,
                            fromWidget = true
                        )
                        context.startActivity(i)
                    },
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.labelSmall.fontSize,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                )
            }
        }
    }
}
