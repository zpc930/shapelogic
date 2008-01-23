package org.shapelogic.external;

import java.util.*;
import javax.persistence.*;

import org.shapelogic.entities.Message;


/** Data class for rule to be used with Hibernate  
 * 
 * @author Sami Badawi
 *
 */
public class HelloHibernate {

  public static void main(String[] args) {

    // Start EntityManagerFactory
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("helloworld");

    // First unit of work
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();

    Message message = new Message("Hello World with JPA");
    em.persist(message);

    Message message2 = new Message("Hej Sami");
    message2.setNextMessage(message);
    em.persist(message2);

    tx.commit();
    em.close();

    // Second unit of work
    EntityManager newEm = emf.createEntityManager();
    EntityTransaction newTx = newEm.getTransaction();
    newTx.begin();

    List messages =
        newEm.createQuery("select m from Message m order by m.text asc").getResultList();

    System.out.println( messages.size() + " message(s) found:" );

    for (Object m : messages) {
      Message loadedMsg = (Message) m;
      System.out.println(loadedMsg.getText());
    }

    newTx.commit();
    newEm.close();

    // Shutting down the application
    emf.close();
  }

}
