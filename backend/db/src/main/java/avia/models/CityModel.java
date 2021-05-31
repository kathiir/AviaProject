package avia.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "city",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"place_id", "place_name", "city_code", "country_name"}
                )
        }
)
public class CityModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "city_id")
    Integer id;

    @Column(name = "place_id")
    String placeId;

    @Column(name = "place_name")
    String placeName;

    @Column(name = "city_code")
    String cityId;

    @Column(name = "country_name")
    String countryName;

}
