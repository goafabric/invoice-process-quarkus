package org.goafabric.invoice.process

import io.quarkus.cache.CacheKeyGenerator
import io.quarkus.cache.CompositeCacheKey
import jakarta.enterprise.context.ApplicationScoped
import org.goafabric.invoice.controller.extensions.UserContext
import java.lang.reflect.Method

@ApplicationScoped
class MyCacheConfiguration : CacheKeyGenerator {
    override fun generate(method: Method, vararg methodParams: Any): Any {
        val key = CompositeCacheKey(UserContext.tenantId, UserContext.organizationId, method.name, methodParams)
        return key
    }
}