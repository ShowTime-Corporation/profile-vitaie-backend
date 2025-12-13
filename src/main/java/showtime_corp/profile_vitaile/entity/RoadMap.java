package showtime_corp.profile_vitaile.entity;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import showtime_corp.profile_vitaile.entity.roadmap.RoadAnalysis;
import showtime_corp.profile_vitaile.entity.roadmap.RoadIdeas;
import showtime_corp.profile_vitaile.entity.roadmap.RoadKeep;
import showtime_corp.profile_vitaile.entity.roadmap.RoadProposal;

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

    @Type(JsonType.class)
    @Column(name = "road_analisis", nullable = false, columnDefinition = "json")
    private RoadAnalysis analysis;

    @Type(JsonType.class)
    @Column(name = "road_proposal", nullable = false, columnDefinition = "json")
    private RoadProposal proposal;

    @Type(JsonType.class)
    @Column(name = "road_ideas", nullable = false, columnDefinition = "json")
    private RoadIdeas ideas;

    @Type(JsonType.class)
    @Column(name = "road_keep", nullable = false, columnDefinition = "json")
    private RoadKeep keep;
}
