package an.kondratev.security_jwt.service;

import an.kondratev.security_jwt.model.Product;
import an.kondratev.security_jwt.repository.ProductsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService implements ProductServiceInterface {
    private final ProductsRepository repository;

    @Override
    public Optional<Product> getProduct(Long productId) {

        return repository.findById(productId);
    }

    @Override
    public List<Product> getAllProducts() {

        return repository.findAll();
    }

    @Override
    public Product createProduct(Product product) {
        return repository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {

        return repository.save(product);
    }
}
