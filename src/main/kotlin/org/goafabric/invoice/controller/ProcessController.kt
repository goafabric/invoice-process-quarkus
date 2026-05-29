package org.goafabric.invoice.controller

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.goafabric.invoice.process.InvoiceProcess


@Path("/process")
@Produces(MediaType.APPLICATION_JSON)
class ProcessController(private val invoiceProcess: InvoiceProcess) {

    @GET
    @Path("/start")
    fun start(): kotlin.String {
        invoiceProcess.run()
        return "launched"
    }

    @GET
    @Path("/process")
    fun loop(): kotlin.String {
        for (i in 0..9) {
            invoiceProcess.run().get()
        }
        return "launched"
    }
}
