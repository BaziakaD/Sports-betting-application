package com.training.sportbetting.domain.outcome;

import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import static java.util.Comparator.reverseOrder;

@Entity
@NoArgsConstructor
@NamedEntityGraph(name = "outcomeWithOdds", attributeNodes = @NamedAttributeNode("outcomeOdds"))
public class Outcome {

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String value;

    @OneToMany(mappedBy = "outcome", cascade = CascadeType.ALL)
    private Set<OutcomeOdd> outcomeOdds;

    @Column(name = "bet_id")
    private Integer betId;

    @Column(name = "result_id")
    private Integer resultId;

    public Outcome(String value) {
        this.value = value;
        this.outcomeOdds = new HashSet<>();
    }

    public Integer getBetId() {
        return betId;
    }

    public void setBetId(Integer betId) {
        this.betId = betId;
    }

    public Integer getResultId() {
        return resultId;
    }

    public void setResultId(Integer resultId) {
        this.resultId = resultId;
    }

    public String getValue() {
        return value;
    }

    public Set<OutcomeOdd> getOutcomeOdds() {
        return outcomeOdds;
    }

    public OutcomeOdd getValidOutcomeOdd() {
        return outcomeOdds
                .stream()
                .sorted(Comparator.comparing(OutcomeOdd::getValidTo, reverseOrder()))
                .limit(1)
                .findFirst().orElseThrow(() -> new IllegalStateException("Outcome odd list is empty."));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Outcome outcome = (Outcome) o;

        return new EqualsBuilder()
                .append(value, outcome.value)
                .append(betId, outcome.betId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(value)
                .append(betId)
                .toHashCode();
    }

    public int getId() {
        return id;
    }
}
