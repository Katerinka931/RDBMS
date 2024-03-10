package org.nosql.postgresql.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "contacts", schema = "public")
@Getter
@Setter
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String phone;

    private String email;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "sportsman_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Sportsman sportsman;
}
