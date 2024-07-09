package com.samueljuma.petapp

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.samueljuma.petapp.data.CatDao
import com.samueljuma.petapp.data.CatDatabase
import com.samueljuma.petapp.data.CatEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CatsDaoTest {
    private lateinit var database: CatDatabase
    private lateinit var catDao: CatDao

    @Before
    fun createDatabase() {
        database =
            Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                CatDatabase::class.java,
            ).allowMainThreadQueries().build()

        catDao = database.catDao()
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun testInsertAndReadCat() =
        runTest {
            // Given a cat
            val cat =
                CatEntity(
                    id = "1",
                    owner = "Test Owner",
                    tags = listOf("tag1", "tag2"),
                    createdAt = "2023-08-01T00:00:00Z",
                    updatedAt = "2023-08-02T00:00:00Z",
                    isFavorite = false,
                )
            // Insert cat to database
            catDao.insert(cat)
            // Then the cat should be in the database
            val cats = catDao.getCats()
            assert(cats.first().isNotEmpty())
            assert(cats.first().contains(cat))
        }

    @Test
    fun testAddCatToFavorites() =
        runTest {
            // Given a cat
            val cat =
                CatEntity(
                    id = "1",
                    owner = "Test Owner",
                    tags = listOf("tag1", "tag2"),
                    createdAt = "2023-08-01T00:00:00Z",
                    updatedAt = "2023-08-02T00:00:00Z",
                    isFavorite = false,
                )

            // Insert cat to database
            catDao.insert(cat)
            // Add cat to favorites
            catDao.update(cat.copy(isFavorite = true))
            // Then the cat should be in the favorites
            val favoriteCats = catDao.getFavoriteCats()
            assert(favoriteCats.first().contains(cat.copy(isFavorite = true)))
        }
}