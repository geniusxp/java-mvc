package tech.geniusxp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.geniusxp.models.TicketType;

import java.util.List;

public interface TicketTypeRepository extends JpaRepository<TicketType, Long> {
}