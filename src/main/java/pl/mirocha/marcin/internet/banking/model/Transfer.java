package pl.mirocha.marcin.internet.banking.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity(name = "ttransfer")
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime dateTime;
    @Enumerated(EnumType.STRING)
    private Status status;
    private double amountOfMoney;  //TODO change int to double in database
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY)
    private User user;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY)
    private Account accountSender;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY)
    private Account accountReceiver;

    public Transfer(int id) {
        this.id = id;
    }
    public Transfer(double amountOfMoney, User user, Account accountSender,
                    Account accountReceiver) {
        this.status = Status.NEW;
        this.amountOfMoney = amountOfMoney;
        this.user = user;
        this.accountSender = accountSender;
        this.accountReceiver = accountReceiver;

    }
    public enum Status{
        NEW,
        DONE
    }
}
