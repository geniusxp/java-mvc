package tech.geniusxp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="TBL_GXP_USER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    @Column(name="id_user")
    private Long id;

    @NotBlank @Size(min = 5, max = 150)
    @Column(name="nm_nome", nullable = false, length = 150)
    private String name;

    @Email @NotBlank @Size(min = 5, max = 320)
    @Column(name="ds_username", unique = true, nullable = false, length = 320)
    private String username;

    @NotBlank @Size(min = 8, max = 150)
    @Column(name="ds_password", nullable = false)
    private String password;

    @NotBlank @Size(min = 11, max = 11)
    @Column(name="nr_cpf", nullable = false, length = 11)
    private String cpf;

    @Past @NotBlank
    @Column(name="dt_birth", nullable = false)
    private LocalDate birthDate;

    @Column(name="url_avatar")
    private String avatarUrl;

    @Column(name="tx_description", length = 500)
    private String description;

    @Column(name="tx_interests", length = 500)
    private String interests;
}
