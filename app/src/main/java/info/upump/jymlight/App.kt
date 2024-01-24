package info.upump.jymlight

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import info.upump.database.DatabaseApp
import info.upump.database.RoomDB
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("Init app", "init")
        DatabaseApp.initilizeDb(this@App, RoomDB.BASE_NAME, RoomDB.DB_PATH)
    }
}