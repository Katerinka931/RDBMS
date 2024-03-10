package org.nosql.postgresql.dto;

import org.nosql.postgresql.entity.Sportsman;
import org.nosql.postgresql.entity.Team;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TeamPojo {
    private long id;
    private String name;
    private int count;
    private List<SportsmanPojo> sportsmen;
    private long sport_id;

    public static TeamPojo fromEntity(Team team) {
        TeamPojo pojo = new TeamPojo();
        setDataToPojo(pojo, team);
        return pojo;
    }

    public static TeamPojo fromEntity(Team team, long sport_id) {
        TeamPojo pojo = new TeamPojo();
        pojo.setSport_id(sport_id);
        setDataToPojo(pojo, team);
        return pojo;
    }

    public static Team toEntity(TeamPojo pojo) {
        Team team = new Team();
        team.setId(pojo.getId());
        team.setName(pojo.getName());
        team.setCount(pojo.getCount());

        if (pojo.getSportsmen() != null) {
            List<Sportsman> sportsmen = new ArrayList<>();
            team.setSportsmen(sportsmen);
            for (SportsmanPojo sportsmanPojo : pojo.getSportsmen()) {
                Sportsman sportsman = SportsmanPojo.toEntity(sportsmanPojo);
                sportsman.setTeam(team);
                sportsmen.add(sportsman);
            }
        }
        return team;
    }

    public static List<TeamPojo> convertTeamsToPojo(List<Team> teams) {
        List<TeamPojo> pojos = new ArrayList<>();
        for (Team team : teams) {
            pojos.add(TeamPojo.fromEntity(team));
        }
        return pojos;
    }

    private static void setDataToPojo(TeamPojo pojo, Team team) {
        pojo.setId(team.getId());
        pojo.setName(team.getName());
        pojo.setCount(team.getCount());

        if (team.getSportsmen() != null) {
            List<SportsmanPojo> sportsmen = new ArrayList<>();
            pojo.setSportsmen(sportsmen);
            for (Sportsman sportsman : team.getSportsmen()) {
                sportsmen.add(SportsmanPojo.fromEntity(sportsman, team.getTeamsSport().getId(), team.getId()));
            }
        }
    }
}
