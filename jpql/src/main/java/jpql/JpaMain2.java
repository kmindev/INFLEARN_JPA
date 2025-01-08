package jpql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Collection;
import java.util.List;

public class JpaMain2 {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpql");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.changeTeam(team);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.changeTeam(team);
            em.persist(member2);

            // 경로 표현식(상태필드, 묵시적, 명시적 조인)
//            List<String> resultList = em.createQuery("select m.username from Member m", String.class).getResultList(); // 상태 필드
//            List<Team> resultList = em.createQuery("select m.team from Member m", Team.class).getResultList(); // 단일 값 연관 경로(묵시적 내부 조인 발생) 탐색 o
            Collection resultList = em.createQuery("select t.members from Team t", Collection.class).getResultList(); // 컬렉션 값 연관 경로(묵시적 내부 조인 발생) 탐색 x => 명시적 조인으로 해결

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
