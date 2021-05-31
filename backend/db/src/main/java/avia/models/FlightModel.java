package avia.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "flights")
public class FlightModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "flight_id")
    Integer id;
   // @Transient
    @ManyToOne
    @JoinColumn(columnDefinition = "city_id" )
    CityModel originPlace;
    //@Transient
    @ManyToOne
    @JoinColumn(columnDefinition = "city_id" )
    CityModel destinationPlace;
    @JoinColumn(columnDefinition = "outbound_date")
    Date outboundDate;
    @JoinColumn(columnDefinition = "inbound_date")
    Date inboundDate;
    @JoinColumn(columnDefinition = "cost")
    Double cost;


}
