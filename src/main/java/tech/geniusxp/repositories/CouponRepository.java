package tech.geniusxp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.geniusxp.models.Coupon;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    List<Coupon> findByNameContainingIgnoreCase(String searchTerm);
}