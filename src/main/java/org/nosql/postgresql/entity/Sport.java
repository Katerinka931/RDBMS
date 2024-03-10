package org.nosql.postgresql.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "sport", schema = "public")
@Getter
@Setter
public class Sport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", length = 150, nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "sport", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Sportsman> sportsmen;

    @OneToMany(mappedBy = "teamsSport", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Team> teams;
}
