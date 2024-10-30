package tech.geniusxp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "TBL_GXP_EVENT")
@Data
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue
    @Column(name="id_event")
    private Long id;

    @NotBlank @Size(min = 5, max = 255)
    @Column(name="nm_event")
    private String name;

    @NotBlank @Size(min = 5, max = 512)
    @Column(name="tx_description", length = 512)
    private String description;

    @NotBlank @Size(min = 5, max = 100)
    @Column(name="ds_event_type", length = 100)
    private String eventType;

    @NotBlank @Size(min = 10, max = 512)
    @Column(name="url_image", length = 512)
    private String imageUrl;

    @OneToMany(mappedBy = "event")
    private List<Coupon> coupons;

    @OneToMany(mappedBy = "event")
    private List<TicketType> ticketTypes;

    @OneToMany(mappedBy = "event")
    private List<Lecture> lectures;
}