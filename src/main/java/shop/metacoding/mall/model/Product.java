package shop.metacoding.mall.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Table(name="product_tb")
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer price;
    private Integer qty;

    // Object로 fk키 추가하는 법(이거씀)
    @ManyToOne // 관계) Product : N / Seller : 1
    private Seller seller;

    // 변수로 fk 추가하는법
    // private Integer sellerId;
}
