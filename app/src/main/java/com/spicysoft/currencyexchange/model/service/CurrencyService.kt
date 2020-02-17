package com.spicysoft.currencyexchange.model.service

import com.spicysoft.currencyexchange.model.api.CurrencyAPI
import com.spicysoft.currencyexchange.model.dto.CurrencyRates
import com.spicysoft.currencyexchange.model.dto.ExchangeData
import com.spicysoft.currencyexchange.model.dto.ExchangeRate
import io.reactivex.Observable
import org.joda.time.DateTime

class CurrencyService(private val api: CurrencyAPI) {

    fun getCurrencyRates(): Observable<ExchangeData> {

        return api.getCurrencies().map {
            val dataTime = DateTime(it.timestamp.toLong() * 1000)
            val rates = mutableListOf<ExchangeRate>()
            val ratesImp = mutableListOf<ExchangeRate>()

            val desc = api.getDescription().blockingFirst()

            for (f in CurrencyRates::class.java.declaredFields) {
                f.isAccessible = true
                val rate = ExchangeRate(f.name, f.get(desc.currencies) as String, f.get(it.rates) as String)
                when (f.name) {
                    in "USD" , "EUR", "SGD", "THB", "MYR", "GBP" -> {
                        ratesImp.add(rate)
                    }
                    else -> rates.add(rate)
                }

            }
            ratesImp.addAll(rates)

            return@map ExchangeData(dataTime, ratesImp)
        }
    }
}