package spring.ms.com.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.ms.com.data.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
