package hellojpa;

import org.hibernate.Hibernate;

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

            Team teamA = new Team();
            teamA.setName("team1");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("team2");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setTeam(teamB);
            em.persist(member2);

            em.flush();
            em.clear();

            // ===== getReference 지연로딩을 사용한다.(프록시 객체 반환)  ======
//            Member findMember = em.find(Member.class, 1L); // 실제 엔티티 조회(즉시 조회)
//            Member findMember = em.getReference(Member.class, member1.getId()); // 프록시 엔티티 객체 조회
//            System.out.println("findMember = " + findMember.getClass());
//            System.out.println("findMember.id = " + findMember.getId());
//            System.out.println("findMember.username = " + findMember.getUsername()); // 이 시점에 쿼리를 날림.

            // ===== 프록시 객체는 엔티티 클래스를 상속한다. 그러므로 타입 비교시 instanceof로 비교해야함. ======
//            Member m1 = em.find(Member.class, member1.getId());
//            Member m2 = em.getReference(Member.class, member2.getId());
//
//            System.out.println("m1 == m2: " + (m1 == m2)); // 프록시 객체 엔티티 타입 체크시 == 말고 instanceof 를 사용해야함.
//            System.out.println("m1 == m2: " + (m2 instanceof Member));

            // ===== 영속 상태의 엔티티는 getReference 조회하면 프록시가 아닌 실제 엔티티 객체를 조회한다. ======
//            Member findMember = em.find(Member.class, member1.getId());
//            System.out.println("findMember = " + findMember.getClass()); // class hellojpa.Member
//
//            Member reference = em.getReference(Member.class, member1.getId());
//            System.out.println("reference = " + reference.getClass()); // class hellojpa.Member

            // ===== 준영속 상태를 초기화하면 LazyInitializationException 발생 =====
//            Member refMember = em.getReference(Member.class, member1.getId());
//            System.out.println("refMember = " + refMember.getClass());
//
//            em.detach(refMember);
//
//            System.out.println("refMember = " + refMember.getUsername()); // LazyInitializationException


            // ===== 프록시 관련 메서드 ======
//            Member refMember = em.getReference(Member.class, member1.getId());
//            System.out.println("refMember.getUsername() = " + refMember.getUsername());
//            System.out.println("isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(refMember)); // 프록시 인스턴스 초기화 여부
//
//            System.out.println("refMember = " + refMember.getClass()); // 프록시 클래스 확인 방법
//
//            Hibernate.initialize(refMember); // 강제 초기화

            // ===== 연관관계 지연로딩 =====
//            Member m = em.find(Member.class, member1.getId());
//            System.out.println("m = " + m.getTeam().getClass());
//            System.out.println("m.getTeam().getName() = " + m.getTeam().getName()); // 이 시점에 Team 조회

            List<Member> members = em.createQuery("select m from Member m", Member.class) // 즉시로딩에서 JPQL 사용시 N+1 문제 발생(Team 만큼 추가 쿼리) fetch join이나 엔티티 그래프로 기능으로 n+1 문제 해결 가능
                    .getResultList();

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
