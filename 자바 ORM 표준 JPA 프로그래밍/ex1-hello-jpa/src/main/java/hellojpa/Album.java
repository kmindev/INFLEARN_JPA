package hellojpa;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@DiscriminatorValue("M") // 슈퍼 타입을 DTYPE 컬럼의 Value를 지정
@Entity
public class Album extends Item {
    private String artist;
}
