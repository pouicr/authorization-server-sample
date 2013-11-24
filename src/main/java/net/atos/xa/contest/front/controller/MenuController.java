package net.atos.xa.contest.front.controller;

import net.atos.xa.contest.front.Navigation;

import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: pouic
 * Date: 23/11/13
 * Time: 20:20
 * To change this template use File | Settings | File Templates.
 */
@Named("menu")
@ViewScoped
public class MenuController implements Serializable {

    public Class<? extends Navigation> getHomePage(){
        return Navigation.Index.class;
    }

    public Class<? extends Navigation> getLoginPage(){
        return Navigation.Login.class;
    }

    public Class<? extends Navigation> getBinRangeAdmin(){
        return Navigation.BinRange.class;
    }

}
