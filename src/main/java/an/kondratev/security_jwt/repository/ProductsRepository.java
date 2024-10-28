package an.kondratev.security_jwt.repository;

import an.kondratev.security_jwt.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Long> {
    Product findById(long id);
}
