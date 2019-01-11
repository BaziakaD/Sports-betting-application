package com.training.sportbetting.domain.sportevent;

import com.training.sportbetting.domain.outcome.Outcome;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Collections.emptySet;

@Entity
public class Result {

    @Id
    @GeneratedValue
    private int id;

    @OneToMany
    @JoinColumn(name = "result_id")
    private Set<Outcome> outcomes;

    public Result(List<Outcome> resultOutcomes) {
        this.outcomes = new HashSet<>(resultOutcomes);
    }

    public Result() {
        this.outcomes = emptySet();
    }

    public Set<Outcome> getOutcomes() {
        return outcomes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Result result = (Result) o;

        return new EqualsBuilder()
                .append(outcomes, result.outcomes)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(outcomes)
                .toHashCode();
    }
}

