package org.goafabric.invoice.process.adapter.patient

import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.faulttolerance.CircuitBreaker
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import org.goafabric.invoice.process.adapter.patient.dto.Patient
import org.goafabric.invoice.process.adapter.patient.dto.PatientNamesOnly

@ApplicationScoped
@RegisterRestClient
@CircuitBreaker
@Path("/patients")
interface PatientAdapter {
    @GET
    @Path("findPatientNamesByFamilyName")
    fun findPatientNamesByFamilyName(@QueryParam("search") search: String): MutableList<PatientNamesOnly>

    @GET
    @Path("getById")
    fun getById(@PathParam("id") id: String): Patient

    @POST
    @Path("save")
    @Consumes(MediaType.APPLICATION_JSON)
    fun save(patient: Patient): Patient
}