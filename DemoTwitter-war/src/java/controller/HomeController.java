/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import business_logic.PostdbFacade;
import business_logic.UserdbFacade;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import entities.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ASUS
 */
@ManagedBean
@SessionScoped
public class HomeController{
    @EJB
    private UserdbFacade userdbFacade;
    @EJB
    private PostdbFacade postdbFacade;
    public Userdb user = new Userdb();
    public Postdb post = new Postdb();
    public String initialKey = null;
    List<Userdb> users = null;
    boolean show = false;

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public List<Userdb> getUsers() {
        return users;
    }

    public void setUsers(List<Userdb> users) {
        this.users = users;
    }
    
    public String getInitialKey() {
        return initialKey;
    }

    public void setInitialKey(String initialKey) {
        this.initialKey = initialKey;
    }

    public UserdbFacade getUserdbFacade() {
        return userdbFacade;
    }

    public void setUserdbFacade(UserdbFacade userdbFacade) {
        this.userdbFacade = userdbFacade;
    }

    public PostdbFacade getPostdbFacade() {
        return postdbFacade;
    }

    public void setPostdbFacade(PostdbFacade postdbFacade) {
        this.postdbFacade = postdbFacade;
    }

    public Userdb getUser() {
        return user;
    }

    public void setUser(Userdb user) {
        this.user = user;
    }

    public Postdb getPost() {
        return post;
    }

    public void setPost(Postdb post) {
        this.post = post;
    }
    
    public HomeController() {
    }
    
    public void retrieve(){
        String email = null;
        HttpServletRequest request = (HttpServletRequest)(FacesContext.getCurrentInstance().getExternalContext().getRequest());
        HttpSession session = request.getSession(true);
        if(session != null){
            String sessionEmail = (String) session.getAttribute("email");
            if(sessionEmail != null && !sessionEmail.equals("")){ 
                email = sessionEmail;
                this.user = this.userdbFacade.find(email);
            }
        }
    }
    
    public String makePost(){
        Date postTime=new Date();
        String email = null;
        String postId = null;
        email = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("retrieved_email");
        if (email != null){
            postId = email + "_" +postTime;
            this.post.setEmail(userdbFacade.find(email));
            this.post.setPostId(postId);
            this.post.setPostTime(postTime);
            postdbFacade.create(post);
        }
        return "home";
    }
    public void search(){
        this.show = true;
        String key = this.initialKey;
        if (key != null){
            key = key.replace(' ', '%');
            key = "%" + key + "%";
            users = userdbFacade.search(key);
            
        }
        System.out.println("Here1"+show);
    }
    public void follow(){
        String followEmail = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("follow_email");
        this.retrieve();
        Userdb followUser = this.userdbFacade.find(followEmail);
        Collection<Userdb> follows = this.user.getUserdbCollection();
        follows.add(followUser);
        this.user.setUserdbCollection(follows);
        this.userdbFacade.edit(this.user);
    }
    public void unfollow(){
        String unfollowEmail = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("unfollow_email");
        this.retrieve();
        Userdb unfollowUser = this.userdbFacade.find(unfollowEmail);
        Collection<Userdb> follows = this.user.getUserdbCollection();
        if(follows.contains(unfollowUser)){ 
            follows.remove(unfollowUser);
            this.user.setUserdbCollection(follows);
            this.userdbFacade.edit(this.user);
        }
    }
    public List<Postdb> feed(){
        this.retrieve();
        Collection<Userdb> follows = this.user.getUserdbCollection();
        List<Postdb> posts = new ArrayList<>();
        for (Userdb followingUser : follows){
            List<Postdb> postsByEmail = postdbFacade.findByEmail(followingUser);
            if((!postsByEmail.isEmpty()) || postsByEmail!=null) {
                posts.addAll(postsByEmail);
            }
        }
        /*Comparator<Postdb> compareByTime = new Comparator<Postdb>() {
            @Override
            public int compare(Postdb o1, Postdb o2) {
                return o1.getPostTime().compareTo(o2.getPostTime());
            }
        };
        Collections.sort(posts, compareByTime);*/
        return posts;
    }
    
    public void like(){
        this.retrieve();
        String likePostId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("post_id");
        Postdb likePost = this.postdbFacade.find(likePostId);
        Collection<Userdb> likedUsers = likePost.getUserdbCollection();
        likedUsers.add(this.user);
        likePost.setUserdbCollection(likedUsers);
        this.postdbFacade.edit(likePost);
    }
    
    public void removeLike(){
        this.retrieve();
        String likePostId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("post_id");
        Postdb likePost = this.postdbFacade.find(likePostId);
        Collection<Userdb> likedUsers = likePost.getUserdbCollection();
        if(likedUsers.contains(this.user)){
            likedUsers.remove(this.user);
            likePost.setUserdbCollection(likedUsers);
            this.postdbFacade.edit(likePost);}
    }
    
    public int numberOfLikes(Postdb currentPost){
        Collection<Userdb> likedUsers = currentPost.getUserdbCollection();
        if (likedUsers.isEmpty() || likedUsers == null) return 0;
        else return likedUsers.size();
    }
    
    public String clear(){
        this.users= null;
        this.show = false;
        return "home";
    }
    
    public String logout(){
        System.out.println("logout");
        return "login";
    }
}
