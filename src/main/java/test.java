import DAO.*;
import model.*;

import java.time.LocalDateTime;

public class test {
    public static void main(String[] args) {
        LocalDateTime dt = LocalDateTime.now();
        String time = "time: " + dt.toString();
        System.out.println(time);

        String dateTimeString = "2025-01-19T23:21:35.529396900";
        LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString);
        System.out.println("LocalDateTime: " + localDateTime);
    }
}
