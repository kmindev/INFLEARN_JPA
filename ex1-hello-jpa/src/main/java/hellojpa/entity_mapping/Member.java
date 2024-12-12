package hellojpa.entity_mapping;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

/*
    create table Member (
       id bigint not null,
        name varchar(255),
        age integer,
        createdDate timestamp,
        description clob,
        lastModifiedDate timestamp,
        roleType varchar(255),
        primary key (id)
    )
 */
//@Table(name = "MBR") // 엔티티에 매팡할 테이블 지정
@Entity // Jpa에서 관리할 엔티티
public class Member {
    @Id
    private Long id;

//    @Column(unique = true, length = 10) // 제약 조건(DDL 자동 생성에만 적용, JPA 실행로직에는 영향을 주지 않음)

    @Column(name = "name") // 컬럼 매핑
    private String name;

    private Integer age;

    @Enumerated(EnumType.STRING) // enum 타입 매핑
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP) // 날짜 타입 매핑
    private Date createdDate;

    private LocalDate lastModifiedDate; // 자바 8부터 LocalDate, LocalDateTime을 사용하면 @Temporal 생략 가능.

    @Lob // BLOB, CLOB 매핑(긴문자열)
    private String description;

    @Transient // 매핑 무시
    private String temp;

    public Member() { // jpa는 기본 생성자가 필요함(Public, Protected)
    }

}
