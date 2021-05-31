package avia.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recent_city", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "city_id"})
)
public class RecentCityModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    Integer id;

    @Column(name = "user_id")
    String userId;

    @ManyToOne
    @JoinColumn(name = "city_id")
    CityModel city;

}
