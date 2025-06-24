package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Employee {
    public int id;
    public User user;
    public String position;
    public String area;
    public LocalDate updateAt;
    public int avai;

    public Employee(int id, User user, String position, String area, LocalDate updateAt, int avai) {
        this.id = id;
        this.user = user;
        this.position = position;
        this.area = area;
        this.updateAt = updateAt;
        this.avai = avai;
    }

    //insert
    public Employee(User user, String position, String area) {
        this.user = user;
        this.position = position;
        this.area = area;
    }

    public int getUserID() {
        return user.id;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", user=" + user +
                ", position='" + position + '\'' +
                ", area='" + area + '\'' +
                ", updateAt=" + updateAt +
                ", avai=" + avai +
                '}';
    }
}
