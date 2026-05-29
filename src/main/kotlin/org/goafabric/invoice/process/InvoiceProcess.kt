package org.goafabric.invoice.process

import jakarta.enterprise.context.ApplicationScoped
import org.goafabric.invoice.process.extensions.UserContext
import org.goafabric.invoice.process.steps.AuthorizationStep
import org.goafabric.invoice.process.steps.EpisodeStep
import org.goafabric.invoice.process.steps.InvoiceStep
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@ApplicationScoped
class InvoiceProcess(private val authorizationStep: AuthorizationStep,
                     private val invoiceStep: InvoiceStep,
                     private val episodeStep: EpisodeStep
) {
    private val log: org.slf4j.Logger = org.slf4j.LoggerFactory.getLogger(this.javaClass)

    private val executor: ExecutorService = Executors.newVirtualThreadPerTaskExecutor()

    fun run(): java.util.concurrent.Future<kotlin.Boolean> {
        val userContextMap = UserContext.adapterHeaderMap
        return executor.submit<Boolean> {
            try {
                return@submit innerLoop(userContextMap)
            } catch (e: java.lang.Exception) {
                //Todo this is just a mitigation of lost exceptions in threads, they are still only logged but not handled by main exception handler - @CircuitBreaker inside the submit block seem to work though
                log.error(e.message, e)
                throw e
            }
        }
    }

    private fun innerLoop(userContextMap: Map<String, String>): Boolean {
        UserContext.setContext(userContextMap)
        log.info("##tenantid inside thread {} ", UserContext.tenantId)
        var lock: org.goafabric.invoice.process.adapter.authorization.Lock? = null
        try {
            lock = authorizationStep.acquireLock()
            episodeStep.retrieveRecords("Burns")
            val invoice = invoiceStep.create()
            invoiceStep.check(invoice)
            val encryptedInvoice = invoiceStep.encrypt(invoice)
            invoiceStep.send(encryptedInvoice)
            //invoiceStep.store(encryptedInvoice)
        } finally {
            authorizationStep.releaseLock(lock)
            log.info("finished ...")
        }
        doSleep()
        return true
    }

    private fun doSleep() {
        log.info("sleeping")
        java.lang.Thread.sleep(1000)
    }

    @jakarta.annotation.PreDestroy
    private fun shutdown() {
        executor.shutdown()
    }
}
