package model;

import DAO.ProductUnitDAO;
import DAO.SaleProgramDAO;
import com.google.gson.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SaleProgram {
    public int id;
    public ArrayList<Integer> productIDs;
    public String title;
    public String content;
    public double discount;
    public int kind;
    public LocalDateTime startTime;
    public LocalDateTime endTime;
    public int avai;

    public SaleProgram(int id, ArrayList<Integer> productIDs, String title, String content, double discount, int kind, LocalDateTime startTime, LocalDateTime endTime, int avai) {
        this.id = id;
        this.productIDs = productIDs;
        this.title = title;
        this.content = content;
        this.discount = discount;
        this.kind = kind;
        this.startTime = startTime;
        this.endTime = endTime;
        this.avai = avai;
    }

    // cartUnit
    public SaleProgram(int id, String title, double discount) {
        this.id = id;
        this.title = title;
        this.discount = discount;
    }

    public String getStartTimeFormat() {
        return Constant.formatLocalDateTime(startTime);
    }

    public String getEndTimeFormat() {
        return Constant.formatLocalDateTime(endTime);
    }
}
