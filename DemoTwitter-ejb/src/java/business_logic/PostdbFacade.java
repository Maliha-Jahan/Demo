/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business_logic;

import entities.Postdb;
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
public class PostdbFacade extends AbstractFacade<Postdb> {
    @PersistenceContext(unitName = "DemoTwitter-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PostdbFacade() {
        super(Postdb.class);
    }
    public List<Postdb> findByEmail(Userdb email){
        List<Postdb> posts = null;
        List<Postdb> result = em.createNamedQuery("Postdb.findByEmail", Postdb.class)
            .setParameter("email", email)
            .getResultList();
        if(!result.isEmpty()) posts = (List<Postdb>) result;
        return posts;
    }
    
}
