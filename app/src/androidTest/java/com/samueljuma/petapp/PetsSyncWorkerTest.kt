package com.samueljuma.petapp

import android.util.Log
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.WorkManagerTestInitHelper
import com.samueljuma.petapp.workers.PetsSyncWorker
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PetsSyncWorkerTest {
    @get:Rule
    val koinTestRule = KoinTestRule()

    @Before
    fun setUp() {
        val config =
            Configuration.Builder()
                .setMinimumLoggingLevel(Log.DEBUG)
                .setExecutor(SynchronousExecutor())
                .build()

        // Initialize WorkManager for instrumentation tests
        WorkManagerTestInitHelper.initializeTestWorkManager(
            ApplicationProvider.getApplicationContext(),
            config,
        )
    }

    @Test
    fun testPetsSyncWorker() {
        // Create request
        val syncPetsWorkRequest =
            OneTimeWorkRequestBuilder<PetsSyncWorker>()
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .setRequiresBatteryNotLow(true)
                        .build(),
                )
                .build()

        // Setting up Test Drivers
        val workManager = WorkManager.getInstance(ApplicationProvider.getApplicationContext())
        val testDriver = WorkManagerTestInitHelper.getTestDriver(ApplicationProvider.getApplicationContext())!!

        // Enqueue work request
        workManager.enqueueUniqueWork(
            "PetsSyncWorker",
            ExistingWorkPolicy.KEEP,
            syncPetsWorkRequest,
        ).result.get()

        // Get WorkInfo and OutPutData
        val workInfo = workManager.getWorkInfoById(syncPetsWorkRequest.id).get()

        // Assert that work is enqueued
        assertEquals(WorkInfo.State.ENQUEUED, workInfo.state)

        // simulate constraints met
        testDriver.setAllConstraintsMet(syncPetsWorkRequest.id)

        // Get Output and state of worker
        val postRequirementWorkInfo = workManager.getWorkInfoById(syncPetsWorkRequest.id).get()
        assertEquals(WorkInfo.State.RUNNING, postRequirementWorkInfo.state)
    }
}