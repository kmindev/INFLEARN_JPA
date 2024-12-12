package hellojpa.persistence_context;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // Jpa에서 관리할 엔티티
public class Member {
    @Id
    private Long id;
    private String name;

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
