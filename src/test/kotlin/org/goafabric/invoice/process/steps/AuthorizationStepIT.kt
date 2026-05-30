package org.goafabric.invoice.process.steps

import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Test

@QuarkusTest
class AuthorizationStepIT {
    @Inject
    private lateinit var authorizationStep: AuthorizationStep

    @Test
    fun `episode step is created`() {
        val lock = authorizationStep.acquireLock()
        authorizationStep.releaseLock(lock)
    }
}