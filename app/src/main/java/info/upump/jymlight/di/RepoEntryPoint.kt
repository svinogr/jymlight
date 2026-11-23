package info.upump.jymlight.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import info.upump.database.repo.CycleRepo

@EntryPoint
@InstallIn(SingletonComponent::class)
interface RepoEntryPoint {
    fun cycleRepo(): CycleRepo
}
