package shop.metacoding.mall.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;

import java.util.List;

// @Rollback(false) // 메서드 종료시마다 롤백안하기
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 진짜 db로 테스트
@Import({
        ProductRepository.class,
        SellerRepository.class
}) // Repository, entityManager 메모리에 뜸
@DataJpaTest // 톰켓 -> DS -> Controller -> Repository -> DB 여기선 (R->DB테스트)
public class ProductRepositoryTest {

    // objectMapping은 entity만 해준다(DTO는 안해준다) -> Repository의 쿼리문에서 ,Product.class 안된다는뜻
    // 하지만 DTO는 테이블이 아니기 때문에 @Entity를 붙일 수 없다

    @Test
    public void findByIdDTO_test() {
        // given
        productRepository.save("바나나", 5000, 50);

        // when
        ProductDTO productDTO = productRepository.findByIdDTO(1);

        // then
        System.out.println(productDTO.getId());
        System.out.println(productDTO.getName());
        System.out.println(productDTO.getPrice());
        System.out.println(productDTO.getQty());
        System.out.println(productDTO.getDes());

    }

    @Autowired
    private  ProductRepository productRepository;

    @Test
    public void save_test() {
        productRepository.save("바나나", 5000, 50);
    }

    @Test
    public void saveWithFK_test() {
        productRepository.saveWithFK("바나나", 5000, 50, 1);
    }

    @Test
    public void findById_test() {
        // given (테스트를 하기 위해서 필요한 데이터 만들기)
        productRepository.save("바나나", 5000, 50);

        // when (테스트 진행)
        Product product = productRepository.findById(1);

        // then (테스트 확인)
        System.out.println(product.getId());
        System.out.println(product.getName());
        System.out.println(product.getPrice());
        System.out.println(product.getQty());
    }

    @Test
    public void findByIdJoinSeller_test() {
        // given (테스트를 하기 위해서 필요한 데이터 만들기)
        productRepository.saveWithFK("바나나", 5000, 50, 1);

        // when (테스트 진행)
        Product product = productRepository.findByIdJoinSeller(1);

        // then (테스트 확인)
        System.out.println(product.getId());
        System.out.println(product.getName());
        System.out.println(product.getPrice());
        System.out.println(product.getQty());
    }

    @Test
    public void findAll_test() {
        // given
        productRepository.save("바나나", 5000, 50);
        productRepository.save("딸기", 5000, 50);

        // when
        List<Product> productList = productRepository.findAll();

        // then
        for (Product product : productList) {
            System.out.println("======================");
            System.out.println(product.getId());
            System.out.println(product.getName());
            System.out.println(product.getPrice());
            System.out.println(product.getQty());

        }
        
    }
}
