package org.goafabric.invoice.process.persistence.entity

import java.util.*

data class EpisodeDetails(
    val id: String?,
    val episodeId: String,
    val referenceId: String,
    val type: String,
    val code: String?,
    val display: String?,
    val patientFamily: String?,
    val patientGiven: String?,
    val patientCity: String?,
    val patientStreet: String?
) {
    // Primary constructor is provided by default
    // Secondary constructor that generates a UUID for the id
    constructor(
        episodeId: String,
        referenceId: String,
        type: String,
        code: String?,
        display: String?,
        patientFamily: String?,
        patientGiven: String?,
        patientCity: String?,
        patientStreet: String?
    ) : this(
        UUID.randomUUID().toString(),
        episodeId,
        referenceId,
        type,
        code,
        display,
        patientFamily,
        patientGiven,
        patientCity,
        patientStreet
    )
}
