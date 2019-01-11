package com.training.sportbetting.utils;

import com.training.sportbetting.domain.bet.Bet;
import com.training.sportbetting.domain.bet.BetType;
import com.training.sportbetting.domain.outcome.Outcome;
import com.training.sportbetting.domain.outcome.OutcomeOdd;
import com.training.sportbetting.domain.sportevent.*;
import com.training.sportbetting.domain.user.Player;
import com.training.sportbetting.repository.jpa.SportEventRepository;
import com.training.sportbetting.repository.jpa.UserRepository;
import com.training.sportbetting.service.SportEventService;
import com.training.sportbetting.service.WagerService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class DataGenerator {

    private DataGenerator() {
    }

    public static void generateData(SportEventRepository sportEventRepository,
                                    UserRepository userRepository,
                                    WagerService wagerService, SportEventService sportEventService) {

        FootballSportEvent footballSportEvent = (FootballSportEvent) createEventWithData(SportEventType.FOOTBALL);
        sportEventRepository.save(footballSportEvent);

        TennisSportEvent tennisSportEvent = (TennisSportEvent) createEventWithData(SportEventType.TENNIS);
        sportEventRepository.save(tennisSportEvent);

        Player player = generatePlayer();
        userRepository.save(player);

        createPlayerActivity(wagerService, sportEventService, player, footballSportEvent, tennisSportEvent);
    }

    private static void createPlayerActivity(WagerService wagerService, SportEventService sportEventService, Player player,
                                             SportEvent... events) {
        var bet = events[0].getBets().stream().findFirst().get();
        wagerService.createWager(player, 10_00d,
                bet.getOutcomes().stream().findFirst().get().getValidOutcomeOdd());

        Result result = sportEventService.generateResultForEvent(events[0]);
        events[0].setResult(result);
        wagerService.applyResultOfEvent(result);

        Set<Outcome> outcomes = events[1].getBets().stream().findFirst().get().getOutcomes();
        wagerService.createWager(player, 10_00d, outcomes.stream().findFirst().get().getValidOutcomeOdd());
    }


    private static SportEvent createEventWithData(SportEventType eventType) {

        SportEvent event;

        if (eventType == SportEventType.FOOTBALL) {
            event = createFootballEvent();
        } else {
            event = createTennisEvent();
        }

        generateDataForSportEvent(event);
        return event;
    }

    private static TennisSportEvent createTennisEvent() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusHours(1);
        return new TennisSportEvent("Rafael Nadal vs. Alexander Zverev, Indian Wells 4th Round", start, end);
    }

    private static FootballSportEvent createFootballEvent() {

        LocalDateTime time1 = LocalDateTime.now();
        LocalDateTime time2 = time1.plusMinutes(105);

        return new FootballSportEvent("Colombia - Argentina", time1, time2);
    }

    private static void generateDataForSportEvent(SportEvent sportEvent) {
        List<Bet> bets = null;
        if (sportEvent instanceof FootballSportEvent) {
            bets = generateBetsForFootball(sportEvent, sportEvent.getEndDate());
        } else if (sportEvent instanceof TennisSportEvent) {
            bets = generateBetsForTennis(sportEvent, sportEvent.getEndDate());
        }
        sportEvent.getBets().addAll(bets);
    }

    private static List<Bet> generateBetsForTennis(SportEvent sportEvent, LocalDateTime endDate) {
        Set<Outcome> outcomes = new HashSet<>();

        Outcome rafaelNadal = new Outcome("Rafael Nadal");
        OutcomeOdd rafaelOdd = new OutcomeOdd(1.01d, LocalDateTime.now(), endDate, rafaelNadal);
        rafaelNadal.getOutcomeOdds().add(rafaelOdd);

        Outcome alexanderZverev = new Outcome("Alexander Zverev");
        OutcomeOdd zverevOdd = new OutcomeOdd(1.7d, LocalDateTime.now(), endDate, alexanderZverev);
        alexanderZverev.getOutcomeOdds().add(zverevOdd);

        outcomes.add(rafaelNadal);
        outcomes.add(alexanderZverev);
        Bet bet = new Bet(sportEvent, "Rafael Nadal vs. Alexander Zverev", BetType.WINNER, outcomes);
        outcomes.forEach(outcome -> outcome.setBetId(bet.getId()));

        return singletonList(bet);
    }

    private static List<Bet> generateBetsForFootball(SportEvent sportEvent, LocalDateTime time2) {
        Set<Outcome> winnerOutcomes = createWinnerOutcomesForFootball(time2);
        Bet winnerBet = new Bet(sportEvent, "Match between Colombia and Argentina", BetType.WINNER, winnerOutcomes);
        winnerOutcomes.forEach(outcome -> outcome.setBetId(winnerBet.getId()));

        Set<Outcome> goalOutcomes = createGoalOutcomesForFootball(time2);
        Bet goalBet = new Bet(sportEvent, "Match between Colombia and Argentina", BetType.GOAL, goalOutcomes);
        goalOutcomes.forEach(outcome -> outcome.setBetId(goalBet.getId()));

        return asList(winnerBet, goalBet);
    }


    private static Set<Outcome> createGoalOutcomesForFootball(LocalDateTime end) {
        Set<Outcome> outcomes = new HashSet<>();

        Outcome zero = new Outcome("0");
        OutcomeOdd zeroOdd = new OutcomeOdd(3d, LocalDateTime.now(), end, zero);
        zero.getOutcomeOdds().add(zeroOdd);

        Outcome one = new Outcome("1");
        OutcomeOdd oneOdd = new OutcomeOdd(1.5d, LocalDateTime.now(), end, one);
        one.getOutcomeOdds().add(oneOdd);

        Outcome two = new Outcome("2");
        OutcomeOdd twoOdd = new OutcomeOdd(1.1d, LocalDateTime.now(), end, two);
        two.getOutcomeOdds().add(twoOdd);

        outcomes.add(zero);
        outcomes.add(one);
        outcomes.add(two);

        return outcomes;
    }

    private static Set<Outcome> createWinnerOutcomesForFootball(LocalDateTime end) {
        Set<Outcome> outcomes = new HashSet<>();

        Outcome colombia = new Outcome("Colombia");
        OutcomeOdd colombiaOutcomeOdd1 = new OutcomeOdd(2d, LocalDateTime.of(2018, 9, 1, 0, 0), LocalDateTime.of(2018, 9, 12, 0, 0), colombia);
        OutcomeOdd colombiaOutcomeOdd2 = new OutcomeOdd(1.5d, LocalDateTime.now(), end, colombia);
        colombia.getOutcomeOdds().add(colombiaOutcomeOdd1);
        colombia.getOutcomeOdds().add(colombiaOutcomeOdd2);

        Outcome argentina = new Outcome("Argentina");
        OutcomeOdd argentinaOutcomeOdd = new OutcomeOdd(1.6d, LocalDateTime.now(), end, argentina);
        argentina.getOutcomeOdds().add(argentinaOutcomeOdd);

        Outcome draw = new Outcome("Draw");
        OutcomeOdd drawOutcomeOdd = new OutcomeOdd(3d, LocalDateTime.now(), end, draw);
        draw.getOutcomeOdds().add(drawOutcomeOdd);

        outcomes.add(colombia);
        outcomes.add(argentina);
        outcomes.add(draw);
        return outcomes;
    }

    private static Player generatePlayer() {
        return new Player.PlayerBuilder()
                .email("email@mail.com")
                .password("$2a$04$1DX6qRacSwxzuURDbCmxQ.ak7XsaWmvufDas4dRvhBpK6vEkuCbqK")
                .name("Dmytro")
                .balance(10000d)
                .currency(Currency.getInstance("USD"))
                .dateOfBirth(LocalDate.of(1998, 1, 5))
                .build();
    }
}
