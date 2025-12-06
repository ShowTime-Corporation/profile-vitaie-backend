package showtime_corp.profile_vitaile.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "RoadMap")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoadMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roadmap_id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(columnDefinition = "TEXT")
    private String road_analisis;

    @Column(columnDefinition = "TEXT")
    private String road_proposal;

    @Column(columnDefinition = "TEXT")
    private String road_ideas;

    @Column(columnDefinition = "TEXT")
    private String road_keep;
}
