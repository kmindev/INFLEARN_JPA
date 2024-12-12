package hellojpa.persistence_context;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaCrud {
    public static void main(String[] args) {
        // resources/META-INF/persistence.xml에 unit name
        // 애플리케이션 당 하나만 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager(); // db Connection과 비슷한 개념 

        // jpa 트랜잭션 범위내에서 실행해야 함. 그러므로 트랜잭션을 생성해서 범위를 설정해야 한다.
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member findMember = em.find(Member.class, 1L); // find

//             Member member = new Member();  // create
//             member.setId(1L);
//             member.setName("HelloA");
//             em.persist(member);

//             em.remove(findMember); // delete

//            findMember.setName("HelloJPA"); // update

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close(); // 애플리케이션 종료때 EntityManagerFactory 닫아준다.
    }

}