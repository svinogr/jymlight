package info.upump.database

import info.upump.database.entities.CycleEntity
import info.upump.database.entities.CycleFullEntityWithWorkouts

interface RepoInterface<T, R> : RepoActionsSpecific<T, R>  {
}