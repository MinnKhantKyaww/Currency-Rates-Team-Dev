package com.spicysoft.currencyexchange.model.dto

import com.fasterxml.jackson.annotation.JsonProperty
import org.joda.time.DateTime
import java.text.DecimalFormat

data class ExchangeData(
    val updatedTime: DateTime,
    val rates: List<ExchangeRate>
)

data class ExchangeRate(
    val shortName: String,
    val desc: String,
    val rate: String
) {
    val _rate: String
        get() = when (shortName) {
            in "JPY", "KHR", "IDR", "KRW", "LAK", "VND"-> {
                val df = DecimalFormat("#,###.###")
                df.format(rate.replace(",", "").toDouble().div(100))
            }
            else -> rate
        }
}

class CurrencyData {
    val info: String = ""
    val description: String = ""
    val timestamp: String = ""
    val rates = CurrencyRates()
}

class DescriptionData {
    val info: String = ""
    val description: String = ""
    val currencies = CurrencyRates()
}


class CurrencyRates {

    @JsonProperty("USD") var USD = ""
    @JsonProperty("ZAR") var ZAR = ""
    @JsonProperty("NPR") var NPR = ""
    @JsonProperty("CNY") var CNY = ""
    @JsonProperty("CHF") var CHF = ""
    @JsonProperty("THB") var THB = ""
    @JsonProperty("PKR") var PKR = ""
    @JsonProperty("KES") var KES = ""
    @JsonProperty("BDT") var BDT = ""
    @JsonProperty("EGP") var EGP = ""
    @JsonProperty("SAR") var SAR = ""
    @JsonProperty("LAK") var LAK = ""
    @JsonProperty("IDR") var IDR = ""
    @JsonProperty("KHR") var KHR = ""
    @JsonProperty("SGD") var SGD = ""
    @JsonProperty("NZD") var NZD = ""
    @JsonProperty("LKR") var LKR = ""
    @JsonProperty("CZK") var CZK = ""
    @JsonProperty("JPY") var JPY = ""
    @JsonProperty("VND") var VND = ""
    @JsonProperty("PHP") var PHP = ""
    @JsonProperty("KRW") var KRW = ""
    @JsonProperty("BRL") var BRL = ""
    @JsonProperty("HKD") var HKD = ""
    @JsonProperty("RSD") var RSD = ""
    @JsonProperty("MYR") var MYR = ""
    @JsonProperty("CAD") var CAD = ""
    @JsonProperty("GBP") var GBP = ""
    @JsonProperty("SEK") var SEK = ""
    @JsonProperty("NOK") var NOK = ""
    @JsonProperty("ILS") var ILS = ""
    @JsonProperty("DKK") var DKK = ""
    @JsonProperty("AUD") var AUD = ""
    @JsonProperty("RUB") var RUB = ""
    @JsonProperty("KWD") var KWD = ""
    @JsonProperty("INR") var INR = ""
    @JsonProperty("BND") var BND = ""
    @JsonProperty("EUR") var EUR = ""

}