package com.example.catsappchallenge.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object SearchManager {
    private val _searchFilter = MutableLiveData("")
    val searchFilter: LiveData<String> get() = _searchFilter

    fun setSearchQuery(query: String) {
        _searchFilter.value = query
    }

    fun prepareSearchFilter(filter: String?): String {
        return "%${filter ?: ""}%"
    }
}