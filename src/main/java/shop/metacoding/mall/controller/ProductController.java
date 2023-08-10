package shop.metacoding.mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import shop.metacoding.mall.model.Product;
import shop.metacoding.mall.model.ProductRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ProductController {

    @PostMapping("/product/delete")
    public String delete(int id) {
        productRepository.deleteById(id);
        return "redirect:/"; // request가방에 아무것도 없으니까 바로 return "home";하면 안된다
        // 그렇다고 request를 다시 만드는건 코드 중복이니 안됨
        // sentRedirect하는법 : 2가지 중 return "redirect:/"; 사용한 것
    }

//    @PostMapping("/product/delete")
//    public String delete22(int id, HttpServletRequest request) {
//        productRepository.deleteById(id);
//        // session : 서버측 저장소
//        // 그냥 request하면 첫번째와 두번째가 다른 request이기 때문에 session에 저장하면 두번째 request때도 쓸 수 있다.
//        // session : 관련된 브라우저 다 끄면 사라짐
//        HttpSession session = request.getSession();
//        session.setAttribute("info", "삭제잘됐음");
//        return "redirect:/"; // request가방에 아무것도 없으니까 바로 return "home";하면 안된다
//        // 그렇다고 request를 다시 만드는건 코드 중복이니 안됨
//        // sentRedirect하는법 : 2가지 중 return "redirect:/"; 사용한 것
//    }

    @PostMapping("/product/update")
    public String update(int id, String name, int price, int qty) {
        productRepository.updateById(id, name, price, qty);
        return "redirect:/";
    }


    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/product/{id}")
    public String detail(@PathVariable int id, HttpServletRequest request) {
        //System.out.println("id: " + id);
        //Product product = productRepository.findById(id);
        //System.out.println(product.getId());
        //System.out.println(product.getName());
        //System.out.println(product.getPrice());
        //System.out.println(product.getQty());
        Product product = productRepository.detailProduct(id);
        request.setAttribute("product", product);

        return "detail";
    }

    @GetMapping("/")
    public String home(HttpServletRequest request) {
        List<Product> productList = productRepository.findAll();
        request.setAttribute("productList", productList); // 중괄호안의 productList : 키값 / 오른쪽 productList : value
        return "home";
    }

    @GetMapping("/write")
    public String writePage() {
        return "write";
    }

    @PostMapping("/product")
    public String write(String name, int price, int qty) {
        System.out.println("name: " + name);
        System.out.println("price: " + price);
        System.out.println("qty: " + qty);

        productRepository.save(name, price, qty);
        return "redirect:/"; // 재호출
    }
    // HttpServletRequest request쓰는게 정석(톰켓아! 하고 부르는 것)
    // 요즘은 매개변수에 key를 넣음 = 개편함
    // request : 가방

}
