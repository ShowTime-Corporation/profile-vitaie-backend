package showtime_corp.profile_vitaile.entity;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import showtime_corp.profile_vitaile.entity.resumen.ResumeEmployability;
import showtime_corp.profile_vitaile.entity.resumen.ResumeInfo;
import showtime_corp.profile_vitaile.entity.resumen.ResumeRecommendation;
import showtime_corp.profile_vitaile.entity.resumen.ResumeSimple;

@Entity
@Table(name = "resumen")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
/**
 * Stores AI-generated resume representations.
 */
public class Resumen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resumen_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Type(JsonType.class)
    @Column(name = "resume_info", nullable = false, columnDefinition = "json")
    private ResumeInfo resumeInfo;

    @Type(JsonType.class)
    @Column(name = "resume_employability", nullable = false, columnDefinition = "json")
    private ResumeEmployability employability;

    @Type(JsonType.class)
    @Column(name = "resume_simple", nullable = false, columnDefinition = "json")
    private ResumeSimple simple;

    @Type(JsonType.class)
    @Column(name = "resume_recomendation", nullable = false, columnDefinition = "json")
    private ResumeRecommendation recommendation;
}
