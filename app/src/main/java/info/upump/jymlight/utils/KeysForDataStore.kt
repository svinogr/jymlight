package info.upump.jymlight.utils

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey

class KeysForDataStore {
    companion object{
        val START_KEY = intPreferencesKey("start")
        val FINISH_KEY = intPreferencesKey("finish")
    }
}