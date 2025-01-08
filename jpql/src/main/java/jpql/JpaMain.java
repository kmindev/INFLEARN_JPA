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
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            member.changeTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            // TypeQuery
            TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);
            List<Member> resultList = query1.getResultList(); // 결과가 여러개
            Member singleResult = query1.getSingleResult(); // 결과가 하나
            em.clear();

            // 파라미터 바인딩
            TypedQuery<Member> query2 = em.createQuery("select m from Member m where username = :username", Member.class);
            query2.setParameter("username", "member1");
            Member singleResult1 = query2.getSingleResult();
            System.out.println("singleResult1.getUsername() = " + singleResult1.getUsername());
            em.clear();

            // 프로젝션
            em.createQuery("select m from Member m", Member.class).getResultList(); // 엔티티 프로젝션
            em.createQuery("select m.team from Member m", Team.class).getResultList(); // 엔티티 프로젝션(join 쿼리) => 별도의 join쿼리를 제공하기에 권장 x
            em.createQuery("select o.address from Orders o", Address.class).getResultList(); // 임베디드 타입 프로젝션
            em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class).getResultList(); // 스칼라 타입 프로젝션
            em.clear();

            // 페이징
            List<Member> resultList1 = em.createQuery("select m from Member m order by m.age desc", Member.class)
                    .setFirstResult(0)
                    .setMaxResults(10)
                    .getResultList();

            System.out.println("resultList1.size() = " + resultList1.size());
            em.clear();

            // 조인
            List<Member> resultList2 = em.createQuery("select m from Member m inner join m.team t", Member.class) // inner 조인
                    .getResultList();
            em.clear();

            List<Member> resultList3 = em.createQuery("select m from Member m left outer join m.team t", Member.class) // outer 조인
                    .getResultList();
            em.clear();

            List<Member> resultList4 = em.createQuery("select m from Member m, Team t where m.username = t.name", Member.class) // 세타 조인
                    .getResultList();
            em.clear();

            List<Member> resultList5 = em.createQuery("select m from Member m left outer join m.team t on t.name = 'A'", Member.class) // 조인 대상 필터링(on)
                    .getResultList();
            em.clear();

            List<Member> resultList6 = em.createQuery("select m from Member m left join Team t on m.username = t.name", Member.class) // 연관 관계가 없는 엔티티 외부 조인
                    .getResultList();
            em.clear();


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
