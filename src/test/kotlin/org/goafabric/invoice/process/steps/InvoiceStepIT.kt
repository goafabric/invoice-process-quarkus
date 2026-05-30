package org.goafabric.invoice.process.steps

import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Test

@QuarkusTest
class InvoiceStepIT {
    @Inject
    private lateinit var invoiceStep: InvoiceStep

    @Test
    fun `episode step is created`() {
        val invoice = invoiceStep.create()
        invoiceStep.check(invoice)
        val encryptedInvoice = invoiceStep.encrypt(invoice)
        invoiceStep.send(encryptedInvoice)

    }
}