package shop.metacoding.mall.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

@Repository
public class SellerRepository {

    @Autowired // DI. IoC컨테이너에 있는 거 꺼내서 사용(다른 자바파일의 @Autowired는 공유된 IoC컨테이너 사용)
    private EntityManager em; // db에 접근할 수 있음

    @Transactional
    public void save(String name, String email) {
        Query query = em.createNativeQuery("insert into product_tb(name, email) values(:name, :email)");
        query.setParameter("name", name);
        query.setParameter("email", email);
        query.executeUpdate(); // 전송
    }
}
