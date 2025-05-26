package jpabook.jpashop.repository;


import jpabook.jpashop.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

//    @Test
//    @Transactional // Test 케이스에서는 기본적으로 롤백함
//    @Rollback(value = false) // 롤백 비활성화
//    void memberTest() {
//        // Given
//        Member member = new Member();
//        member.setUsername("memberA");
//
//        // When
//        Long savedId = memberRepository.save(member);
//        Member findMember = memberRepository.findOne(savedId);
//
//        // Then
//        assertThat(findMember.getId()).isEqualTo(member.getId());
//        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
//        assertThat(findMember).isEqualTo(member);
//    }


}