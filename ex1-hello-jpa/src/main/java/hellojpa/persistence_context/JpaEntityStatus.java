package hellojpa.persistence_context;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaEntityStatus {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // 비영속 상태(영속성 컨텍스트와 관계가 없는 대상)
            Member member = new Member(100L, "HelloJPA");

            // 영속 상태(영속성 컨텍스트 관리 대상)
            System.out.println("=== Before ===");
            em.persist(member);
//            em.detach(member); // 준영속(영속성 컨텍스트에 있다가 분리된 상태)
            System.out.println("=== After ===");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

}
