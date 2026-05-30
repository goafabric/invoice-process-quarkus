package org.goafabric.invoice.process.steps

import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.goafabric.invoice.process.adapter.ObjectStorageAdapter
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
    @param:ConfigProperty(name = "quarkus.azure.storage.blob.connection-string")
    private val azureBlobEnabled: Optional<String>,
    private val objectStorageAdapter: ObjectStorageAdapter

) {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)


    fun create(): Invoice {
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

    fun store(invoice: Invoice) {
        if (azureBlobEnabled.isPresent) {
            log.info("storing invoice")
            val objectEntry: ObjectStorageAdapter.ObjectEntry =
                ObjectStorageAdapter.ObjectEntry(
                    key = "invoice.txt",
                    sizeBytes = invoice.content.length.toLong(),
                    contentType = MediaType.TEXT_PLAIN,
                    data = invoice.content.byteInputStream(Charsets.UTF_8)
            )
            objectStorageAdapter.put(objectEntry)
        }
    }

}
