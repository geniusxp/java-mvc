package tech.geniusxp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_GXP_LECTURE")
@Data
@NoArgsConstructor
public class Lecture {
    @Id
    @GeneratedValue
    @Column(name="id_lecture")
    private Long id;

    @NotBlank @Size(min = 5, max = 150)
    @Column(name="nm_lecture", length = 150)
    private String name;

    @NotBlank @Size(min = 5, max = 512)
    @Column(name="tx_description", length = 512)
    private String description;

    @FutureOrPresent @NotNull
    @Column(name="dt_lecture")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "id_speaker")
    private Speaker speaker;
}