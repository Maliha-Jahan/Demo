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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ASUS
 */
@ManagedBean
@SessionScoped
public class LoginController {
    @EJB
    private UserdbFacade userdbFacade;
    public Userdb user = new Userdb();
    
    public LoginController() {
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
    
    public String login(){
        String email = this.user.getEmail();
        String password = this.user.getPassword();
        Userdb result_user = userdbFacade.login(email, password);
        if (result_user != null) {
            HttpServletRequest request = (HttpServletRequest)(FacesContext.getCurrentInstance()
                    .getExternalContext().getRequest());
            //HttpServletResponse response = (HttpServletResponse)(FacesContext.getCurrentInstance().getExternalContext().getResponse());
            HttpSession session = request.getSession(true);
            session.setMaxInactiveInterval(60*60);
            session.setAttribute("email", email);
            //Cookie c1 = new Cookie("userid", ""+Uid.intValue());
            //c1.setMaxAge(60*60*24*30);
            //response.addCookie(c1);
            return "home";}
        else {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,
                "Incorrect Username and Passowrd", "Please enter correct username and Password"));
            return "login";}
    }
    
}
