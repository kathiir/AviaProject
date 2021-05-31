package avia.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_purchase")
public class PurchaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_purchase_id")
    Integer id;

    @Column(name = "user_id")
    String userId;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    FlightModel flightModel;

    @Column(name = "count_passenger")
    Integer countPassengers;

    @Column(name = "flight_cost")
    Double flightCost;
}
