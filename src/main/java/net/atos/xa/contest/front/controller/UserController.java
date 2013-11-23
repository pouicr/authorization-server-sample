package net.atos.xa.contest.front.controller;

import net.atos.xa.contest.dto.front.User;
import net.atos.xa.contest.front.Navigation;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: pouic
 * Date: 23/11/13
 * Time: 20:30
 * To change this template use File | Settings | File Templates.
 */
@Named("user")
@SessionScoped
public class UserController implements Serializable {

    private User user = new User();
    private String identification;
    private String password;

    public Class<? extends Navigation> login() {
        user.setName(getIdentification());
        user.setLogged(true);
        return Navigation.Index.class;
    }

    public Class<? extends Navigation> logout() {
        user.setName("Anonymous");
        user.setLogged(false);
        return Navigation.Index.class;
    }

    public boolean isLogged() {
        return user.isLogged();
    }

    public String getName() {
        return user.getName();
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
