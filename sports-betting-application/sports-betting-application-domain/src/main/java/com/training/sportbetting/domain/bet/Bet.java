package com.training.sportbetting.domain.bet;

import com.training.sportbetting.domain.outcome.Outcome;
import com.training.sportbetting.domain.sportevent.SportEvent;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Set;

/**
 * Domain class which used like a bet and encapsulate event, bet description, bet type and list of outcomes.
 */
@Entity
@NoArgsConstructor
@NamedEntityGraph(name = "betsWithOutcomes",
        attributeNodes = @NamedAttributeNode(value = "outcomes", subgraph = "outcomesWithOutcomesOdd"),
        subgraphs = @NamedSubgraph(name = "outcomesWithOutcomesOdd", attributeNodes = @NamedAttributeNode("outcomeOdds")))
public class Bet {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private SportEvent event;

    @Basic
    private String description;

    @Enumerated(EnumType.STRING)
    private BetType type;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "bet_id")
    private Set<Outcome> outcomes;

    public Bet(SportEvent event, String description, BetType type, Set<Outcome> outcomes) {
        this.event = event;
        this.description = description;
        this.type = type;
        this.outcomes = outcomes;
    }

    public SportEvent getEvent() {
        return event;
    }

    public String getDescription() {
        return description;
    }

    public BetType getType() {
        return type;
    }

    public Set<Outcome> getOutcomes() {
        return outcomes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Bet bet = (Bet) o;

        return new EqualsBuilder()
                .append(event, bet.event)
                .append(description, bet.description)
                .append(type, bet.type)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(event)
                .append(description)
                .append(type)
                .toHashCode();
    }

    public int getId() {
        return id;
    }
}
