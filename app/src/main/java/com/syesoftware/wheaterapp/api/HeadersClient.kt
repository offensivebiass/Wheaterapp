package com.syesoftware.wheaterapp.api

import android.content.Context
import android.util.Log
import com.syesoftware.wheaterapp.utils.NetworkUtil
import com.syesoftware.wheaterapp.utils.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class HeadersClient(context: Context) : Interceptor {

    private var context = context;
    private val TAG = "HeadersClient"

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!NetworkUtil.isOnline(context)) {
            throw NoConnectivityException()
        }
        val original = chain.request()

        // Request customization: add request headers
        val requestBuilder = original.newBuilder()
                .header("x-api-key", "fbba3cce2c745b1f963df4cbea208546")

        val request = requestBuilder.build()
        return chain.proceed(request)
    }

}
