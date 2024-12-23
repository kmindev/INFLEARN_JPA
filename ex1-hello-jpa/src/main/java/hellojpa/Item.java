package hellojpa;

import javax.persistence.*;

@DiscriminatorColumn // DTYPE(자식 타입을 구분하는 컬럼 생성)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // DB 슈퍼타입, 서브타입 관계
@Entity
public class Item {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private int price;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
