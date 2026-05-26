package org.goafabric.invoice.process.adapter.invoice

import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class InvoiceMockAdapter {
    fun check(invoice: Invoice?) {
        // just empty
    }

    fun encrypt(invoice: Invoice): Invoice {
        val encryptedContent = java.util.Base64.getEncoder().encodeToString(
            invoice.content.toByteArray(java.nio.charset.StandardCharsets.UTF_8)
        )
        return Invoice(invoice.id, encryptedContent)
    }

    fun send(invoice: Invoice?) {
        // just empty
    }

    fun store(invoice: Invoice?): Invoice? {
        return invoice
    }
}
