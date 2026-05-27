package org.goafabric.invoice.process.adapter.patient.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.time.LocalDate

@JsonIgnoreProperties(ignoreUnknown = true)
data class Patient(
    val id: String?,
    val version: Long?,

    val givenName: String,
    val familyName: String,

    val gender: String,
    val birthDate: LocalDate,

    val address: MutableList<Address>,
    val contactPoint: MutableList<ContactPoint>
)
