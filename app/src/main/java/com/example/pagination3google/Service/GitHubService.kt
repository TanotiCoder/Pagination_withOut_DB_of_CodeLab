package com.example.pagination3google.Service

import com.example.pagination3google.Model.RepoSearchResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val IN_QUALIFIER = "in:name,description"

interface GitHubService {
    @GET("search/repositories?sort=stars")
    suspend fun searchRepo(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): RepoSearchResponse

    companion object {
        private const val BASE_URL = "https://api.github.com/"
        fun create(): GitHubService {
            val logger = HttpLoggingInterceptor()
            logger.level = Level.BASIC
            val client = OkHttpClient.Builder().addInterceptor(logger).build()
            return Retrofit.Builder().baseUrl(BASE_URL).client(client)
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(GitHubService::class.java)
        }
    }
}