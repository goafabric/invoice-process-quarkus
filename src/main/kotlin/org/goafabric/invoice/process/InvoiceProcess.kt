/*
package org.goafabric.invoice.process

import jakarta.enterprise.context.ApplicationScoped
import org.goafabric.personservice.extensions.UserContext
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.locks.Lock

@ApplicationScoped
class InvoiceProcess(authorizationStep: AuthorizationStep, invoiceStep: InvoiceStep, episodeStep: EpisodeStep) {
    private val log: org.slf4j.Logger = org.slf4j.LoggerFactory.getLogger(this.javaClass)

    private val authorizationStep: AuthorizationStep
    private val invoiceStep: InvoiceStep
    private val episodeStep: EpisodeStep
    private val executor: ExecutorService

    init {
        this.authorizationStep = authorizationStep
        this.invoiceStep = invoiceStep
        this.episodeStep = episodeStep
        executor = Executors.newVirtualThreadPerTaskExecutor()
    }

    fun run(): java.util.concurrent.Future<kotlin.Boolean?> {
        val userContextMap: Unit */
/* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType *//*
? =
            UserContext.adapterHeaderMap
        return executor.submit<kotlin.Boolean?>(java.util.concurrent.Callable {
            try {
                return innerLoop(userContextMap)
            } catch (e: java.lang.Exception) {
                //Todo this is just a mitigation of lost exceptions in threads, they are still only logged but not handled by main exception handler - @CircuitBreaker inside the submit block seem to work though
                log.error(e.message, e)
                throw e
            }
        })
    }

    @kotlin.Throws(java.lang.InterruptedException::class)
    private fun innerLoop(userContextMap: kotlin.collections.MutableMap<kotlin.String?, kotlin.String?>?): kotlin.Boolean {
        UserContext.setContext(userContextMap)
        log.info("##tenantid inside thread {} ", UserContext.getTenantId())
        //if (true) { throw new IllegalStateException("yo baby"); }
        var lock: Lock? = null
        try {
            lock = authorizationStep.acquireLock()
            episodeStep.retrieveRecords("Burns")
            val invoice: Unit */
/* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType *//*
? =
                invoiceStep.create()
            invoiceStep.check(invoice)
            val encryptedInvoice: Unit */
/* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType *//*
? =
                invoiceStep.encrypt(invoice)
            invoiceStep.send(encryptedInvoice)
            invoiceStep.store(encryptedInvoice)
        } finally {
            authorizationStep.releaseLock(lock)
            log.info("finished ...")
        }
        doSleep()
        return true
    }

    private fun doSleep() {
        log.info("sleeping")
        Thread.sleep(1000)
    }

    @jakarta.annotation.PreDestroy
    private fun shutdown() {
        executor.shutdown()
    }
}
*/
