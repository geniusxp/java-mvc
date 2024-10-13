package tech.geniusxp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.geniusxp.models.Speaker;

import java.util.List;

public interface SpeakerRepository extends JpaRepository<Speaker, Long> {
    List<Speaker> findByNameContainingIgnoreCase(String searchTerm);
}