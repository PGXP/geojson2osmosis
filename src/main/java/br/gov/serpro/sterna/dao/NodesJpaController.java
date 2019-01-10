/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.serpro.sterna.dao;

import br.gov.serpro.sterna.dao.exceptions.NonexistentEntityException;
import br.gov.serpro.sterna.dao.exceptions.PreexistingEntityException;
import br.gov.serpro.sterna.entity.Nodes;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author 70744416353
 */
public class NodesJpaController implements Serializable {

    public NodesJpaController() {
        emf = Persistence.createEntityManagerFactory("sternaPU");
    }

    public NodesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Nodes nodes) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(nodes);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findNodes(nodes.getId()) != null) {
                throw new PreexistingEntityException("Nodes " + nodes + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Nodes nodes) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            nodes = em.merge(nodes);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = nodes.getId();
                if (findNodes(id) == null) {
                    throw new NonexistentEntityException("The nodes with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Nodes nodes;
            try {
                nodes = em.getReference(Nodes.class, id);
                nodes.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The nodes with id " + id + " no longer exists.", enfe);
            }
            em.remove(nodes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Nodes> findNodesEntities() {
        return findNodesEntities(true, -1, -1);
    }

    public List<Nodes> findNodesEntities(int maxResults, int firstResult) {
        return findNodesEntities(false, maxResults, firstResult);
    }

    private List<Nodes> findNodesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Nodes.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Nodes findNodes(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Nodes.class, id);
        } finally {
            em.close();
        }
    }

    public int getNodesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Nodes> rt = cq.from(Nodes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
