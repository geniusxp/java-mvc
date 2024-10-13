package tech.geniusxp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="TBL_GXP_SPEAKER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Speaker {
    @Id
    @GeneratedValue
    @Column(name="id_speaker")
    private Long id;

    @NotBlank @Size(min = 5, max = 150)
    @Column(name="nm_speaker", length = 150)
    private String name;

    @NotBlank @Size(min = 5, max = 512)
    @Column(name="tx_description", length = 512)
    private String description;

    @NotBlank @Size(min = 5, max = 512)
    @Column(name="url_social_media", length = 512)
    private String socialMediaUrl;

    @NotBlank @Size(min = 2, max = 5)
    @Column(name="ds_language", length = 5)
    private String language;

    @NotBlank @Size(min = 5, max = 512)
    @Column(name="url_avatar", length = 512)
    private String avatarUrl;
}
