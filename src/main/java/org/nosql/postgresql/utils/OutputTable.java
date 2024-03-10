package org.nosql.postgresql.utils;

import java.util.List;
import java.util.Map;

public class OutputTable {

    public static void printHavingTable(String title, String firstCol, String secondCol, List<Map<String, Object>> result) {
        System.out.printf("+---------%s-----------+\n", title);
        System.out.println("+------------+-------------+");
        System.out.printf("|    %s    |    %s    |\n", firstCol, secondCol);
        for (Map<String, Object> row : result) {
            System.out.println("+------------+-------------+");
            System.out.printf(String.format("|     %s      |  %s |\n", row.get("team_id"), row.get("mx")));
        }
        System.out.println("+------------+-------------+");
    }

    public static void printJoinTable(String title, String firstCol, String secondCol, List<Map<String, Object>> result) {
        System.out.printf("+-----------------%s------------------+\n", title);
        System.out.println("+----------------------------------------+");
        String text;
        System.out.printf("| %s\t\t| %s\n", firstCol, secondCol);
        for (Map<String, Object> row : result) {
            System.out.println("+----------------------------------------+");
            try {
                String a = row.get(firstCol).toString();
                text = a.length() <= 5 ? "   " : "";
            } catch (NullPointerException e) {
                text = "    ";
            }
            System.out.printf(String.format("| %s\t\t| %s\n", row.get(firstCol) + text, row.get(secondCol)));
        }
        System.out.println("+----------------------------------------+\n");
    }

    public static void printWindowFunctionTable(List<Map<String, Object>> result) {
        System.out.printf("+-----------%s-----------+\n", "Оконная функция");
        System.out.println("+-------------------------------------+");
        System.out.printf("| %s\t\t| %s\t| %s\n", "name", "sport_id", "count");
        for (Map<String, Object> row : result) {
            System.out.println("+-----------+-----------+-------------+");
            System.out.printf(String.format("| %s\t\t| %s\t\t\t| %s\n", row.get("name"), row.get("sport_id"), row.get("count")));
        }
        System.out.println("+-------------------------------------+\n");
    }
}
