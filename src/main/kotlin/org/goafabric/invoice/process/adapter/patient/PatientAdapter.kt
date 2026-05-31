package org.goafabric.invoice.process.adapter.patient

import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.faulttolerance.CircuitBreaker
import org.eclipse.microprofile.faulttolerance.Timeout
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import org.goafabric.invoice.process.adapter.AdapterConfiguration
import org.goafabric.invoice.process.adapter.patient.dto.Patient
import org.goafabric.invoice.process.adapter.patient.dto.PatientNamesOnly

@Path("/patients")
@RegisterRestClient
@Timeout
@CircuitBreaker
@RegisterClientHeaders(AdapterConfiguration::class)
@ApplicationScoped
interface PatientAdapter {
    @GET
    @Path("findPatientNamesByFamilyName")
    fun findPatientNamesByFamilyName(@QueryParam("search") search: String): MutableList<PatientNamesOnly>

    @GET
    @Path("getById/{id}")
    fun getById(@PathParam("id") id: String): Patient

    @POST
    @Path("save")
    @Consumes(MediaType.APPLICATION_JSON)
    fun save(patient: Patient): Patient
}