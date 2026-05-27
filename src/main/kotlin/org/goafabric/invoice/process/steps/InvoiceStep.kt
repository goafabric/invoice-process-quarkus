package org.goafabric.invoice.process.steps

import jakarta.enterprise.context.ApplicationScoped
import org.goafabric.invoice.process.adapter.invoice.Invoice
import org.goafabric.invoice.process.adapter.invoice.InvoiceMockAdapter
import org.goafabric.invoice.process.persistence.ADTCreator
import org.goafabric.invoice.process.persistence.EpisodeDetailsRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

@ApplicationScoped
class InvoiceStep(
    private val episodeDetailsRepository: EpisodeDetailsRepository,
    private val invoiceAdapter: InvoiceMockAdapter,
) {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)


    fun create(): Invoice? {
        val episodeDetails = episodeDetailsRepository.findAll("1")

        log.info("logging adt")
        val content = StringBuilder()
        episodeDetails.forEach({ entry -> content.append(ADTCreator.fromEpisodeDetails(entry)).append("\n") })

        log.info("\n {}", content)
        return Invoice(UUID.randomUUID().toString(), content.toString())
    }

    fun check(invoice: Invoice?) {
        invoiceAdapter.check(invoice)
    }

    fun encrypt(invoice: Invoice): Invoice {
        return invoiceAdapter.encrypt(invoice)
    }

    fun send(invoice: Invoice?) {
        invoiceAdapter.send(invoice)
    }

    /*
    fun store(invoice: Invoice) {
        if (s3Enabled) {
            log.info("storing invoice")
            val objectEntry: ObjectEntry = ObjectEntry(
                "invoice.txt",
                MediaType.TEXT_PLAIN_VALUE,
                invoice.content.length() as Long,
                invoice.content.getBytes(StandardCharsets.UTF_8)
            )
            s3Adapter.save(objectEntry)
        }
    }

     */
}
