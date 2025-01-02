package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Address address = new Address("city", "street", "100");

            Member member1 = new Member();
            member1.setUsername("hello");
            member1.setHomeAddress(address);
            member1.setWorkPeriod(new Period());

            Member member2 = new Member();
            member2.setUsername("hello1");
            member2.setHomeAddress(address);
            member2.setWorkPeriod(new Period());

            // 공유 객체를 사용하므로 문제 발생 => member1 을 수정했는데 member2 가 변경됨.
            // 그러므로 불편 객체를 사용(생성자르로 통해서만 값을 주입하고, setter 메서드 금지)
            member1.getHomeAddress().setCity("new city");

            em.persist(member1);
            em.persist(member2);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }

}
