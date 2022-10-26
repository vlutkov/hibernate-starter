import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import converter.BirthdayConverter;
import enums.Role;
import model.entity.Birthday;
import model.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import type.JsonType;

import java.time.LocalDate;

public class HibernateRunner {

    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.addAttributeConverter(new BirthdayConverter());
        configuration.registerTypeOverride(new JsonBinaryType());
        configuration.configure();

        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            session.beginTransaction();

            User user = User.builder()
                    .username("ivan3@gmail.com")
                    .firstname("Ivan")
                    .lastname("Ivanov")
                    .info("""
                            {
                                "name": "Ivan",
                                "id": 25
                            }
                            """)
                    .birthDate(new Birthday(LocalDate.of(2000, 1, 19)))
                    .role(Role.ADMIN)
                    .build();

            System.out.println(session.isDirty());
            User user1 = session.get(User.class, "ivan1@gmail.com");
            user1.setLastname("Ivanov");
            System.out.println(session.isDirty());
//            session.flush();
//            session.save(user);

            session.getTransaction().commit();
        }
    }
}
