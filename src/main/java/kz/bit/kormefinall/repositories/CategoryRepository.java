package kz.bit.kormefinall.repositories;

import jakarta.transaction.Transactional;
import kz.bit.kormefinall.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Category, Long> {
}