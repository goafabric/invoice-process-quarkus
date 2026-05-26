package org.goafabric.invoice.process.steps

import jakarta.enterprise.context.ApplicationScoped
import org.goafabric.invoice.persistence.ADTCreator
import org.goafabric.invoice.persistence.EpisodeDetailsRepository
import org.goafabric.invoice.process.adapter.invoice.Invoice
import org.goafabric.invoice.process.adapter.invoice.InvoiceMockAdapter
import org.goafabric.invoice.process.adapter.s3.S3Adapter
import org.goafabric.invoice.process.adapter.s3.dto.ObjectEntry
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import java.nio.charset.StandardCharsets
import java.util.*

@ApplicationScoped
class InvoiceStep(
    episodeDetailsRepository: EpisodeDetailsRepository,
    invoiceAdapter: InvoiceMockAdapter,
    s3Adapter: S3Adapter
) {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    private val episodeDetailsRepository: EpisodeDetailsRepository
    private val invoiceAdapter: InvoiceMockAdapter
    private val s3Adapter: S3Adapter

    @Value("\${spring.cloud.aws.s3.enabled:}")
    private val s3Enabled: Boolean? = null

    init {
        this.episodeDetailsRepository = episodeDetailsRepository
        this.invoiceAdapter = invoiceAdapter
        this.s3Adapter = s3Adapter
    }

    fun create(): Invoice? {
        val episodeDetails: Unit /* TODO: class org.jetbrains.kotlin.nj2k.types.JKJavaNullPrimitiveType */? =
            episodeDetailsRepository.findAll("1")

        log.info("logging adt")
        val content = StringBuilder()
        episodeDetails.forEach({ entry -> content.append(ADTCreator.fromEpisodeDetails(entry)).append("\n") })

        log.info("\n {}", content)
        return Invoice(UUID.randomUUID().toString(), content.toString())
    }

    fun check(invoice: Invoice?) {
        invoiceAdapter.check(invoice)
    }

    fun encrypt(invoice: Invoice?): Invoice {
        return invoiceAdapter.encrypt(invoice)
    }

    fun send(invoice: Invoice?) {
        invoiceAdapter.send(invoice)
    }

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
}
