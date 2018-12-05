package br.edu.ifro.util;

import br.edu.ifro.model.Autor;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CadastrarAutor {
    public static void main(String[] args) {
        Autor a1 = new Autor();
        a1.setNome("Machado de Assis");
        
        Autor a2 = new Autor();
        a2.setNome("Carlos Drummond de Andrade");
        
        Autor a3 = new Autor();
        a3.setNome("Graciliano Ramos");
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("biblioteca");
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();
        em.persist(a1);
        em.persist(a2);
        em.persist(a3);
        em.getTransaction().commit();
    }
}
