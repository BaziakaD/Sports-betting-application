package com.training.sportbetting.domain.user;

import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;

@Entity
@NoArgsConstructor
public class Player extends User {

    public static final List<Currency> CURRENCIES =
            asList(Currency.getInstance("EUR"), Currency.getInstance("USD"), Currency.getInstance("HUF"));

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer accountNumber;

    @Column(nullable = false)
    private Double balance;

    @Column(nullable = false)
    private Currency currency;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    public static final double MAX_BALANCE = 1_000_000d;
    public static final double MIN_ACCURACY = 0.01;

    public Player(String email, String password, String name, Double balance, Currency currency, LocalDate dateOfBirth) {
        super(email, password);
        this.name = name;
        this.accountNumber = new Random(System.nanoTime()).nextInt(100_000);
        this.balance = balance;
        this.currency = currency;
        this.dateOfBirth = dateOfBirth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(name, player.name)
                .append(accountNumber, player.accountNumber)
                .append(dateOfBirth, player.dateOfBirth)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(name)
                .append(accountNumber)
                .append(dateOfBirth)
                .toHashCode();
    }

    public static class PlayerBuilder {

        private String email;
        private String password;
        private String name;
        private Double balance;
        private Currency currency;
        private LocalDate dateOfBirth;

        public PlayerBuilder() {
            currency = CURRENCIES.get(0);
            balance = 0d;
        }

        public PlayerBuilder email(String email) {
            this.email = email;
            return this;
        }

        public PlayerBuilder password(String password) {
            this.password = password;
            return this;
        }

        public PlayerBuilder name(String name) {
            this.name = name;
            return this;
        }

        public PlayerBuilder balance(Double balance) {
            if (balance == null) {
                throw new IllegalArgumentException("Balance can not be null");
            }
            this.balance = balance;
            return this;
        }

        public PlayerBuilder currency(Currency currency) {

            if (!CURRENCIES.contains(currency)) {
                throw new IllegalArgumentException("Currency %s is not supported. Supported is - [USD, EUR, HUF]");
            }

            this.currency = currency;
            return this;
        }

        public PlayerBuilder dateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Player build() {
            if (email == null || password == null || name == null || dateOfBirth == null) {
                throw new IllegalStateException("Email, password, name and date of birth is required for player building.");
            }
            return new Player(email, password, name, balance, currency, dateOfBirth);
        }
    }
}
