package tech.geniusxp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "TBL_GXP_COUPON")
@Data
@NoArgsConstructor
public class Coupon {
    @Id
    @GeneratedValue
    @Column(name="id_coupon")
    private Long id;

    @NotBlank @Size(min = 5, max = 255)
    @Column(name="nm_coupon")
    private String name;

    @NotBlank @Size(min = 5, max = 512)
    @Column(name="tx_description", length = 512)
    private String description;

    @NotBlank @Size(min = 5, max = 20)
    @Column(name="cod_coupon", length = 20)
    private String code;

    @DecimalMin(value = "0.0") @DecimalMax("100.0")
    @Column(name="vl_discount")
    private double discountValue;

    @FutureOrPresent
    @Column(name="dt_expiration")
    private LocalDate expirationDate;

    @ManyToOne
    @JoinColumn(name = "id_event")
    private Event event;
}