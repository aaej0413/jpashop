package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class) // junit 실행할 때 스프링이랑 같이 엮어서 실행 할래~!
@SpringBootTest // 스프링 부트 띄운 상태에서 테스트 실행 할래~!
@Transactional // 테스트에서 사용하면 테스트 끝나면 다 롤백 해줌~!
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    public void 회원가입() throws Exception {
        //given 이런 상황이 주어졌을 때,
        Member member = new Member();
        member.setName("응두");

        //when 이걸 실행하면,
        Long saveId = memberService.join(member);

        //then 이런 결과가 나와야 돼!
        em.flush();
        assertEquals(member,memberRepository.findOne(saveId));

    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //given 이런 상황이 주어졌을 때,
        Member member1 = new Member();
        member1.setName("ahn1");

        Member member2 = new Member();
        member2.setName("ahn1");

        //when 이걸 실행하면,
        memberService.join(member1);
        memberService.join(member2);
//        try {
//            memberService.join(member2); // 예외가 발생해야 한다!!
//        }catch (IllegalStateException e) {
//            return;
//        }

        //then 이런 결과가 나와야 돼!
        fail("예외가 발생해야 한다.");

        }

}