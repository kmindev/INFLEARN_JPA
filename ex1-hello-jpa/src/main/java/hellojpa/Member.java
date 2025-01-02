package hellojpa;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(name = "username")
    private String username;

    // period
    @Embedded
    private Period workPeriod;

    // 집 주소
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name = "home_city")),
            @AttributeOverride(name = "street", column = @Column(name = "home_street")),
            @AttributeOverride(name = "zipcode", column = @Column(name = "home_zipcode"))
    })
    private Address homeAddress;

    // 회사 주소
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name = "work_city")),
            @AttributeOverride(name = "street", column = @Column(name = "work_street")),
            @AttributeOverride(name = "zipcode", column = @Column(name = "work_zipcode"))
    })
    private Address workAddress;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Period getWorkPeriod() {
        return workPeriod;
    }

    public void setWorkPeriod(Period workPeriod) {
        this.workPeriod = workPeriod;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }
}
