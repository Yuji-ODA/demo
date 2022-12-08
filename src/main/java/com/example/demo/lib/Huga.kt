package com.example.demo.lib

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class Huga(
    var id: Int,
    @JsonProperty("c_value") var cvalue: Float,
    var beType: String) {
    companion object {
        fun dead(): Huga = Huga(100, 1.23864F, "俺は死んじまった打")
    }
}
