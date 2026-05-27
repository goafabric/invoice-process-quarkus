package org.goafabric.invoice.process.adapter.catalog

import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.QueryParam
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient

@ApplicationScoped
@RegisterRestClient
interface ConditionAdapter {
    fun findByCode(@QueryParam("code") code: String?): Condition?
}