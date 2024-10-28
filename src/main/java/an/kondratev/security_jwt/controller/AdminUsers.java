package an.kondratev.security_jwt.controller;

import an.kondratev.security_jwt.dto.UserDTO;
import an.kondratev.security_jwt.model.Product;
import an.kondratev.security_jwt.service.ProductServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class AdminUsers {
    private ProductServiceInterface productService;

    @GetMapping("/public/products")
    public ResponseEntity<Object> getProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping("/super_admin/save_product")
    public ResponseEntity<Object> signUpProduct(@RequestBody UserDTO productRequest) {
        Product saveProduct = new Product();
        saveProduct.setName(productRequest.getName());
        return ResponseEntity.ok(productService.createProduct(saveProduct));
    }

    @PutMapping("/moderator/updateProduct")
    public ResponseEntity<Object> updateProduct(@RequestBody UserDTO productRequest) {
        Product updateProduct = new Product();
        updateProduct.setName(productRequest.getName());
        return ResponseEntity.ok(productService.updateProduct(updateProduct));
    }

    @GetMapping("user/alone")
    public ResponseEntity<Object> userAlone() {
        return ResponseEntity.ok("Available to user only");
    }

}
