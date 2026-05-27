package org.goafabric.invoice.process.adapter.authorization

import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.QueryParam
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient

@ApplicationScoped
@RegisterRestClient
interface LockAdapter {
    fun acquireLockByKey(@QueryParam("lockKey") lockKey: String): Lock

    fun removeLockById(@QueryParam("lockId") lockId: String)
}