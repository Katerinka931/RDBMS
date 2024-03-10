package org.nosql.postgresql.dto;

import org.nosql.postgresql.entity.Sport;
import org.nosql.postgresql.entity.Sportsman;
import org.nosql.postgresql.entity.Team;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SportPojo {
    private long id;
    private String name;
    private List<SportsmanPojo> sportsmen;
    private List<TeamPojo> teams;

    public static SportPojo fromEntity(Sport sport) {
        SportPojo pojo = new SportPojo();
        pojo.setId(sport.getId());
        pojo.setName(sport.getName());

        if (sport.getSportsmen() != null) {
            List<SportsmanPojo> sportsmen = new ArrayList<>();
            pojo.setSportsmen(sportsmen);
            for (Sportsman sportsman : sport.getSportsmen()) {
                sportsmen.add(SportsmanPojo.fromEntity(sportsman, sport.getId()));
            }
        }
        if (sport.getTeams() != null) {
            List<TeamPojo> teams = new ArrayList<>();
            pojo.setTeams(teams);
            for (Team team : sport.getTeams()) {
                teams.add(TeamPojo.fromEntity(team, sport.getId()));
            }
        }
        return pojo;
    }

    public static Sport toEntity(SportPojo pojo) {
        Sport sport = new Sport();
        sport.setId(pojo.getId());
        sport.setName(pojo.getName());

        if (pojo.getSportsmen() != null) {
            List<Sportsman> sportsmen = new ArrayList<>();
            sport.setSportsmen(sportsmen);
            for (SportsmanPojo sportsmanPojo : pojo.getSportsmen()) {
                Sportsman sportsman = SportsmanPojo.toEntity(sportsmanPojo);
                sportsman.setSport(sport);
                sportsmen.add(sportsman);
            }
        }
        if (pojo.getTeams() != null) {
            List<Team> teams = new ArrayList<>();
            sport.setTeams(teams);
            for (TeamPojo teamPojo : pojo.getTeams()) {
                Team team = TeamPojo.toEntity(teamPojo);
                team.setTeamsSport(sport);
                teams.add(team);
            }
        }
        return sport;
    }

    public static List<SportPojo> convertSportsToPojo(List<Sport> sports) {
        List<SportPojo> pojoList = new ArrayList<>();
        for (Sport sport : sports) {
            pojoList.add(SportPojo.fromEntity(sport));
        }
        return pojoList;
    }
}
