package saramin.dev.team4.springstudy.domain.item;

import lombok.Getter;
import lombok.Setter;
import saramin.dev.team4.springstudy.domain.Category;
import saramin.dev.team4.springstudy.exception.NotEnoughStockException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Item {
    @Id
    @GeneratedValue
    @Column(name="item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) throws NotEnoughStockException {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0 ) {
            throw new NotEnoughStockException("재고가 부족합니다.");
        }
        this.stockQuantity = restStock;
    }
}
