/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import business_logic.UserdbFacade;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import entities.*;

/**
 *
 * @author ASUS
 */
@ManagedBean
@SessionScoped
public class SignupController {
    @EJB
    private UserdbFacade userdbFacade;
    public Userdb user = new Userdb();
    
    public SignupController() {
    }

    public UserdbFacade getUserdbFacade() {
        return userdbFacade;
    }

    public void setUserdbFacade(UserdbFacade userdbFacade) {
        this.userdbFacade = userdbFacade;
    }

    public Userdb getUser() {
        return user;
    }

    public void setUser(Userdb user) {
        this.user = user;
    }
    
    public String signup(){
        userdbFacade.create(this.user);
        return "login";
    }
}
