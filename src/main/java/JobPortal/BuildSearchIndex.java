package JobPortal;


import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Component
public class BuildSearchIndex
        implements ApplicationListener<ApplicationReadyEvent> {
  
  @PersistenceContext
  private EntityManager entityManager;
  
  @Override
  public void onApplicationEvent(final ApplicationReadyEvent event) {
//    try {
////      FullTextEntityManager fullTextEntityManager =
////        Search.getFullTextEntityManager(entityManager);
////      fullTextEntityManager.createIndexer().startAndWait();
//    }
//    catch (InterruptedException e) {
//      System.out.println(
//        "An error occurred trying to build the serach index: " +
//         e.toString());
//    }
    return;
  }


}
