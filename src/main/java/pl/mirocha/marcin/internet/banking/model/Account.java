package pl.mirocha.marcin.internet.banking.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity(name = "taccount")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String accountNumber;
    private double accountBalance;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.EAGER)
    private User user;
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Transfer> transfers = new HashSet<>();

    public Account(int id) {
        this.id = id;
    }
}
