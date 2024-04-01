package org.nosql.postgresql.service;

import org.nosql.postgresql.utils.OutputTable;
import org.nosql.postgresql.repository.JDBC;
import org.nosql.postgresql.dto.SportPojo;
import org.nosql.postgresql.entity.Sport;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

public class Service {
    private final JDBC jdbc;

    public Service(DataSource dataSource) {
        this.jdbc = new JDBC(dataSource);
    }

    public SportPojo createSport(String name) {
        List<Sport> querySport = jdbc.createSport(name);
        return SportPojo.fromEntity(querySport.get(0));
    }

    public boolean deleteSport(String name) {
        if (jdbc.isSportExists(name)) {
            jdbc.deleteSport(name);
            return true;
        }
        return false;
    }

    public int editSport(String name, String newName) {
        return jdbc.editSport(name, newName);
    }

    public void selectHaving() {
        List<Map<String, Object>> result = jdbc.selectHaving();
        OutputTable.printHavingTable("HAVING", "team", "birth", result);
    }

    public void selectJoins() {
        List<Map<String, Object>> inner_join_result = jdbc.selectInnerJoin();
        List<Map<String, Object>> left_join_result = jdbc.selectLeftJoin();
        List<Map<String, Object>> right_join_result = jdbc.selectRightJoin();
        List<Map<String, Object>> full_join_result = jdbc.selectFullJoin();
        List<Map<String, Object>> cross_join_result = jdbc.selectCrossJoin();

        OutputTable.printJoinTable("INNER", "firstname", "email", inner_join_result);
        OutputTable.printJoinTable(" LEFT", "firstname", "email", left_join_result);
        OutputTable.printJoinTable("RIGHT", "firstname", "email", right_join_result);
        OutputTable.printJoinTable(" FULL", "firstname", "email", full_join_result);
        OutputTable.printJoinTable("CROSS", "firstname", "email", cross_join_result);
    }

    public void selectFunction() {
        List<Map<String, Object>> result = jdbc.selectFunction();
        OutputTable.printWindowFunctionTable(result);
    }

    public void recursiveFunction() {
        List<Map<String, Object>> result = jdbc.recursiveFunction();
        OutputTable.printRecursiveTable("RECUR", "id", "firstname", "parentid",  result);
    }
}
