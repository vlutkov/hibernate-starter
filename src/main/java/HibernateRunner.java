import lombok.extern.slf4j.Slf4j;
import model.entity.PersonalInfo;
import model.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.HibernateUtil;

@Slf4j
public class HibernateRunner {

    public static void main(String[] args) {
        User user = User.builder()
                .username("petr@gmail.com")
                .personalInfo(PersonalInfo.builder()
                        .firstname("Petr")
                        .lastname("Petrov")
                        .build())
                .build();
        log.info("User entity is in transient state, object: {}", user);

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {

            Session session1 = sessionFactory.openSession();
            try (session1) {
                Transaction transaction = session1.beginTransaction();
                log.trace("Transaction in created, {}", transaction);

                session1.saveOrUpdate(user);
                log.trace("User entity is in persist state: {}, session: {}", user, session1);

                session1.getTransaction().commit();
            }
            log.warn("User entity is in detach state: {}", user);
        } catch (Exception e) {
            log.error("Exception: {}", e);
            throw e;
        }
    }
}
