package an.kondratev.security_jwt.service;

import an.kondratev.security_jwt.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductServiceInterface {
    Optional<Product> getProduct(Long productId);

    List<Product> getAllProducts();

    Product createProduct(Product product);

    Product updateProduct(Product product);
}
