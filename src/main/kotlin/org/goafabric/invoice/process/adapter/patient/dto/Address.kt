package org.goafabric.invoice.process.adapter.patient.dto

data class Address(
    val id: String?,
    val version: Long?,

    val use: String,
    val street: String,
    val city: String,
    val postalCode: String,
    val state: String,
    val country: String
)

