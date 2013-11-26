package net.atos.xa.contest.dto.front;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: pouic
 * Date: 23/11/13
 * Time: 20:14
 * To change this template use File | Settings | File Templates.
 */
@SessionScoped
public class User implements Serializable {

    private String name="UNKNOWN";

    private boolean logged = false;

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
