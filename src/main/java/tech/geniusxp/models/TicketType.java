package tech.geniusxp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table (name = "TBL_GXP_TICKET_TYPE")
@Data
@NoArgsConstructor
public class TicketType {
    @Id
    @GeneratedValue
    @Column(name="id_ticket_type")
    private Long id;

    @NotNull @DecimalMin("0.01")
    @Column(name="vl_price")
    private double priceValue;

    @NotBlank @Size(min = 5, max = 150)
    @Column(name="ds_category", length = 150)
    private String category;

    @NotBlank @Size(min = 5, max = 512)
    @Column(name="tx_description", length = 512)
    private String description;

    @NotNull @Min(1)
    @Column(name="nr_quantity")
    private int availableQuantity;

    @NotNull @Min(0)
    @Column(name="nr_sold")
    private int soldQuantity = 0;

    @FutureOrPresent @NotNull
    @Column(name="dt_finishes_at")
    private LocalDateTime finishesAt;

    @ManyToOne
    @JoinColumn(name="id_event")
    private Event event;
}