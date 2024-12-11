package kz.bit.kormefinall.repositories;

  import jakarta.transaction.Transactional;
  import kz.bit.kormefinall.dto.ProductDTO;
  import kz.bit.kormefinall.models.Category;
  import kz.bit.kormefinall.models.Product;
  import org.springframework.data.jpa.repository.EntityGraph;
  import org.springframework.data.jpa.repository.JpaRepository;
  import org.springframework.data.jpa.repository.Query;
  import org.springframework.data.repository.query.Param;
  import org.springframework.stereotype.Repository;

  import java.util.List;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Long> {
  List<Product> findByCategory(Category category);

}