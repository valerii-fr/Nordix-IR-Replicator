package dev.nordix.irbridge.export.di

import dev.nordix.irbridge.export.data.ExportManagerImpl
import dev.nordix.irbridge.export.domain.ExportManager
import org.koin.dsl.module

val exportModule = module {
    single<ExportManager> {
        ExportManagerImpl(
            remotesRepository = get()
        )
    }
}
