package com.avia.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user", nullable = false)
    private Long idUser;

    @Size(max = 30)
    @NotNull
    @Column(nullable = false, length = 30)
    private String email;

    @Size(max = 30)
    @NotNull
    @Column(name = "user_password", nullable = false, length = 30)
    private String userPassword;

    @Column(name = "id_pass")
    private Long idPass;

    @Column
    private Timestamp created;

    @Column
    private Timestamp changed;

    @NotNull
    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("users")
    private Set<Role> roles = Collections.emptySet();

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id_pass")
//    @JsonBackReference
//    private Passenger passengers;
}
