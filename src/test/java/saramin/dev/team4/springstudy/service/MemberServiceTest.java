package saramin.dev.team4.springstudy.service;

import javax.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import saramin.dev.team4.springstudy.domain.Member;
import saramin.dev.team4.springstudy.repository.MemberRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

//RunWith(SpringRunner.class)    //Junit4
@ExtendWith(SpringExtension.class)  //Junit5
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
    @DisplayName("Sign up")
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long savedId = memberService.join(member);

        //then
        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test()
    @DisplayName("duplicate member exception")
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        Throwable exception = assertThrows(IllegalStateException.class, () -> {
            memberService.join(member1);
            memberService.join(member2); //예외가 발생해야 한다!!!
        });

        //then
        assertEquals("이미 존재하는 회원입니다.", exception.getMessage());
    }
}