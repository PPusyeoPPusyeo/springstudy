package saramin.dev.team4.springstudy.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import saramin.dev.team4.springstudy.domain.Address;
import saramin.dev.team4.springstudy.domain.Member;
import saramin.dev.team4.springstudy.domain.Order;
import saramin.dev.team4.springstudy.domain.OrderStatus;
import saramin.dev.team4.springstudy.domain.item.Book;
import saramin.dev.team4.springstudy.domain.item.Item;
import saramin.dev.team4.springstudy.exception.NotEnoughStockException;
import saramin.dev.team4.springstudy.repository.OrderRepository;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ExtendWith(SpringExtension.class)
public class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    @DisplayName("상품주문")
    public void 상품주문() {
        //given
        Member member = createMember();
        Book book = createBook();
        int orderCount = 2;

        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals( OrderStatus.ORDER, getOrder.getStatus(),"상품 주문시 상태는 ORDER");
        assertEquals( 1, getOrder.getOrderItems().size(),"주문한 상품 종류 수가 정확해야 한다.");
        assertEquals( 1000 * orderCount, getOrder.getTotalPrice(),"주문 가격은 가격 * 수량이다.");
        assertEquals( 8, book.getStockQuantity(),"주문 수량만큼 재고가 줄어야 한다.");
    }

    @Test
    public void 상품주문_재고수량초과() throws  Exception {
        //given
        Member member = createMember();
        Item item =createBook();
        int orderCount = 11;

        //then
        assertThrows(NotEnoughStockException.class, () -> {
            orderService.order(member.getId(),item.getId(),orderCount);
        });
    }

    @Test
    public void 주문취소() {
        //given
        Member member = createMember();
        Item item =createBook();
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.CANCEL, getOrder.getStatus(),"주문 취소시 상태는 CANCEL 이다.");
        assertEquals(10, item.getStockQuantity(), "주문이 취소된 상품은 그만큼 재고가 증가해야 한다.");
    }

    private Book createBook() {
        Book book = new Book();
        book.setName("JPA");
        book.setPrice(1000);
        book.setStockQuantity(10);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("홍길동");
        member.setAddress(new Address("서울","강가","123-123"));
        em.persist(member);
        return member;
    }
}
