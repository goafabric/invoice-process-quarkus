package org.goafabric.invoice.process.steps

import jakarta.enterprise.context.ApplicationScoped
import org.goafabric.invoice.persistence.EpisodeDetailsRepository
import org.goafabric.invoice.persistence.entity.EpisodeDetails
import org.goafabric.invoice.process.adapter.catalog.ConditionAdapter
import org.goafabric.invoice.process.adapter.patient.EncounterAdapter
import org.goafabric.invoice.process.adapter.patient.PatientAdapter
import org.goafabric.invoice.process.adapter.patient.dto.Encounter
import org.goafabric.invoice.process.adapter.patient.dto.Patient
import org.goafabric.invoice.process.adapter.patient.type.MedicalRecordType
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@ApplicationScoped
class EpisodeStep(
    patientAdapter: PatientAdapter,
    encounterAdapter: EncounterAdapter,
    conditionAdapter: ConditionAdapter,
    episodeDetailsRepository: EpisodeDetailsRepository
) {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    private val patientAdapter: PatientAdapter
    private val encounterAdapter: EncounterAdapter
    private val conditionAdapter: ConditionAdapter
    private val episodeDetailsRepository: EpisodeDetailsRepository

    @Value("\${adapter.catalogservice.url:}")
    private val catalogServiceUrl: String? = null

    init {
        this.patientAdapter = patientAdapter
        this.encounterAdapter = encounterAdapter
        this.conditionAdapter = conditionAdapter
        this.episodeDetailsRepository = episodeDetailsRepository
    }

    fun retrieveRecords(familyName: String?) {

        log.info("creating episodes from records")
        val patients: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            patientAdapter.findPatientNamesByFamilyName(familyName)
        if (!patients.isEmpty()) {
            val patient: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
                patients.getFirst()
            createPatient(patientAdapter.getById(patient.id()))

            val encounters: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
                encounterAdapter.findByPatientIdAndDisplay(patient.id(), "")
            createChargeItems(encounters)
            createConditions(encounters)
        }
    }

    private fun createPatient(patient: Patient) {
        val episodeId = "1"
        episodeDetailsRepository.save(
            EpisodeDetails(
                episodeId,
                patient.id(),
                "patient",
                null,
                null,
                patient.familyName(),
                patient.givenName(),
                patient.address().getFirst().city(),
                patient.address().getFirst().street()
            )
        )
    }

    private fun createChargeItems(encounters: MutableList<Encounter?>) {
        log.info("chargeitems")
        encounters.getFirst().medicalRecords().stream()
            .filter({ medicalRecord -> medicalRecord.type().equals(MedicalRecordType.CHARGEITEM) })
            .forEach({ chargeItem ->
                val episodeId = "1"
                episodeDetailsRepository.save(
                    EpisodeDetails(
                        episodeId,
                        chargeItem.id(),
                        "chargeitem",
                        chargeItem.code(),
                        chargeItem.display(),
                        null,
                        null,
                        null,
                        null
                    )
                )
                log.info(chargeItem.toString())
            })
    }

    private fun createConditions(encounters: MutableList<Encounter?>) {
        log.info("conditions")
        encounters.getFirst().medicalRecords().stream()
            .filter({ medicalRecord -> medicalRecord.type().equals(MedicalRecordType.CONDITION) })
            .filter({ medicalRecord -> !medicalRecord.code().equals("none") })
            .forEach({ condition ->
                val episodeId = "1"
                episodeDetailsRepository.save(
                    EpisodeDetails(
                        episodeId,
                        condition.id(),
                        "condition",
                        condition.code(),
                        condition.display(),
                        null,
                        null,
                        null,
                        null
                    )
                )
                log.info(condition.toString())
                log.info(
                    if (!catalogServiceUrl!!.isEmpty()) conditionAdapter.findByCode(condition.code()).toString() else ""
                )
            })
    }
}
