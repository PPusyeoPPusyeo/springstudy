package saramin.dev.team4.springstudy.repository;

import lombok.Getter;
import lombok.Setter;
import saramin.dev.team4.springstudy.domain.OrderStatus;


@Getter @Setter
public class OrderSearch {
    private OrderStatus orderStatus;
    private String memberName;
}
