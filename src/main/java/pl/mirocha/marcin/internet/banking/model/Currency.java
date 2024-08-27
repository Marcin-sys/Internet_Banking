package pl.mirocha.marcin.internet.banking.model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity(name = "tcurrency")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String currencyHeld;
    private String currencyExchange;
    private double currencyRatioExchange;

    public Currency(int id) {
        this.id = id;
    }
}

