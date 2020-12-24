/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business_logic;

import entities.Userdb;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ASUS
 */
@Stateless
public class UserdbFacade extends AbstractFacade<Userdb> {
    @PersistenceContext(unitName = "DemoTwitter-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserdbFacade() {
        super(Userdb.class);
    }
    public Userdb login(String email, String Password){
        Userdb user = null;
        List<Userdb> result = em.createNamedQuery("Userdb.login", Userdb.class)
            .setParameter("email", email)
            .setParameter("password", Password)
            .getResultList();
        if(!result.isEmpty()) user = (Userdb) result.get(0);
        return user;
    }
    public List<Userdb> search(String key){
        List<Userdb> user = null;
        List<Userdb> result = em.createNamedQuery("Userdb.search", Userdb.class)
            .setParameter("key", key)
            .getResultList();
        if(!result.isEmpty()) user = (List<Userdb>) result;
        return user;
    } 
}
