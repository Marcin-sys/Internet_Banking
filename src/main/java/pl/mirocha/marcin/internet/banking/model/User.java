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
@Entity(name = "tuser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String login;
    @Column(length = 33)
    private String password;
    private String name;
    private String surname;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>();
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Transfer> transfers = new HashSet<>();

    public User(int id) {
        this.id = id;
    }

    public enum Role{
        USER,
        ADMIN;
    }

}
