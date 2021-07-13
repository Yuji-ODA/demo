package com.example.demo.lib

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(SnakeCaseStrategy::class)
data class Huga(
    @JsonProperty("ID") var id: Int,
    @JsonProperty("c_value") var cValue: Float,
    var beType: String) {
    companion object {
        fun dead(): Huga = Huga(100, 1.23864F, "俺は死んじまった打")
    }
}
