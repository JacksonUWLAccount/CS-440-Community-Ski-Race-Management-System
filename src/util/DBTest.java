package util;

import java.sql.Connection;

public class DBTest {

    public static void main(String[] args) {
        
        Connection conn = DBConnection.getConnection();
        if (conn != null) {
            System.out.println("Connection test passed!");
        } else {
            System.out.println("Connection failed.");
        }
        
        DBConnection.closeConnection();
    }
}

/*
 * DAO = Data Access Object , online said was standard naming
 *
 * PROJECT STRUCTURE:
 *
 * src/
 *  ├── util/
 *  │    ├── DBConnection.java   - manages the database connection
 *  │    └── DBTest.java         - run this to test your connection works
 *  └── dao/
 *       ├── UserDAO.java        - signup, login, authentication (US.15, US.16, SY.4)
 *       ├── TeamDAO.java        - create/delete team, add/remove skiers & coaches (US.2, US.3, US.4, US.9, US.10, US.11)
 *       ├── RaceDAO.java        - create, update, cancel, stats (US.6, US.7, US.8, US.12, US.13, US.14)
 *       ├── CourseDAO.java      - create course (US.5)
 *       ├── SeasonDAO.java      - create league/season (US.1)
 *       └── ResultsDAO.java     - calculate totals, winner, performance report (SY.2, SY.3, SY.5)
 *
 * SETUP STEPS:
 *
 * 1. Create a file called config.properties in the project root, same level as src not inside it
 * 2. Inside that file put your MySQL password like this, no quotes no spaces around the equals:
 *        DB_PASSWORD=yourmysqlpassword
 * 3. config.properties is in .gitignore so it never gets pushed to GitHub,
 *    everyone just has their own local copy with their own password
 * 4. Run the CREATE TABLE script from the planning doc in MySQL Workbench to set up the database
 * 5. Add the MySQL Connector JAR to your classpath in Eclipse:
 *    right-click project -> Build Path -> Configure Build Path -> Libraries -> Add External JARs in Classpath 
 *    (Use one in project so we have same connector is most recent)
 * 6. Run DBTest.java to make sure your connection works before writing any queries
 *
 * What needs to be in each file:
 * ```

---

**config.properties** create in project (write:
DB_PASSWORD=yourmysqlpassword


**.gitignore** create in project (write:
config.properties

 *
 *
 * example method: user signup on how to use DBconnection class:
 * 
 * public static void signupUser(String username, String firstName,
                              String lastName, String email, String password) {

    Connection conn = DBConnection.getConnection();

    String sql = "INSERT INTO Users (Username, FirstName, LastName, Email, Password) VALUES (?, ?, ?, ?, ?)";

    try {
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, firstName);
        ps.setString(3, lastName);
        ps.setString(4, email);
        ps.setString(5, password);
        ps.executeUpdate();
        System.out.println("User created successfully.");
    } catch (SQLException e) {
        System.err.println("Signup failed.");
        e.printStackTrace();
    }
 }
 *
 * IMPORTS NEEDED IN EVERY DAO FILE:
 *
 *  import util.DBConnection;
 *  import java.sql.Connection;
 *  import java.sql.PreparedStatement;
 *  import java.sql.ResultSet;
 *  import java.sql.SQLException;
 */