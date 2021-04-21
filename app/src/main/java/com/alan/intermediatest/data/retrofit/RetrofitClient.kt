package com.alan.intermediatest.data.retrofit

import com.alan.intermediatest.utils.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private val retrofitClient: Retrofit.Builder by lazy {
//        val client = OkHttpClient.Builder().addInterceptor(HeaderInterceptor()).build()
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder().addInterceptor(logging).build()

        Retrofit.Builder()
            .baseUrl(Constants.SERVER_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiInterface: MarvelInterface by lazy {
        retrofitClient
            .build()
            .create(MarvelInterface::class.java)
    }


}

private class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
//                .addHeader("api-key", LocalStorageRepository.getApiKey())
                .build()
        )
    }
}

