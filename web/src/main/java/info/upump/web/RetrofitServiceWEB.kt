package info.upump.web

import info.upump.web.client.CycleRetrofitClient
import info.upump.web.client.RetrofitClient

class RetrofitServiceWEB {
    companion object {
        fun getCycleService(): CycleRetrofitClient {
            return RetrofitClient.getClient().create(CycleRetrofitClient::class.java)
        }
    }
}