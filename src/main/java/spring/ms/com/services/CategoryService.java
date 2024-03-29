package spring.ms.com.services;

import org.springframework.stereotype.Service;
import spring.ms.com.data.entity.Category;
import spring.ms.com.data.repository.CategoryRepository;
import spring.ms.com.rest.request.CategoryRequest;
import spring.ms.com.rest.response.CategoryResponse;
import spring.ms.com.rest.transformer.CategoryTransformer;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResponse> getAll(){
        return categoryRepository
                .findAll()
                .stream()
                .map(CategoryTransformer::toCategoryResponse)
                .collect(Collectors.toList());
    }

    public Optional<CategoryResponse> getByName(String name){
        return categoryRepository
                .findByName(name)
                .map(CategoryTransformer::toCategoryResponse);
    }

    public Optional<CategoryResponse> getById(int id){
        return  categoryRepository
                .findById(id)
                .map(CategoryTransformer::toCategoryResponse);
    }

    public int createCategory(CategoryRequest categoryRequest){
        if(categoryRepository.findByName(categoryRequest.getName()).isEmpty()){
            Category category = CategoryTransformer
                    .toCategoryEntity(categoryRequest);
            return categoryRepository.save(category).getId();
        }else {
            return -1;
        }
    }
    public int editCategory(int id, CategoryRequest categoryRequest){
        String name = categoryRepository.findById(id).get().getName();

        if(categoryRepository.findByName(categoryRequest.getName()).isEmpty() ||
                categoryRepository.findByName(categoryRequest.getName()).get().getName().equals(name)){
            Category category = CategoryTransformer
                    .toCategoryEntity(categoryRequest);

            category.setId(id);
            return categoryRepository.save(category).getId();
        }else {
            return -1;
        }
    }

    public void deleteCategory(int id){
        categoryRepository.deleteById(id);
    }



}
