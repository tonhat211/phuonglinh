package model;

import java.sql.Timestamp;

public class Log {
    private int id;
    private String ip;
    private String info;
    private String action;
    private String preValue;
    private String afterValue;
    private String area;
    private int level;
    private Timestamp time;

    public Log() {
    }

    public Log(int id, String ip, String info, String action, String preValue, String afterValue, String area, int level, Timestamp time) {
        this.id = id;
        this.ip = ip;
        this.info = info;
        this.action = action;
        this.preValue = preValue;
        this.afterValue = afterValue;
        this.area = area;
        this.level = level;
        this.time = time;
    }

    public Log(String ip, String info, String action, String preValue, String afterValue, int level, Timestamp time) {
        this.ip = ip;
        this.info = info;
        this.action = action;
        this.preValue = preValue;
        this.afterValue = afterValue;
        this.level = level;
        this.time = time;
    }

    public Log(String ip, String info, String action, String preValue, String afterValue, int level) {
        this.ip = ip;
        this.info = info;
        this.action = action;
        this.preValue = preValue;
        this.afterValue = afterValue;
        this.level = level;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPreValue() {
        return preValue;
    }

    public void setPreValue(String preValue) {
        this.preValue = preValue;
    }

    public String getAfterValue() {
        return afterValue;
    }

    public void setAfterValue(String afterValue) {
        this.afterValue = afterValue;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", info='" + info + '\'' +
                ", action='" + action + '\'' +
                ", preValue='" + preValue + '\'' +
                ", afterValue='" + afterValue + '\'' +
                ", level=" + level +
                ", time=" + time +
                '}';
    }
}
