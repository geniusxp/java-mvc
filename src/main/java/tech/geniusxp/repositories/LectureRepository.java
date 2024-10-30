package tech.geniusxp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.geniusxp.models.Lecture;

import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    List<Lecture> findByNameContainingIgnoreCase(String searchTerm);
}