package tech.geniusxp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.geniusxp.enums.PaymentMethod;
import tech.geniusxp.enums.PaymentStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_GXP_PAYMENT")
@Data
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue
    @Column(name="id_payment")
    private Long id;

    @NotNull
    @Column(name="ds_payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @NotNull
    @Column(name="st_payment")
    @Enumerated(EnumType.STRING)
    private PaymentStatus status = PaymentStatus.PENDING;

    @DecimalMin("0.01")
    @Column(name="vl_total_price")
    private double totalPrice;

    @Column(name="dt_due")
    private LocalDateTime dueDate = LocalDateTime.now().plusDays(3);

    @Column(name="dt_payment")
    private LocalDateTime paymentDate = null;

    @OneToOne
    @JoinColumn(name = "id_ticket", nullable = false)
    private Ticket ticket;
}