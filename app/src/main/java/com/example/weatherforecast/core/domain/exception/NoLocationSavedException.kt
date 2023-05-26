package com.example.weatherforecast.core.domain.exception

data class NoLocationSavedException(override val message: String = "Please search for a location") : Exception()