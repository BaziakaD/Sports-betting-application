package com.training.sportbetting.domain.sportevent;

import com.training.sportbetting.domain.bet.Bet;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "eventWithBets",
                attributeNodes = {
                        @NamedAttributeNode("bets")
                }
        ),
        @NamedEntityGraph(
                name = "eventWithBetsWithResult",
                attributeNodes = {
                        @NamedAttributeNode("bets"),
                        @NamedAttributeNode(value = "result", subgraph = "resultWithOutcomes")
                },
                subgraphs = {
                        @NamedSubgraph(
                                name = "resultWithOutcomes",
                                attributeNodes = {
                                        @NamedAttributeNode(value = "outcomes")
                                }
                        )
                }
        ),
        @NamedEntityGraph(
                name = "eventAll",
                attributeNodes = {
                        @NamedAttributeNode(value = "bets", subgraph = "betsWithOutcomes"),
                        @NamedAttributeNode(value = "result", subgraph = "resultWithOutcomes")
                },
                subgraphs = {
                        @NamedSubgraph(
                                name = "betsWithOutcomes",
                                attributeNodes = {
                                        @NamedAttributeNode(value = "outcomes", subgraph = "outcomesWithOdds")
                                }
                        ),
                        @NamedSubgraph(
                                name = "outcomesWithOdds",
                                attributeNodes = {
                                        @NamedAttributeNode(value = "outcomeOdds")
                                }
                        ),
                        @NamedSubgraph(
                                name = "resultWithOutcomes",
                                attributeNodes = {
                                        @NamedAttributeNode(value = "outcomes")
                                }
                        )
                }
        )
})
@NoArgsConstructor
public abstract class SportEvent {

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private Set<Bet> bets;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "result_id")
    private Result result;

    @Enumerated(EnumType.STRING)
    @Column(length = 8)
    protected SportEventType type;

    public SportEvent(String title, LocalDateTime startDate, LocalDateTime endDate) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;

        this.bets = new HashSet<>();
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public Set<Bet> getBets() {
        return bets;
    }

    public Result getResult() {
        return result;
    }

    public SportEventType getType() {
        return type;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SportEvent event = (SportEvent) o;

        return new EqualsBuilder()
                .append(title, event.title)
                .append(startDate, event.startDate)
                .append(endDate, event.endDate)
                .append(type, event.type)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(title)
                .append(startDate)
                .append(endDate)
                .append(type)
                .toHashCode();
    }
}
