package saramin.dev.team4.springstudy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import saramin.dev.team4.querydslstudy.domain.Team;
import saramin.dev.team4.springstudy.domain.Member;
import saramin.dev.team4.springstudy.domain.Order;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

//RunWith(SpringRunner.class)    //Junit4
@ExtendWith(SpringExtension.class)  //Junit5
@SpringBootTest
class SpringStudyApplicationTests {

    @Test
    void contextLoads() {
    }

}
