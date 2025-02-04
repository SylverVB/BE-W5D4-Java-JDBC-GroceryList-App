package com.grocery;

import util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * There is no need to change anything in this class.
 * It does not affect the outcome of the test cases.
 * You can, however, run this class to play with a CLI menu that interacts with the code you write in
 * GroceryDAO.
 */
public class GroceryMain {

    public static void main(String[] args) {
        GroceryDAO groceryDAO = new GroceryDAO();
        databaseSetup();

        boolean active = true;
        
        // Moving Scanner declaration outside the loop to avoid multiple instantiations
        try (Scanner inputScanner = new Scanner(System.in)) {
            while (active) {
                System.out.println("What would you like to do?" +
                        "\n 1: Try to add a grocery item." +
                        "\n 2: See all the groceries." +
                        "\n 3: Quit");

                int inputSelection = inputScanner.nextInt();
                inputScanner.nextLine(); // Consume newline

                switch (inputSelection) {
                    case 1:
                        System.out.println("What grocery would you like to add?");
                        String grocery = inputScanner.nextLine();
                        groceryDAO.addGrocery(grocery);
                        break;
                    case 2:
                        System.out.println("Here are all the groceries: ");
                        System.out.println(groceryDAO.getAllGroceries());
                        break;
                    case 3:
                        System.out.println("Goodbye!");
                        active = false;
                        break;
                    default:
                        System.out.println("Invalid selection. Please try again.");
                }
            }
        } // Scanner will be automatically closed here
    }

    /**
     * For the purpose of this short exercise, this method will destroy and set up a new grocery table.
     * This is not a normal way to set up your tables, in real-world projects you would likely set up your database
     * schema in a SQL editor such as DBeaver or DataGrip.
     */
    public static void databaseSetup() {
        try {
            Connection conn = ConnectionUtil.getConnection();
            PreparedStatement ps1 = conn.prepareStatement("DROP TABLE IF EXISTS grocery");
            ps1.executeUpdate();
            ps1.close(); // Close the statement after use

            PreparedStatement ps2 = conn.prepareStatement("CREATE TABLE grocery(grocery_name VARCHAR(255))");
            ps2.executeUpdate();
            ps2.close(); // Close the statement after use

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}