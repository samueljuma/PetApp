package com.samueljuma.petapp

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.samueljuma.petapp.data.CatsAPI
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import java.io.IOException
import kotlin.jvm.Throws

class CatsAPITest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var catsAPI: CatsAPI

    @Before
    fun setup(){
        //setup mock web server
        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = MockRequestDispatcher()
        mockWebServer.start()

        // set up Retrofit
        val json = Json{
            ignoreUnknownKeys = true
            isLenient = true
        }
        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(json.asConverterFactory(contentType = "application/json".toMediaType()))
            .build()
        catsAPI = retrofit.create(CatsAPI::class.java)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `fetchCats() returns a list of Cats`() = runTest {
        val response = catsAPI.fetchCats("cute")
        assert(response.isSuccessful)
    }

    @After
    @Throws(IOException::class)
    fun tearDown(){
        mockWebServer.shutdown()
    }

}