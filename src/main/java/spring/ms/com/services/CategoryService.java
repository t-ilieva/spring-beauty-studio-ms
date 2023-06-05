package spring.ms.com.services;

import org.springframework.stereotype.Service;
import spring.ms.com.data.repository.CategoryRepository;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
}
