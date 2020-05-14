package com.elevenetc.android.resta.features.rests.api.foursquare

import okhttp3.Interceptor

internal class VerInterceptor(
        private val version: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val original = chain.request()
        val originalHttpUrl = original.url()

        val url = originalHttpUrl.newBuilder()
                .addQueryParameter("v", version)
                .build()

        return chain.proceed(original.newBuilder().url(url).build())
    }
}