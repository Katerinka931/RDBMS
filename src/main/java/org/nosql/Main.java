package org.nosql;

import org.nosql.postgresql.service.Service;
import org.nosql.postgresql.utils.HibernateConfiguration;
import org.nosql.postgresql.utils.DataSourceConnection;
import org.nosql.postgresql.dto.SportPojo;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        HibernateConfiguration.getInstance();
//        CustomService serviceJDBC = new CustomService(sessionFactory);
        Service serviceJDBC = new Service(DataSourceConnection.createDataSource());
        Scanner input = new Scanner(System.in);

        System.out.println("Вас приветствует консольный клиент для работы с базой данных PostgreSQL!");
        printMainMenu();
        int choice;

        do {
            choice = input.nextInt();
            switch (choice) {
                case 1 -> {
                    System.out.println("Введите название нового спорта: ");
                    String name = input.next();

                    SportPojo sport = serviceJDBC.createSport(name);
                    System.out.print("Создан новый вид спорта: ");
                    System.out.println(sport.getName() + "(id=" + sport.getId() + ")");
                }
                case 2 -> {
                    System.out.println("Введите название спорта, который хотите удалить: ");
                    String name = input.next();

                    if (!serviceJDBC.deleteSport(name))
                        System.out.println("Вид спорта не существует");
                    else System.out.println("Удаление успешно");
                }
                case 3 -> {
                    System.out.println("Введите название спорта, который хотите отредактировать: ");
                    String name = input.next();

                    System.out.println("Введите новое название: ");
                    String newName = input.next();

                    if (serviceJDBC.editSport(name, newName) != 0)
                        System.out.println("Вид спорта отредактирован. Новое название: " + newName);
                    else System.out.println("Редактирование завершилось неудачей");
                }
                case 4 -> {
                    int additionalChoice;
                    do {
                        printAdditionalMenu();
                        additionalChoice = input.nextInt();

                        switch (additionalChoice) {
                            case 1 -> serviceJDBC.selectHaving();
                            case 2 -> serviceJDBC.selectJoins();
                            case 3 -> serviceJDBC.selectFunction();
                        }
                    } while (additionalChoice != 0);
                }
                default -> {
                    continue;
                }
            }
            printMainMenu();
        } while (choice != 0);

        System.exit(0);
    }

    private static void printMainMenu() {
        System.out.println("Меню: ");
        System.out.println("1 - Создать запись");
        System.out.println("2 - Удалить запись");
        System.out.println("3 - Редактировать запись");
        System.out.println("4 - Сделать выборку записей");
        System.out.println("0 - Выход");
    }

    private static void printAdditionalMenu() {
        System.out.println("Пункт 4: Выборка записей");
        System.out.println("1 - Используя having/group by");
        System.out.println("2 - Все виды соединений (join)");
        System.out.println("3 - Оконная функция");
        System.out.println("0 - Вернуться в главное меню");
    }
}