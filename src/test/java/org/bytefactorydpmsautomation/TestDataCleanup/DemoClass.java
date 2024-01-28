package org.bytefactorydpmsautomation.TestDataCleanup;

import java.net.MalformedURLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class DemoClass {

    public static void main(String[] args) throws MalformedURLException {
        DeleteRequest deleteRequest = new DeleteRequest();
//        deleteRequest.deleteResolutionWorkOrder(1002);
//        deleteRequest.deleteScheduler(1363);
//        deleteRequest.deleteAsset(757);
//        deleteRequest.deleteCheckTypes(55);
//        deleteRequest.deleteChecks(1);
//        deleteRequest.deleteCheckList(304);
//        deleteRequest.deleteUserGroup(15);
//        deleteRequest.deleteUser(1360);
//        deleteRequest.deleteRole(2216);

        LocalDateTime currentDateTime = LocalDateTime.now();
        DayOfWeek dayOfWeek = currentDateTime.getDayOfWeek();
//        System.out.println("Day of the week: " + dayOfWeek);

        for (DayOfWeek day : DayOfWeek.values()) {
            System.out.println(day);
        }
    }
}
