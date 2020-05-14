package com.elevenetc.android.resta.features.rests.api.foursquare

import okhttp3.Interceptor

internal class AuthInterceptor(
        private val clientId: String,
        private val clientSecret: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val original = chain.request()
        val originalHttpUrl = original.url()

        val url = originalHttpUrl.newBuilder()
                .addQueryParameter("client_id", clientId)
                .addQueryParameter("client_secret", clientSecret)
                .build()

        return chain.proceed(original.newBuilder().url(url).build())
    }
}