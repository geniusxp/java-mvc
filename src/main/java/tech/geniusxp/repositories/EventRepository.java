package tech.geniusxp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.geniusxp.models.Event;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByNameContainingIgnoreCase(String searchTerm);
}