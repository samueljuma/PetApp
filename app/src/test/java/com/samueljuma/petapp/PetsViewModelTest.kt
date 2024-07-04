package com.samueljuma.petapp

import com.samueljuma.petapp.data.Cat
import com.samueljuma.petapp.data.PetsRepository
import com.samueljuma.petapp.viewmodel.PetsViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class PetsViewModelTest {
    private val petsRepository = mockk<PetsRepository>(relaxed = true)
    private lateinit var petsViewModel: PetsViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        petsViewModel = PetsViewModel(petsRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testGetPets() {
        val cats = listOf(
            Cat(
                id = "1",
                owner = "Owner 1",
                tags = listOf("Tag 1", "Tag 2"),
                createdAt = "2023-09-01T00:00:00Z",
                updatedAt = "2023-09-01T00:00:00Z",
                isFavorite = false
            )
        )

        //Given
        coEvery { petsRepository.getPets() } returns flowOf(cats)
        //When
        petsViewModel.getPets()

        /* Verifies that the getPets() function of the
         * petsRepository was actually called during the test,
         * ensuring that the view model interacts with the repository as expected.
        */
        coVerify { petsRepository.getPets() }
        //Then
        val uiState = petsViewModel.petsUIState.value
        assertEquals(cats, uiState.pets)
    }

    @Test
    fun testUpdatePet(){

        //Given
        val cat = Cat(
            id = "1",
            owner = "Test Owner",
            tags =listOf("tag1", "tag2"),
            createdAt = "2023-08-01T00:00:00Z",
            updatedAt = "2023-08-02T00:00:00Z",
            isFavorite = false
        )

        //When
        petsViewModel.updatePet(cat.copy(isFavorite = true))
        //Then
        coEvery { petsRepository.updatePet(cat.copy(isFavorite = true))}
    }

    @Test
    fun testGetFavoritePets(){
        //Given
        val favoriteCats = listOf(
            Cat(
                id = "1",
                owner = "Test Owner",
                tags =listOf("tag1", "tag2"),
                createdAt = "2023-08-01T00:00:00Z",
                updatedAt = "2023-08-02T00:00:00Z",
                isFavorite = false
            )
        )
        coEvery { petsRepository.getFavouritePets() } returns flowOf(favoriteCats)

        //When
        petsViewModel.getFavoritePets()

        //Then
        coVerify { petsRepository.getFavouritePets() }
        assertEquals(favoriteCats, petsViewModel.favoritePets.value)
    }

}