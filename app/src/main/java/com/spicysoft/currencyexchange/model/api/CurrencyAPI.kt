package com.spicysoft.currencyexchange.model.api

import com.spicysoft.currencyexchange.model.dto.CurrencyData
import com.spicysoft.currencyexchange.model.dto.DescriptionData
import io.reactivex.Observable
import retrofit2.http.GET

interface CurrencyAPI {

    @GET("latest")
    fun getCurrencies(): Observable<CurrencyData>;

    @GET("currencies")
    fun getDescription(): Observable<DescriptionData>;
}