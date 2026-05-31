package org.goafabric.invoice.process.steps

import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Test

@QuarkusTest
class EpisodeStepNRIT {
    @Inject
    private lateinit var episodeStep: EpisodeStep

    @Test
    fun `episode step is created`() {
        episodeStep.retrieveRecords("Burns")
    }
}