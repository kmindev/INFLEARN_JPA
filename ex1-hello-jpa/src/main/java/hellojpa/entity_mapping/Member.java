package hellojpa.entity_mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//@Table(name = "MBR") // 엔티티에 매팡할 테이블 지정
@Entity // Jpa에서 관리할 엔티티
public class Member {
    @Id
    private Long id;

    @Column(unique = true, length = 10) // 제약 조건(DDL 자동 생성에만 적용, JPA 실행로직에는 영향을 주지 않음)
    private String name;
    private int age;

    public Member() { // jpa는 기본 생성자가 필요함(Public, Protected)
    }

    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
