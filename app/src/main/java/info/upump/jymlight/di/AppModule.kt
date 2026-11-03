package info.upump.jymlight.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import info.upump.database.RoomDB
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRoomDB(@ApplicationContext context: Context): RoomDB =
        Room.databaseBuilder(context, RoomDB::class.java, RoomDB.BASE_NAME)
            .createFromAsset(RoomDB.BASE_NAME)
            .addMigrations(RoomDB.MIGRATION_1to2)
            .build()
}
