package com.training.sportbetting.domain.wager;

import com.training.sportbetting.domain.outcome.OutcomeOdd;
import com.training.sportbetting.domain.user.Player;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Currency;

@Entity
@NoArgsConstructor
public class Wager {

    @Id
    @GeneratedValue
    private int id;

    @OneToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @OneToOne
    @JoinColumn(name = "outcome_odd_id")
    private OutcomeOdd outcomeOdd;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private Currency currency;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private boolean isProcessed;

    @Column(nullable = false)
    private boolean isWin;

    public Wager(Player player, OutcomeOdd outcomeOdd, Double amount, Currency currency, LocalDateTime timestamp) {
        this.player = player;
        this.outcomeOdd = outcomeOdd;
        this.amount = amount;
        this.currency = currency;
        this.timestamp = timestamp;
        this.isProcessed = false;
        this.isWin = false;
    }

    public int getId() {
        return id;
    }

    public Player getPlayer() {
        return player;
    }

    public OutcomeOdd getOutcomeOdd() {
        return outcomeOdd;
    }

    public Double getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public boolean isProcessed() {
        return isProcessed;
    }

    public boolean isWin() {
        return isWin;
    }

    public void setProcessed(boolean processed) {
        isProcessed = processed;
    }

    public void setWin(boolean win) {
        isWin = win;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Wager wager = (Wager) o;

        return new EqualsBuilder()
                .append(isWin, wager.isWin)
                .append(player, wager.player)
                .append(outcomeOdd, wager.outcomeOdd)
                .append(amount, wager.amount)
                .append(currency, wager.currency)
                .append(timestamp, wager.timestamp)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(player)
                .append(outcomeOdd)
                .append(amount)
                .append(currency)
                .append(timestamp)
                .append(isWin)
                .toHashCode();
    }
}
