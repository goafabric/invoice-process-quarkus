package org.goafabric.invoice.process.adapter.patient

import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.QueryParam
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import org.goafabric.invoice.process.adapter.patient.dto.Patient
import org.goafabric.invoice.process.adapter.patient.dto.PatientNamesOnly

@ApplicationScoped
@RegisterRestClient
interface PatientAdapter {
    fun findPatientNamesByFamilyName(@QueryParam("search") search: String): MutableList<PatientNamesOnly>

    fun getById(@PathParam("id") id: String): Patient

    fun save(patient: Patient): Patient
}