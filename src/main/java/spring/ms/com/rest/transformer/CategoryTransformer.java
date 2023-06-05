package spring.ms.com.rest.transformer;

import spring.ms.com.data.entity.Category;
import spring.ms.com.rest.request.CategoryRequest;
import spring.ms.com.rest.response.CategoryResponse;

public class CategoryTransformer {

    public static CategoryResponse toCategoryResponse(Category category){
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(category.getId());
        categoryResponse.setName(category.getName());

        return categoryResponse;
    }

    public static Category toCategoryEntity(CategoryRequest categoryRequest){
        Category category = new Category();
        category.setName(categoryRequest.getName());

        return category;
    }

    public static Category toCategoryEntity(CategoryResponse categoryResponse){
        Category category = new Category();
        category.setId(categoryResponse.getId());
        category.setName(categoryResponse.getName());

        return category;
    }
}
