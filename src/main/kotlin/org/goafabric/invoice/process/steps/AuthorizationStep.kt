package org.goafabric.invoice.process.steps

import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.rest.client.inject.RestClient
import org.goafabric.invoice.process.adapter.authorization.Lock
import org.goafabric.invoice.process.adapter.authorization.LockAdapter
import org.goafabric.personservice.extensions.UserContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@ApplicationScoped
class AuthorizationStep(
    @param:RestClient private val lockAdapter: LockAdapter) {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    fun acquireLock(): Lock {
        log.info("acquire lock")
        val lock = lockAdapter.acquireLockByKey("invoice-" + UserContext.tenantId)
        check(!lock.isLocked) { "process is already locked" }
        return lock
    }

    fun releaseLock(lock: Lock?) {
        if (lock != null) {
            log.info("release lock")
            lockAdapter.removeLockById(lock.id)
        }
    }
}
