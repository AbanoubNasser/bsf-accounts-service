package com.bsf.services.accountsservice.model;

import com.bsf.services.accountsservice.base.entity.Auditable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import java.util.Set;

@Entity
@Table(name = "accounts",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "number")
        })
@Getter
@Setter
public class Account extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String number;

    private String ownerName;

    private String bankName;

    @PositiveOrZero
    private double balance;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Transaction> transactions;
}
