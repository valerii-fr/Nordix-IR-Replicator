package dev.nordix.irbridge.remotes.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import dev.nordix.irbridge.remotes.data.entity.RemoteCommandEntity
import dev.nordix.irbridge.remotes.data.entity.RemoteEntity

@Database(
    entities = [
        RemoteEntity::class,
        RemoteCommandEntity::class,
    ],
    version = 2,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
    ]
)
internal abstract class RemoteDatabase : RoomDatabase() {
    abstract val remoteDao: RemoteDao
}
