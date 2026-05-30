package org.goafabric.invoice.process.adapter.patient

import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.QueryParam
import org.eclipse.microprofile.faulttolerance.CircuitBreaker
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import org.goafabric.invoice.process.adapter.patient.dto.Encounter

@ApplicationScoped
@RegisterRestClient
@CircuitBreaker
@Path("/encounters")
interface EncounterAdapter {
    @GET
    @Path("findByPatientIdAndDisplay")
    fun findByPatientIdAndDisplay(
        @QueryParam("patientId") patientId: String,
        @QueryParam("display") display: String
    ): List<Encounter>
}