package info.upump.jymlight

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import info.upump.database.DatabaseApp
import info.upump.database.RoomDB
import info.upump.web.client.CycleService
import info.upump.web.client.RetrofitClient
import info.upump.web.model.CycleRet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.coroutineContext


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("Init app", "init")
        DatabaseApp.initilizeDb(this@App, RoomDB.BASE_NAME, RoomDB.DB_PATH)

        val l = CoroutineScope(Job())
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


        }
    }
}