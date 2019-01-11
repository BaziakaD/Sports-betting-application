package com.training.sportbetting.domain.outcome;

import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class OutcomeOdd {

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private Double value;

    @Column(nullable = false)
    private LocalDateTime validFrom;

    @Column(nullable = false)
    private LocalDateTime validTo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "outcome_id")
    private Outcome outcome;

    public OutcomeOdd(Double value, LocalDateTime validFrom, LocalDateTime validTo, Outcome outcome) {
        this.value = value;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.outcome = outcome;
    }

    public Double getValue() {
        return value;
    }

    public LocalDateTime getValidFrom() {
        return validFrom;
    }

    public LocalDateTime getValidTo() {
        return validTo;
    }

    public Outcome getOutcome() {
        return outcome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        OutcomeOdd that = (OutcomeOdd) o;

        return new EqualsBuilder()
                .append(value, that.value)
                .append(validFrom, that.validFrom)
                .append(validTo, that.validTo)
                .append(outcome.getValue(), that.outcome.getValue())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(value)
                .append(validFrom)
                .append(validTo)
                .append(outcome.getValue())
                .toHashCode();
    }
}
