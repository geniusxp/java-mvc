package tech.geniusxp.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_GXP_TICKET")
@Data
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue
    @Column(name="id_ticket")
    private Long id;

    @Column(name="dt_of_use")
    private LocalDateTime dateOfUse = null;

    @Column(name="dt_issued")
    private LocalDateTime issuedDate = LocalDateTime.now();

    @Column(name="nr_ticket")
    private String ticketNumber = Instant.now().getEpochSecond() + "@" + Math.random();

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_ticket_type")
    private TicketType ticketType;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "ticket", orphanRemoval = true)
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "id_coupon")
    private Coupon coupon = null;
}