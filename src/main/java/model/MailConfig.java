package model;

public class MailConfig {
    private int id;
    private String email;
    private String pwd;
    private int avai;

    public MailConfig(int id, String email, String pwd, int avai) {
        this.id = id;
        this.email = email;
        this.pwd = pwd;
        this.avai = avai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getAvai() {
        return avai;
    }

    public void setAvai(int avai) {
        this.avai = avai;
    }

    public void getPlainPwd() {

    }
}
