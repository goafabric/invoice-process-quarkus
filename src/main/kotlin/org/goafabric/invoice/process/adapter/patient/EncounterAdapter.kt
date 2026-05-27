package org.goafabric.invoice.process.adapter.patient

import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.QueryParam
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import org.goafabric.invoice.process.adapter.patient.dto.Encounter

@ApplicationScoped
@RegisterRestClient
interface EncounterAdapter {
    fun findByPatientIdAndDisplay(
        @QueryParam("patientId") patientId: String?,
        @QueryParam("display") display: String?
    ): MutableList<Encounter>
}