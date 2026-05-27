package org.goafabric.invoice.process.adapter.catalog

import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.QueryParam
import org.eclipse.microprofile.faulttolerance.CircuitBreaker
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient

@ApplicationScoped
@RegisterRestClient
@CircuitBreaker
@Path("/conditions")
interface ConditionAdapter {
    @GET
    @Path("findByCode")
    fun findByCode(@QueryParam("code") code: String?): Condition?
}