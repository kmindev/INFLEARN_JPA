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
            Team teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀B");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.changeTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.changeTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.changeTeam(teamB);
            em.persist(member3);

            em.flush();
            em.clear();

            // 경로 표현식(상태필드, 묵시적, 명시적 조인)
//            List<String> resultList = em.createQuery("select m.username from Member m", String.class).getResultList(); // 상태 필드
//            List<Team> resultList = em.createQuery("select m.team from Member m", Team.class).getResultList(); // 단일 값 연관 경로(묵시적 내부 조인 발생) 탐색 o
//            Collection resultList = em.createQuery("select t.members from Team t", Collection.class).getResultList(); // 컬렉션 값 연관 경로(묵시적 내부 조인 발생) 탐색 x => 명시적 조인으로 해결

            // 패치 조인 select m from Member m

            // ManyToOne 관계에서의 패치 조인
            List<Member> result1 = em.createQuery("select m from Member m join fetch m.team", Member.class)
                    .getResultList();

            // N + 1 => 연관된 Team 정보가 1차 캐시에 없으므로 필요한 Team 만큼 쿼리를 실행.(패치 조인을 사용하면 해결)
            for (Member member : result1) {
                System.out.println("member = " + member.getUsername() + " ," + member.getTeam().getName());
            }

            em.flush();
            em.clear();

            // OneToMany(컬렉션) 관계에서의 패치 조인
            List<Team> result2 = em.createQuery("select distinct t from Team t join fetch t.members", Team.class)
                    .getResultList();

            // join 하면서 중복된 Team을 가져옴. => distinct로 중복 제거할 수 있음.
            for (Team team : result2) {
                System.out.println("team = " + team.getName() + " Members : " + team.getMembers().size());
            }


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
