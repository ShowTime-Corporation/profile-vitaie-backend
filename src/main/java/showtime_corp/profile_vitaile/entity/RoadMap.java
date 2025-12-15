package showtime_corp.profile_vitaile.entity;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Table(name = "roadmap")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
/**
 * Represents an AI-generated professional roadmap.
 */

public class RoadMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roadmap_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "road_analisis", nullable = false, columnDefinition = "TEXT")
    private String analysis;

    @Column(name = "road_proposal", nullable = false, columnDefinition = "TEXT")
    private String proposal;

    @Column(name = "road_ideas", nullable = false, columnDefinition = "TEXT")
    private String ideas;

    @Column(name = "road_keep", nullable = false, columnDefinition = "TEXT")
    private String keep;
}
