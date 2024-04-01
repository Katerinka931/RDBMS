package org.nosql.postgresql.repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

import lombok.RequiredArgsConstructor;
import org.nosql.postgresql.entity.Sport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


@RequiredArgsConstructor
public class JDBC {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JDBC(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private final RowMapper<Sport> sportRowMapper = (rs, rowNum) -> {
        Sport sport = new Sport();
        sport.setId(rs.getLong("id"));
        sport.setName(rs.getString("name"));
        return sport;
    };

    public List<Sport> createSport(String name) {
        var params = new MapSqlParameterSource();
        params.addValue("name", name);
        return namedParameterJdbcTemplate.query("INSERT INTO sport (name) VALUES (:name) returning id, name",
                params, sportRowMapper);
    }

    public void deleteSport(String name) {
        var params = new MapSqlParameterSource();
        params.addValue("name", name);
        namedParameterJdbcTemplate.update("DELETE FROM sport WHERE name = :name", params);
    }

    public int editSport(String name, String newName) {
        var params = new MapSqlParameterSource();
        params.addValue("name", name);
        params.addValue("newName", newName);

        return namedParameterJdbcTemplate.update("UPDATE sport SET name = :newName WHERE name = :name", params);
    }


    public List<Map<String, Object>> selectHaving() {
        // старший человек в команде
        return jdbcTemplate.queryForList("SELECT team_id, max(birthdate) AS mx FROM sportsman\n" +
                "GROUP BY team_id\n" +
                "HAVING max(birthdate) > '2007-01-01'");
    }

    public List<Map<String, Object>> selectInnerJoin() {
        return jdbcTemplate.queryForList("SELECT firstname, email FROM sportsman INNER JOIN contacts ON sportsman.id = contacts.sportsman_id");
    }

    public List<Map<String, Object>> selectLeftJoin() {
        return jdbcTemplate.queryForList(" SELECT firstname, email FROM sportsman\n" +
                "LEFT OUTER JOIN contacts ON sportsman.id = contacts.sportsman_id");
    }

    public List<Map<String, Object>> selectRightJoin() {
        return jdbcTemplate.queryForList("SELECT firstname, email FROM sportsman\n" +
                "RIGHT OUTER  JOIN contacts ON sportsman.id = contacts.sportsman_id");
    }

    public List<Map<String, Object>> selectFullJoin() {
        return jdbcTemplate.queryForList("SELECT firstname, email FROM sportsman\n" +
                "FULL OUTER  JOIN contacts ON sportsman.id = contacts.sportsman_id");
    }

    public List<Map<String, Object>> selectCrossJoin() {
        return jdbcTemplate.queryForList("SELECT firstname, email FROM sportsman\n" +
                "CROSS JOIN contacts");
    }

    public List<Map<String, Object>> selectFunction() {
        return jdbcTemplate.queryForList("SELECT name, sport_id, sum(count) over (partition BY sport_id) AS COUNT\n" +
                "FROM team");
    }

    public List<Map<String, Object>> recursiveFunction() {
        return jdbcTemplate.queryForList("WITH RECURSIVE tmp AS (\n" +
                "SELECT id, firstname, parentid FROM sportsman WHERE parentid=0\n" +
                "UNION ALL\n" +
                "SELECT s.id, s.firstname, s.parentid FROM sportsman s JOIN tmp ON tmp.id = s.parentid\n" +
                ") \n" +
                "SELECT * FROM tmp;"); //OFFSET 5 LIMIT 10
    }

    public boolean isSportExists(String name) {
        return namedParameterJdbcTemplate.queryForObject("SELECT count(*) FROM sport WHERE name = :name",
                Collections.singletonMap("name", name), Integer.class) > 0;
    }
}