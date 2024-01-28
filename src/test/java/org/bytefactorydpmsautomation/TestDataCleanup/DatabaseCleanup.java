package org.bytefactorydpmsautomation.TestDataCleanup;

import org.bytefactorydpmsautomation.PageObjects.CheckListExecutionPage;
import org.bytefactorydpmsautomation.PageObjects.ResolutionWorkOrderPage;
import org.bytefactorydpmsautomation.PageObjects.SchedulerPage;
import org.testng.annotations.AfterSuite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import static org.bytefactorydpmsautomation.tests.LoginTest.driver;

public class DatabaseCleanup {
    private Connection connection;
    public void connectToDatabase() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://192.168.0.179:5432/bfal";
        String user = "postgres";
        String password = "max2max123456!";
        connection = DriverManager.getConnection(url, user, password);
    }
    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
    public void cleanTestData() {
        String schedulerId = SchedulerPage.schedulerId;
        String workOrderNum = ResolutionWorkOrderPage.workOrderNum;
        String[] deleteQueries = {
//                    "INSERT INTO user_role(role_id, active, role_name) VALUES (5, 'true', 'SuperUser Role');"
                "DELETE FROM user_role WHERE role_name IN ('SuperUser Role', 'Manager Role', 'Technician Role');",
//                "DELETE FROM user WHERE username IN ('Test User(S10)', 'Test User(M10)', 'Test User(T10)');",
//                "DELETE FROM user_group WHERE group_name = 'Tech Group';",
//                "DELETE FROM scheduler WHERE Assert = 'CNC Milling Machine' AND AssignedToUser = 'Gowtham Perumalsamy' AND Description = 'CNC Milling Machine Scheduler';",
//                "DELETE FROM pm_check WHERE check_name IN ('Check for oil levels', 'Check air lubricators', 'Check for axis reference points', 'Inspect for cracked hoses', 'Verify lube operation', 'Check tool knockout')",
//                "DELETE FROM pm_check_type WHERE check_type_name = 'M'",
//                "DELETE FROM pm_check_list WHERE check_list_name = 'Daily Checklist' AND description  = 'Daily Checklist Des' ",
//                //Write code to fetch SchedulerId and then perform Scheduler Deletion operation
//                "DELETE FROM im_scheduler WHERE scheduler_id = '" + schedulerId + "'",
//                "DELETE FROM pm_resolution_work_order WHERE resolution_work_order_id = '" + workOrderNum + "'"
        };
        try {
            for (String deleteQuery : deleteQueries) {
                try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
                    statement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @AfterSuite
    public void tearDownAfterAllTests() throws Exception {
        connectToDatabase();
        cleanTestData();
        Thread.sleep(2000);
        closeConnection();
        driver.close();
    }
}

