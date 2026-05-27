package org.goafabric.invoice.process.persistence.entity

import java.util.*

data class Episode(
    val id: String?,
    val patientId: String?,
    val creationYear: Int?,
    val creationQuarter: Int?
) {
    constructor(patientId: String?, creationYear: Int?, creationQuarter: Int?) : this(
        UUID.randomUUID().toString(),
        patientId,
        creationYear,
        creationQuarter
    )
}
