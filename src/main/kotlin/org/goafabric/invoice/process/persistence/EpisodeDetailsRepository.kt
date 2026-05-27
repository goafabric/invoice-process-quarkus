package org.goafabric.invoice.process.persistence

import jakarta.enterprise.context.ApplicationScoped
import org.goafabric.invoice.process.persistence.entity.EpisodeDetails

@ApplicationScoped
class EpisodeDetailsRepository {
    private val entries: MutableList<EpisodeDetails> = ArrayList<EpisodeDetails>()

    fun findAll(episodeId: String): MutableList<EpisodeDetails> {
        return entries.stream().filter { entry: EpisodeDetails -> entry.episodeId == episodeId }.toList()
    }

    fun save(entry: EpisodeDetails) {
        if (entries.stream().anyMatch { e: EpisodeDetails -> e.referenceId == entry.referenceId }) {
            delete(entry)
        } //update

        entries.add(entry)
    }

    fun delete(entry: EpisodeDetails) {
        entries.remove(entry)
    }
}
