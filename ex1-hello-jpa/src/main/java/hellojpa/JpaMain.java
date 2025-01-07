package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            // JPQL
//            String jpql = "select m From Member m where m.username like '%kim%'";
//            List<Member> resultList = em.createQuery(jpql, Member.class).getResultList();

            // Criteria => 동적 쿼리에 유리 (유지보수가 어려우므로 실무에서 사용 x)
//            CriteriaBuilder cb = em.getCriteriaBuilder();
//            CriteriaQuery<Member> query = cb.createQuery(Member.class);
//            Root<Member> m = query.from(Member.class);
//            CriteriaQuery<Member> cq = query.select(m).where(cb.equal(m.get("username"), "kim"));
//            List<Member> resultList = em.createQuery(cq).getResultList();

            // Native Query ; native 쿼리를 날리기 전에 commit을 함. (jdbc, mybatis를 사용하는 경우 강제로 flush 해야함.)
            List<Member> resultList = em.createNativeQuery("select member_id, city, street, zipcode, username from member where username = 'kim'", Member.class).getResultList();


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
