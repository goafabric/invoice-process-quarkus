package org.goafabric.invoice.process.adapter.patient.dto

data class ContactPoint(
    val id: String?,
    val version: Long?,

    val use: String,
    val system: String,
    val value: String
)
