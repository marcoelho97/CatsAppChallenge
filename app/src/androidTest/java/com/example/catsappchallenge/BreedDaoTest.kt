package com.example.catsappchallenge

import androidx.room.Room
import androidx.room.Room.inMemoryDatabaseBuilder
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.catsappchallenge.data.dao.BreedDao
import com.example.catsappchallenge.data.database.CatsAppDatabase
import com.example.catsappchallenge.data.model.Breed
import com.example.catsappchallenge.data.model.BreedListDTO
import com.example.catsappchallenge.data.model.breedListDTO
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BreedDaoTest {

    private lateinit var database: CatsAppDatabase
    private lateinit var breedDao: BreedDao
    private val breedOne = Breed(
        id = "abc",
        name = "BreedOne",
        origin = "Origin",
        temperament = listOf("Friendly"),
        description = "Description",
        image = "image_url",
        lowLifeSpan = 10f,
        highLifeSpan = 15f,
        favourite = false
    )
    private val breedTwo = Breed(
        id = "efg",
        name = "BreedTwo",
        origin = "OriginTwo",
        temperament = listOf("FriendlyTwo"),
        description = "DescriptionTwo",
        image = "image_urlTwo",
        lowLifeSpan = 20f,
        highLifeSpan = 30f,
        favourite = false
    )

    @Before
    fun setup() {
        database = inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CatsAppDatabase::class.java
        ).allowMainThreadQueries().build()

        breedDao = CatsAppDatabase.getDatabase(ApplicationProvider.getApplicationContext()).breedDao
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun upsertBreed_insertsBreed() = runBlocking {
        val breed = breedOne
        breedDao.upsertBreed(breed)

        val loaded = breedDao.getBreedById(breed.id)
        assertEquals(loaded, breed)
    }

    @Test
    fun upsertBreedList_insertsBreeds() = runBlocking {
        val breeds = listOf(
            breedOne,
            breedTwo
        )
        breedDao.upsertBreedList(breeds)

        val loadedBreed1 = breedDao.getBreedById(breeds[0].id)
        val loadedBreed2 = breedDao.getBreedById(breeds[1].id)

        assertEquals(loadedBreed1, breeds[0])
        assertEquals(loadedBreed2, breeds[1])
    }

    @Test
    fun updateFavouriteByBreedId_updatesFavourite() = runBlocking {
        val breed = breedOne
        breedDao.upsertBreed(breed)


        val updatedCount = breedDao.updateFavouriteByBreedId(breed.id, true)
        val loaded = breedDao.getBreedById(breed.id)

        assertEquals(updatedCount, 1)
        assertEquals(loaded?.favourite, true)
    }

    @Test
    fun getAllBreeds_returnsAllBreeds() = runBlocking {
        val breeds = listOf(
            breedOne,
            breedTwo
        )
        breedDao.upsertBreedList(breeds)

        val loadedBreeds = breedDao.getAllBreeds("%", null)
        assertEquals(loadedBreeds.size, 2)
    }

    @Test
    fun getAllBreeds_withFilterFavourite_returnsFilteredBreeds() = runBlocking {
        val breeds = listOf(
            breedOne,
            breedTwo
        )
        breedDao.upsertBreedList(breeds)

        val updatedCount = breedDao.updateFavouriteByBreedId(breedTwo.id, true)
        val loadedBreeds = breedDao.getAllBreeds("%", true)
        assertEquals(loadedBreeds.size, 1)
        assertEquals(loadedBreeds[0].id, "efg")
    }

    @Test
    fun getAllBreeds_withSearchFilter_returnsFilteredBreeds() = runBlocking {
        val breeds = listOf(
            breedOne,
            breedTwo
        )
        breedDao.upsertBreedList(breeds)

        val loadedBreeds = breedDao.getAllBreeds("%reedO%", null)
        assertEquals(loadedBreeds.size, 1)
        assertEquals(loadedBreeds[0].id,"abc")
    }
}