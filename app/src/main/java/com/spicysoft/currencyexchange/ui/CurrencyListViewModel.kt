package com.spicysoft.currencyexchange.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.spicysoft.currencyexchange.model.RetrofitManager
import com.spicysoft.currencyexchange.model.api.CurrencyAPI
import com.spicysoft.currencyexchange.model.dto.ExchangeRate
import com.spicysoft.currencyexchange.model.service.CurrencyService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.joda.time.DateTime

class CurrencyListViewModel(application: Application) : AndroidViewModel(application) {

    private val service: CurrencyService

    private val compositeDisposable = CompositeDisposable()

    private var _rates: List<ExchangeRate>? = null

    val rates = MutableLiveData<List<ExchangeRate>>()

    val updateTime = MutableLiveData<DateTime>()

    init {
        service = CurrencyService(RetrofitManager.create(CurrencyAPI::class.java))
    }

    fun loadCurrencyRate() {
        val disposable = service.getCurrencyRates()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                updateTime.value = it.updatedTime
                rates.value = it.rates
                _rates = it.rates
            }, {
                it.printStackTrace()
            })
        compositeDisposable.add(disposable)
    }

    fun filterCurrencyRates(values: String) {

        _rates?.also {
            rates.value = it.filter { rate ->
                rate.shortName.toLowerCase().startsWith(values.toLowerCase())
            }
        }

    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}