package net.atos.xa.contest.dto.front;

/**
 * Created with IntelliJ IDEA.
 * User: pouic
 * Date: 23/11/13
 * Time: 20:14
 * To change this template use File | Settings | File Templates.
 */
public class User {

    private String name="Unknown";

    private boolean logged = false;

    public User() {
    }

    public void logout(){
        logged = false;
    }

    public void login(){
        logged = true;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }
}
