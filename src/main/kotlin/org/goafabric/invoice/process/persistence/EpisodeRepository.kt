package org.goafabric.invoice.process.persistence

import jakarta.enterprise.context.ApplicationScoped
import org.goafabric.invoice.process.persistence.entity.Episode

@ApplicationScoped
class EpisodeRepository {
    private val episodes: MutableList<Episode> = ArrayList<Episode>()

    fun findAll(): MutableList<Episode> {
        return episodes
    }

    fun findByPatientId(patientId: String): MutableList<Episode> {
        return episodes.stream().filter { entry: Episode -> entry.patientId.equals(patientId) }.toList()
    }

    fun save(episode: Episode) {
        episodes.add(episode)
    }
}
