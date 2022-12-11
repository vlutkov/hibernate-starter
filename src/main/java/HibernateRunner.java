import lombok.extern.slf4j.Slf4j;
import model.entity.Chat;
import model.entity.Company;
import model.entity.PersonalInfo;
import model.entity.User;
import model.entity.UsersChat;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;

@Slf4j
public class HibernateRunner {

    public static void main(String[] args) {
        UsersChat usersChat = UsersChat.builder()
                .createdBy("admin")
                .build();


        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {

            Session session1 = sessionFactory.openSession();
            try (session1) {
                session1.beginTransaction();

//                User user1 = session1.get(User.class, 1L);
//                User user3 = session1.get(User.class, 3L);

                User user3 = session1.get(User.class, 3L);

                Chat chat2 = session1.get(Chat.class, 3L);

                usersChat.setUser(user3);
                usersChat.setChat(chat2);

                session1.save(usersChat);

//                session1.save(user);

//                Company company1 = session1.get(Company.class, 1L);
                System.out.println("");

                session1.getTransaction().commit();
            }
        }
    }
}
