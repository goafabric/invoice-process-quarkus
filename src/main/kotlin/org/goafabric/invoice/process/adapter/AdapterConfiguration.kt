package org.goafabric.invoice.process.adapter

import jakarta.ws.rs.core.MultivaluedHashMap
import jakarta.ws.rs.core.MultivaluedMap
import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory
import org.goafabric.invoice.controller.extensions.UserContext

class AdapterConfiguration : ClientHeadersFactory {

    override fun update(
        multivaluedMap: MultivaluedMap<String, String>,
        multivaluedMap1: MultivaluedMap<String, String>
    ): MultivaluedMap<String, String> {
        val result: MultivaluedMap<String, String> = MultivaluedHashMap<String, String>()
        UserContext.adapterHeaderMap.forEach { (key: String, value: String) -> result.add(key, value) }
        return result
    }
}
