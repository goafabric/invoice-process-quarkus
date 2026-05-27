package org.goafabric.invoice.process.persistence

import org.goafabric.invoice.process.persistence.entity.EpisodeDetails

object ADTCreator {
    fun createCondition(code: String, display: String): String {
        return "DG1|1|I10|$code^$display"
    }

    fun createChargeItem(code: String, display: String?): String {
        return "FT1|1|$code^$display"
    }

    fun createPatient(familyName: String, givenName: String?, city: String?, street: String?): String {
        return "PID|1|$familyName^$givenName|$city^$street"
    }

    fun fromEpisodeDetails(episodeDetails: EpisodeDetails): String {
        when (episodeDetails.type) {
            "condition" -> {
                return createCondition(episodeDetails.code!!, episodeDetails.display!!)
            }

            "chargeitem" -> {
                return createChargeItem(episodeDetails.code!!, episodeDetails.display!!)
            }

            "patient" -> {
                return createPatient(
                    episodeDetails.patientFamily!!,
                    episodeDetails.patientGiven,
                    episodeDetails.patientCity,
                    episodeDetails.patientStreet
                )
            }

            else -> {
                error("not supported")
            }
        }
    }
}

