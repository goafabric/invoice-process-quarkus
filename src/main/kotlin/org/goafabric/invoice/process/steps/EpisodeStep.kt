package org.goafabric.invoice.process.steps

import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.eclipse.microprofile.rest.client.inject.RestClient
import org.goafabric.invoice.process.adapter.catalog.ConditionAdapter
import org.goafabric.invoice.process.adapter.patient.EncounterAdapter
import org.goafabric.invoice.process.adapter.patient.PatientAdapter
import org.goafabric.invoice.process.adapter.patient.dto.Encounter
import org.goafabric.invoice.process.adapter.patient.dto.Patient
import org.goafabric.invoice.process.adapter.patient.type.MedicalRecordType
import org.goafabric.invoice.process.persistence.EpisodeDetailsRepository
import org.goafabric.invoice.process.persistence.entity.EpisodeDetails
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

@ApplicationScoped
class EpisodeStep(
    @param:RestClient private val patientAdapter: PatientAdapter,
    @param:RestClient private val encounterAdapter: EncounterAdapter,
    @param:RestClient private val conditionAdapter: ConditionAdapter,
    private val episodeDetailsRepository: EpisodeDetailsRepository,
    @param:ConfigProperty(name = "adapter.catalogservice.url")
    private val catalogServiceUrl: Optional<String>

) {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    fun retrieveRecords(familyName: String) {

        log.info("creating episodes from records")
        val patients = patientAdapter.findPatientNamesByFamilyName(familyName)
        if (!patients.isEmpty()) {
            val patient = patients.first()
            createPatient(patientAdapter.getById(patient.id))

            val encounters = encounterAdapter.findByPatientIdAndDisplay(patient.id, "")
            createChargeItems(encounters)
            createConditions(encounters)
        }
    }

    private fun createPatient(patient: Patient) {
        val episodeId = "1"
        episodeDetailsRepository.save(
            EpisodeDetails(
                episodeId,
                patient.id!!,
                "patient",
                null,
                null,
                patient.familyName,
                patient.givenName,
                patient.address.first().city,
                patient.address.first().street
            )
        )
    }

    private fun createChargeItems(encounters: List<Encounter>) {
        log.info("chargeitems")
        encounters.first().medicalRecords.stream()
            .filter({ medicalRecord -> medicalRecord.type == MedicalRecordType.CHARGEITEM })
            .forEach({ chargeItem ->
                val episodeId = "1"
                episodeDetailsRepository.save(
                    EpisodeDetails(
                        episodeId,
                        chargeItem.id!!,
                        "chargeitem",
                        chargeItem.code,
                        chargeItem.display,
                        null,
                        null,
                        null,
                        null
                    )
                )
                log.info(chargeItem.toString())
            })
    }

    private fun createConditions(encounters: List<Encounter>) {
        log.info("conditions")
        encounters.first().medicalRecords.stream()
            .filter({ medicalRecord -> medicalRecord.type == MedicalRecordType.CONDITION })
            .filter({ medicalRecord -> medicalRecord.code != "none" })
            .forEach({ condition ->
                val episodeId = "1"
                episodeDetailsRepository.save(
                    EpisodeDetails(
                        episodeId,
                        condition.id!!,
                        "condition",
                        condition.code,
                        condition.display,
                        null,
                        null,
                        null,
                        null
                    )
                )
                log.info(condition.toString())
                log.info(
                    if (catalogServiceUrl.isPresent) conditionAdapter.findByCode(condition.code).toString() else ""
                )
            })
    }
}
