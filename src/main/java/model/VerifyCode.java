package model;

import DAO.VerifyCodeDAO;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class VerifyCode {
	private String code;
    private String email;
    private LocalDateTime time;
    private int isVerify;

    public VerifyCode() {
    }

    public VerifyCode(String code, String email, LocalDateTime time, int isVerify) {
        this.code = code;
        this.email = email;
        this.time = time;
        this.isVerify = isVerify;
    }

    public VerifyCode(String email) {
        this.email = email;
        this.isVerify= 0;
    }

    public VerifyCode(String code, String email) {
        this.code = code;
        this.email = email;
        this.isVerify = 0;
    }




    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public int getIsVerify() {
        return isVerify;
    }

    public void setIsVerify(int isVerify) {
        this.isVerify = isVerify;
    }

    @Override
    public String toString() {
        return "VerifyCode{" +
                "code='" + code + '\'' +
                ", time=" + time +
                ", isVerify=" + isVerify +
                '}';
    }

    public boolean isWithinFiveMinutes() {
        long minutes = ChronoUnit.MINUTES.between(this.time, LocalDateTime.now());
        return minutes <= 5;
    }

    public static void main(String[] args) {
        VerifyCode verifyCode = new VerifyCode("55425","21130463@st.hcmuaf.edu.vn");
//        verifyCode = VerifyCodeDAO.getInstance().selectTheLast(verifyCode.getEmail());
//        System.out.println(verifyCode.verify());
    }


}
