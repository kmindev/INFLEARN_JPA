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
//            Address address = new Address("city", "street", "100");
//
//            Member member1 = new Member();
//            member1.setUsername("hello");
//            member1.setHomeAddress(address);
//            member1.setWorkPeriod(new Period());
//
//            Member member2 = new Member();
//            member2.setUsername("hello1");
//            member2.setHomeAddress(address);
//            member2.setWorkPeriod(new Period());

            // 공유 객체를 사용하므로 문제 발생 => member1 을 수정했는데 member2 가 변경됨.
            // 그러므로 불편 객체를 사용(생성자르로 통해서만 값을 주입하고, setter 메서드 금지)
//            member1.getHomeAddress().setCity("new city");
//
//            em.persist(member1);
//            em.persist(member2);

            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(new Address("city1", "street", "100000"));
            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            member.getAddressHisotry().add(new Address("old1", "street1", "1000"));
            member.getAddressHisotry().add(new Address("old2", "street2", "2000"));

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("========== start ============");
            Member findMember = em.find(Member.class, member.getId());

            // 값타입 컬렉션은 지연로딩 전략을 사용한다.
            for (String food : findMember.getFavoriteFoods()) { // 지연 로딩
                System.out.println(food);
            }

            for (Address address : findMember.getAddressHisotry()) { // 지연 로딩
                System.out.println(address.getCity());
            }

            Address oldAddress = findMember.getHomeAddress();
            findMember.setHomeAddress(new Address("new city", oldAddress.getStreet(), oldAddress.getZipcode())); // 임베디드 타입 업데이트

            // 값 타입 컬렉션 변경(primitive type)
            // name으로 delete 쿼리
            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");

            // 값 타입 컬렉션 변경(커스텀 객체)
            // 해당 memeber의 모든 히스토리를 삭제하고, 다시 insert 한다.(delete: 1회, insert: N회)
            findMember.getAddressHisotry().remove(new Address("old1", "street1", "1000")); // equals&hashcode 구현 필요
            findMember.getAddressHisotry().add(new Address("new city1", "street1", "1000"));

            System.out.println("========== end ============");
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
