package saramin.dev.team4.springstudy.service;

import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import saramin.dev.team4.springstudy.domain.Delivery;
import saramin.dev.team4.springstudy.domain.Member;
import saramin.dev.team4.springstudy.domain.Order;
import saramin.dev.team4.springstudy.domain.OrderItem;
import saramin.dev.team4.springstudy.domain.item.Item;
import saramin.dev.team4.springstudy.repository.ItemRepository;
import saramin.dev.team4.springstudy.repository.MemberRepository;
import saramin.dev.team4.springstudy.repository.OrderRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        OrderItem orderItem = OrderItem.createOrderItem(item,item.getPrice(),count);

        Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository.save(order);

        return order.getId();
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }

}
