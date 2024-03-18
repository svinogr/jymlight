package info.upump.jymlight

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import info.upump.database.DatabaseApp
import info.upump.database.RoomDB


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
const val IS_LOCALDB = false

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("Init app", "init")

        if (IS_LOCALDB) {
            DatabaseApp.initilizeDb(this@App, RoomDB.BASE_NAME, RoomDB.DB_PATH)
        } else{
            // TODO убрать когда будет готов web
            DatabaseApp.initilizeDb(this@App, RoomDB.BASE_NAME, RoomDB.DB_PATH)
        }

        /*val l = CoroutineScope(Job())
        l.launch(Dispatchers.IO) {
            val c = RetrofitClient.getClient().create(CycleService::class.java)
            val allTemplateCycle = c.getAllTemplateCycle()
            val execute = allTemplateCycle.enqueue(object : Callback<MutableList<CycleRet>>{
                override fun onResponse(
                    call: Call<MutableList<CycleRet>>,
                    response: Response<MutableList<CycleRet>>
                ) {
                    Log.d("Init app", "${response.body()}")

                }

                override fun onFailure(call: Call<MutableList<CycleRet>>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })


        }*/
    }
}