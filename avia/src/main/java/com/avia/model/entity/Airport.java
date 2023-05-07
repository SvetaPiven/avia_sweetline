package com.avia.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "airports")
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_airport", nullable = false)
    private Long idAirport;

    @Size(max = 50)
    @NotNull
    @Column(name = "name_airport", nullable = false, length = 50)
    private String nameAirport;

    @Size(max = 50)
    @NotNull
    @Column(nullable = false)
    private String city;

    @JsonIgnore
    @NotNull
    @Column(nullable = false)
    private Float longitude;

    @JsonIgnore
    @NotNull
    @Column(nullable = false)
    private Float latitude;

    @JsonIgnore
    @NotNull
    @Column(nullable = false)
    private String timezone;

    @JsonIgnore
    @Column
    private Timestamp created;

    @JsonIgnore
    @Column
    private Timestamp changed;

    @JsonIgnore
    @NotNull
    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @Size(max = 30)
    @NotNull
    @Column(nullable = false, length = 30)
    private String country;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "idArrivalAirport", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Flight> flightsArrival = new LinkedHashSet<>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "idDepartureAirport", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Flight> flightsDeparture = new LinkedHashSet<>();
}
