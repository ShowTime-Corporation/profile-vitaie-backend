package showtime_corp.profile_vitaile.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Resumen")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Resumen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer resumen_id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(columnDefinition = "TEXT")
    private String resume_info;

    @Column(columnDefinition = "TEXT")
    private String resume_employability;

    @Column(columnDefinition = "TEXT")
    private String resume_simple;

    @Column(columnDefinition = "TEXT")
    private String resume_recomendation;
}
