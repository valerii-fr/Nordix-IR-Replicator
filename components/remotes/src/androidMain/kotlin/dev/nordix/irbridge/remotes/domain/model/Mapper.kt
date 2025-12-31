package dev.nordix.irbridge.remotes.domain.model

import dev.nordix.irbridge.core.encoders.IntArrayBase64Codec
import dev.nordix.irbridge.core.utils.ID
import dev.nordix.irbridge.remotes.data.entity.RemoteCommandEntity
import dev.nordix.irbridge.remotes.data.entity.RemoteDBView
import dev.nordix.irbridge.remotes.data.entity.RemoteEntity

internal fun Remote.toEntity() : RemoteEntity = RemoteEntity(
    id = id.value,
    name = name,
    description = description,
    order = order,
    showInWidget = showInWidget
)

internal fun RemoteDBView.toDomain() : Remote = Remote(
    id = ID(remote.id),
    name = remote.name,
    description = remote.description,
    order = remote.order,
    showInWidget = remote.showInWidget,
    commands = commands.map { it.toDomain() },
)

internal fun RemoteCommand.toEntity(remoteId: ID<Remote>) : RemoteCommandEntity = RemoteCommandEntity(
    id = id.value,
    remoteId = remoteId.value,
    name = name,
    description = description,
    icon = icon,
    color = color,
    durations = IntArrayBase64Codec.encode(durations)
)

internal fun RemoteCommandEntity.toDomain() : RemoteCommand = RemoteCommand(
    id = ID(id),
    name = name,
    description = description,
    icon = icon,
    color = color,
    durations = IntArrayBase64Codec.decode(durations)
)
