package atmProject;
import java.util.*;
import java.sql.*;

public class atm {

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");		//Register class diver
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm", "root", ""); // Create connection
            Statement stmt = con.createStatement(); // Create statement
            Scanner sc = new Scanner(System.in);
            System.out.println("Hello Welcome to our ATM");
            System.out.println("Enter your Pin Number");
            int pin = sc.nextInt();
            ResultSet rs = stmt.executeQuery("select * from list where ac_no=" + pin); // Execute Query
            String name = null;
            int count = 0;
            int balance = 0;
            while (rs.next()) {
                name = rs.getString(3);
                balance = rs.getInt(4);
                count++;
            }

            int deposit = 0;
            int withdraw = 0;

            if (count > 0) {
                System.out.println("Hello " + name);
                while (true) {
                    System.out.println("press 1 for balance enquiry");
                    System.out.println("press 2 for cash deposit");
                    System.out.println("press 3 for cash withdraw");
                    System.out.println("press 4 for mini statement");
                    System.out.println("press 5 for exit");

                    System.out.println();
                    System.out.println("Enter your choice");
                    int option = sc.nextInt();
                    switch (option) {
                        case 1:
                            System.out.println("Your account balance is Rs: " + balance);
                            break;
                        case 2:
                            System.out.println("How much amount do you want to add to your bank account?");
                            deposit = sc.nextInt();
                            balance = deposit + balance;
                            int add = stmt.executeUpdate("UPDATE list SET balance = " + balance + " WHERE ac_no = " + pin);
                            System.out.println("Deposit successfully! Now your current balance is: " + balance);
                            break;
                        case 3:
                            System.out.println("How much amount do you want to withdraw?");
                            withdraw = sc.nextInt();
                            if (balance >= withdraw) {
                                System.out.println("Withdraw Successfully");
                                balance = balance - withdraw;
                                int sub = stmt.executeUpdate("UPDATE list SET balance = " + balance + " WHERE ac_no = " + pin);
                            } else {
                                System.out.println("Insufficient amount");
                            }
                            break;
                        case 4:
                            System.out.println("Mini statement");
                            System.out.println("Available balance is Rs " + balance);
                            System.out.println("Last deposit amount is " + deposit);
                            System.out.println("Last withdraw amount is " + withdraw);
                            break;
                        case 5:
                            System.out.println("Thanks, visit again!");
                            break;
                        default:
                            System.out.println("Invalid option");
                            break;
                    }
                    if (option == 5) {
                        break;
                    }
                }
            } else {
                System.out.println("Invalid pin");
            }
            sc.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
