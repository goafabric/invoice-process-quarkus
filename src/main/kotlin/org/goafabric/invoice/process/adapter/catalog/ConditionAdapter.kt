package org.goafabric.invoice.process.adapter.catalog

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.QueryParam
import org.eclipse.microprofile.faulttolerance.CircuitBreaker
import org.eclipse.microprofile.faulttolerance.Timeout
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import org.goafabric.invoice.process.adapter.AdapterConfiguration

@Path("/conditions")
@RegisterRestClient
@Timeout
@CircuitBreaker
@RegisterClientHeaders(AdapterConfiguration::class)
interface ConditionAdapter {
    @GET
    @Path("findByCode")
    fun findByCode(@QueryParam("code") code: String?): Condition?
}