package com.elevenetc.android.resta.features.rests.api.foursquare

import com.elevenetc.android.resta.BuildConfig
import com.elevenetc.android.resta.features.rests.api.*
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

/**
 * Provides [RestsApi]
 * And defines required dependencies for Foursquare implementation
 */
@Module
class FoursquareModule {

    @Provides
    fun venuesProvider(inst: FoursquareRestsApi): RestsApi = inst

    @Provides
    fun api(
            @Named(Names.CLIENT_ID) clientId: String,
            @Named(Names.CLIENT_SECRET) clientSecret: String,
            @Named(Names.ENDPOINT) endpoint: String,
            @Named(Names.API_VER) apiVer: String
    ): FoursquareApi {

        val client = OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(clientId, clientSecret))
                .addInterceptor(VerInterceptor(apiVer))
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl(endpoint)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        return retrofit.create(FoursquareApi::class.java)
    }

    @Provides
    fun mapper(): Mapper {
        return Mapper()
    }

    @Named(Names.CLIENT_ID)
    @Provides
    fun clientId(): String {
        return BuildConfig.FS_CLIENT_ID
    }

    @Named(Names.CLIENT_SECRET)
    @Provides
    fun clientSecret(): String {
        return BuildConfig.FS_CLIENT_SECRET
    }

    @Named(Names.ENDPOINT)
    @Provides
    fun endpoint(): String {
        return "https://api.foursquare.com/v2/"
    }

    @Named(Names.API_VER)
    @Provides
    fun apiVer(): String {
        return "20180706"
    }

    @Named(Names.VENUES_CATEGORY)
    @Provides
    fun venuesCategory(): String {
        return "4d4b7105d754a06374d81259"
    }

    object Names {
        const val CLIENT_ID = "clientId"
        const val CLIENT_SECRET = "clientSecret"
        const val ENDPOINT = "endpoint"
        const val API_VER = "apiVer"
        const val VENUES_CATEGORY = "venuesCategory"
    }
}