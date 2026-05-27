package org.goafabric.invoice.process.adapter.authorization

import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.DELETE
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.QueryParam
import org.eclipse.microprofile.faulttolerance.CircuitBreaker
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient

@ApplicationScoped
@RegisterRestClient
@CircuitBreaker
@Path("/locks")
interface LockAdapter {
    @GET
    @Path("acquireLockByKey")
    fun acquireLockByKey(@QueryParam("lockKey") lockKey: String): Lock

    @DELETE
    @Path("removeLockById")
    fun removeLockById(@QueryParam("lockId") lockId: String)
}