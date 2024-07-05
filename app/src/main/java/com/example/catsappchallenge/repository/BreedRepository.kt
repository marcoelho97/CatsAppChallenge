package com.example.catsappchallenge.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.widget.Toast
import com.example.catsappchallenge.R
import com.example.catsappchallenge.data.dao.BreedDao
import com.example.catsappchallenge.data.model.Breed
import com.example.catsappchallenge.data.model.BreedListDTO
import com.example.catsappchallenge.network.RetrofitInstance
import com.example.catsappchallenge.utils.SearchManager.prepareSearchFilter
import com.example.catsappchallenge.utils.splitLifeSpan
import com.example.catsappchallenge.utils.toastMessage
import com.example.catsappchallenge.viewmodel.BreedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BreedRepository(private val context: Context, private val breedDao: BreedDao) {
    private var breedList: List<BreedListDTO> = emptyList()

    private lateinit var connectivityManager: ConnectivityManager

    fun isConnected(): Boolean {
        val hasInternet: Boolean

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                    ?: return false
            hasInternet = when {
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            hasInternet = try {
                if (connectivityManager.activeNetworkInfo == null) {
                    false
                } else {
                    connectivityManager.activeNetworkInfo?.isConnected!!
                }
            } catch (e: Exception) {
                false
            }
        }
        return hasInternet
    }

    fun registerNetworkCallback(breedViewModel: BreedViewModel) {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(
            networkRequest,
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)

                    CoroutineScope(Dispatchers.IO).launch {
                        fetchAndInsertBreeds(breedViewModel)
                    }
                }
            }
        )
    }

    suspend fun fetchAndInsertAllBreeds() = withContext(Dispatchers.IO) {
        val breedList = getAllBreedsFromDb(searchFilter = null, filterFavourites = null)
        if (breedList.isNotEmpty()) {
            return@withContext
        }

        // If isn't connected, ViewModel will registerNetworkCallback
        if (isConnected()) {
            fetchAndInsertBreeds()
        }
        return@withContext
    }

    private suspend fun fetchAndInsertBreeds(breedViewModel: BreedViewModel? = null) {
        try {
            val allBreeds = RetrofitInstance.api.getAllBreeds()
            val breedsToInsert = allBreeds.map { breed ->

                val temperaments: List<String> = breed.temperament.split(",").map { it.trim() }
                val lifeSpan: Pair<Float, Float> = splitLifeSpan(breed.life_span)

                Breed(
                    id = breed.id,
                    name = breed.name,
                    origin = breed.origin,
                    temperament = temperaments,
                    description = breed.description,
                    image = breed.reference_image_id,
                    lowLifeSpan = lifeSpan.first,
                    highLifeSpan = lifeSpan.second,
                    favourite = false
                )
            }
            breedDao.upsertBreedList(breedsToInsert)
            breedViewModel?.getAllBreeds()
        } catch (e: Exception) {
            toastMessage(context, R.string.list_error_fetching_breeds)
        }
    }

    suspend fun getAllBreedsFromDb(
        searchFilter: String?,
        filterFavourites: Boolean?
    ): List<BreedListDTO> =
        withContext(Dispatchers.IO) {
            breedList = breedDao.getAllBreeds(
                searchFilter = prepareSearchFilter(searchFilter),
                filterFavourite = filterFavourites
            )
            return@withContext breedList
        }

    suspend fun getBreedById(breedId: String): Breed? {
        return breedDao.getBreedById(breedId)
    }

    suspend fun updateFavouriteByBreed(
        breedId: String,
        favourite: Boolean
    ): Boolean =
        withContext(Dispatchers.IO) {
            // TODO: Test with a non existent ID
            return@withContext breedDao.updateFavouriteByBreedId(
                breedId = breedId,
                favourite = favourite
            ) == 1
        }
}
