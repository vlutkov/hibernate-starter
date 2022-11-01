import lombok.extern.slf4j.Slf4j;
import model.entity.Company;
import model.entity.PersonalInfo;
import model.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;

@Slf4j
public class HibernateRunner {

    public static void main(String[] args) {
        Company company = Company.builder()
                .name("Google2")
                .build();
        User user = User.builder()
                .username("ivan@gmail.com")
                .personalInfo(PersonalInfo.builder()
                        .firstname("Ivan")
                        .lastname("Ivanov")
                        .build())
                .company(company)
                .build();

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {

            Session session1 = sessionFactory.openSession();
            try (session1) {
                session1.beginTransaction();

//                session1.save(user);

                Company company1 = session1.get(Company.class, 1L);
                System.out.println("");

                session1.getTransaction().commit();
            }
        }
    }
}
