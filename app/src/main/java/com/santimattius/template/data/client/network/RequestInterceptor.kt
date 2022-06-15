package com.santimattius.template.data.client.network

import okhttp3.Interceptor
import okhttp3.Response

internal class RequestInterceptor(private val apiKey: String) : Interceptor {

    companion object {
        private const val PATH_PARAM_KEY = "api_key"
    }

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()

        val url = request.url.newBuilder()
            .addQueryParameter(PATH_PARAM_KEY, apiKey)
            .build()

        val newRequest = request.newBuilder()
            .url(url)
            .build()
        return chain.proceed(newRequest)
    }
}