package models;

import java.util.Date;

public class UserInput {


    private String login;
    private String pass;
    private String path;
    private String role;
    private String vols;
    private String dss;
    private String des;
    public int vol;
    public Date ds;
    public Date de;
    public int userId;
    public int resId;

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setVols(String vols) {
        this.vols = vols;
    }

    public void setDss(String dss) {
        this.dss = dss;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

    public String getPath() {
        return path;
    }

    public String getRole() {
        return role;
    }

    public String getVols() {
        return vols;
    }

    public String getDss() {
        return dss;
    }

    public String getDes() {
        return des;
    }

    public UserInput(String login, String pass) {
        this.login = login;
        this.pass = pass;
    }

    public UserInput(String login, String pass, String role, String path) {
        this.login = login;
        this.pass = pass;
        this.path = path;
        this.role = role;
    }

    public UserInput(String login, String pass, String role, String path, String dss, String des, String vols) {
        this.login = login;
        this.pass = pass;
        this.path = path;
        this.role = role;
        this.vols = vols;
        this.dss = dss;
        this.des = des;
    }

    public UserInput() {
    }

    public boolean isEmpty() {
        return (this.login == null && this.pass == null);
    }

}
