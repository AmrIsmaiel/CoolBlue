package com.coolblue.customer.base.data.remote.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RetrofitClient @Inject constructor(
    private val baseURL: String,
    private val httpClient: OkHttpClient.Builder,
    private val httpLoggingInterceptor: HttpLoggingInterceptor,
    private val builder: Retrofit.Builder
) {

    fun getInstance(): Retrofit {
        httpClient.connectTimeout(15, TimeUnit.SECONDS)
        httpClient.readTimeout(30, TimeUnit.SECONDS)
        httpClient.writeTimeout(30, TimeUnit.SECONDS)
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(httpLoggingInterceptor)

        val gson = GsonBuilder()
            .setLenient()
            .serializeNulls()
            .create()

        builder.baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

        builder.client(httpClient.build())
        return builder.build()
    }
}



