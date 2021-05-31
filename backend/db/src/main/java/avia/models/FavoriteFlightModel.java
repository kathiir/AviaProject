package avia.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "favorite_flights",
        uniqueConstraints = @UniqueConstraint(columnNames = {"flight_id", "user_id"})
)
public class FavoriteFlightModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "favorite_flights_id")
    Integer id;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    FlightModel flightModel;

    @Column(name = "user_id")
    String userId;
}
