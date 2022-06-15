package com.santimattius.template.data.client.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

private const val BASE_URL = "https://api.themoviedb.org"

internal inline fun <reified S> service(key: String): S {
    require(key.isNotBlank()) { "API Key required" }

    val client = OkHttpClient().newBuilder()
        .addInterceptor(RequestInterceptor(key))
        .build()

    return service(baseUrl = BASE_URL, client = client)
}

internal inline fun <reified S> service(baseUrl: String, client: OkHttpClient = OkHttpClient()): S {
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .configure()
        .build()

    return retrofit.create()
}


private fun Retrofit.Builder.configure(): Retrofit.Builder {
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    val moshiConverterFactory = MoshiConverterFactory.create(moshi)
    return this.addConverterFactory(moshiConverterFactory)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
}