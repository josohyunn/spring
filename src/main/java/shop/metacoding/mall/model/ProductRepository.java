package shop.metacoding.mall.model;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository // 컴퍼넌트 스캔(직접 new안해도됨). IoC컨테이너에 메모리 띄움
public class ProductRepository { // Repository : DAO와 같은 역할(DAO는 db에만 접근하지만 repository는 다른 외부에서도 접근)

    @Autowired // DI. IoC컨테이너에 있는 거 꺼내서 사용(다른 자바파일의 @Autowired는 공유된 IoC컨테이너 사용)
    private EntityManager em; // db에 접근할 수 있음

    public Product findByIdJoinSeller(int id) { // 혼자 실습해보기
        Query query  = em.createNativeQuery("select * from product_tb inner join seller_tb on product_tb.seller_id = seller_tb.id", Product.class);
        Product product = (Product) query.getSingleResult();
        return product;

    }

    // 조회하는건 @Transactional 안붙여도된다
    public ProductDTO findByIdDTO(int id) {
        Query query  = em.createNativeQuery("select id, name, price, qty, '설명' des from product_tb where id = :id");
        query.setParameter("id", id);

        // 위에 쿼리문에 ProductDTO.class 못하니까 의존성 추가해서 mapper사용
        JpaResultMapper mapper = new JpaResultMapper();
        ProductDTO productDTO = mapper.uniqueResult(query, ProductDTO.class); //여러건이면 unique말고 list사용

        return productDTO;
    }

    @Transactional
    public void save(String name, int price, int qty) {
        Query query = em.createNativeQuery("insert into product_tb(name, price, qty) values(:name, :price, :qty)");
        query.setParameter("name", name);
        query.setParameter("price", price);
        query.setParameter("qty", qty);
        query.executeUpdate(); // 전송
    }

    @Transactional
    public void saveWithFK(String name, int price, int qty, int seller_id) {
        Query query = em.createNativeQuery("insert into product_tb(name, price, qty, seller_id) values(:name, :price, :qty, :seller_id)");
        query.setParameter("name", name);
        query.setParameter("price", price);
        query.setParameter("qty", qty);
        query.setParameter("seller_id", seller_id);
        query.executeUpdate(); // 전송
    }

    public List<Product> findAll() {
        Query query = em.createNativeQuery("select * from product_tb", Product.class);
        List<Product> productList = query.getResultList(); //executeQuery, executeUpdate랑 똑같은 개념
        return productList;
    }

    public Product findById(int id) {
        Query query = em.createNativeQuery("select * from product_tb where id = :id", Product.class); // Product.class : 매핑하는 것(Entity만 가능)
        query.setParameter("id", id);
        Product product = (Product) query.getSingleResult();
        return product;
    }

    public Product findById2(int id) { // findById랑 같은 거지만 불편함
        Query query = em.createNativeQuery("select * from product_tb where id = :id");
        query.setParameter("id", id);
        // row가 1건
        // 1, 바나나, 1000, 50
        Object[] object = (Object[]) query.getSingleResult();
        int id2 = (int) object[0];
        String name2 = (String) object[1];
        int price2 = (int) object[2];
        int qty2 = (int) object[3];

        Product product = new Product();
        product.setId(id2);
        product.setName(name2);
        product.setPrice(price2);
        product.setQty(qty2);
        return product;
    }

    public Product detailProduct(int id) {
        Query query = em.createNativeQuery("select * from product_tb where id = :id", Product.class); // Product.class : 매핑하는 것
        query.setParameter("id", id);
        Product product = (Product) query.getSingleResult();
        return product;
    }

    // @Transactional 쓸 때 : insert, updatd, delete
    @Transactional
    public void deleteById(int id) {
        Query query = em.createNativeQuery("delete from product_tb where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Transactional
    public void updateById(int id, String name, int price, int qty) {
        Query query = em.createNativeQuery("update product_tb set id = :id, name = :name, price = :price, qty = :qty where id = :id");
        query.setParameter("id", id);
        query.setParameter("name", name);
        query.setParameter("price", price);
        query.setParameter("qty", qty);
        query.executeUpdate();

    }
}
