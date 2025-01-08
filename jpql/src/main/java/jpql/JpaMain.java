package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpql");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

            // TypeQuery
            TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);
            List<Member> resultList = query1.getResultList(); // 결과가 여러개
            Member singleResult = query1.getSingleResult(); // 결과가 하나

            // 파라미터 바인딩
            TypedQuery<Member> query2 = em.createQuery("select m from Member m where username = :username", Member.class);
            query2.setParameter("username", "member1");
            Member singleResult1 = query2.getSingleResult();
            System.out.println("singleResult1.getUsername() = " + singleResult1.getUsername());

            // 프로젝션
            em.createQuery("select m from Member m", Member.class).getResultList(); // 엔티티 프로젝션
            em.createQuery("select m.team from Member m", Team.class).getResultList(); // 엔티티 프로젝션(join 쿼리) => 별도의 join쿼리를 제공하기에 권장 x
            em.createQuery("select o.address from Orders o", Address.class).getResultList(); // 임베디드 타입 프로젝션
            em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class).getResultList(); // 스칼라 타입 프로젝션


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
