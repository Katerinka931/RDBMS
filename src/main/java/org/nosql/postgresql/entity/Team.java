package org.nosql.postgresql.entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "team", schema = "public")
@Getter
@Setter
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", length = 150, nullable = false, unique = true)
    private String name;

    private int count;

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Sportsman> sportsmen;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "sport_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Sport teamsSport;
}
