package org.nosql.postgresql.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "achievements", schema = "public")
@Getter
@Setter
public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Temporal(TemporalType.DATE)
    private Date recvDate;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "sportsman_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Sportsman achSportsman;
}
