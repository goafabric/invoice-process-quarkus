package org.goafabric.invoice.process.adapter.patient.dto

import org.goafabric.invoice.process.adapter.patient.type.MedicalRecordType

data class MedicalRecord(
    val id: String?,
    val encounterId: String?,
    val version: Long?,

    val type: MedicalRecordType,
    val display: String,
    val code: String,
    val specialization: String?
) {
    constructor(type: MedicalRecordType, display: String, code: String) : this(
        null,
        null,
        null,
        type,
        display,
        code,
        null
    )

    constructor(type: MedicalRecordType, display: String, code: String, specialization: String) : this(
        null,
        null,
        null,
        type,
        display,
        code,
        specialization
    )
}
