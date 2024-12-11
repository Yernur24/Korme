package kz.bit.kormefinall.services;

import kz.bit.kormefinall.dto.CategoryDTO;
import kz.bit.kormefinall.mapper.CategoryMapper;
import kz.bit.kormefinall.models.Category;
import kz.bit.kormefinall.models.Product;
import kz.bit.kormefinall.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    private final CategoryRepository categoryRepository;

    public List<Category> allCategories() {
        return categoryRepository.findAll();
    }

    public List<CategoryDTO> getCategories(){
        return categoryMapper.toDtoList(categoryRepository.findAll());
    }

    public CategoryDTO getCategory(Long id){
        return categoryMapper.toDto(categoryRepository.findById(id).orElse(new Category()));
    }
    public CategoryDTO addCategory(CategoryDTO category){
        return categoryMapper.toDto(categoryRepository.save(categoryMapper.toModel(category)));
    }
    public CategoryDTO updateCategory(CategoryDTO category){
        return categoryMapper.toDto(categoryRepository.save(categoryMapper.toModel(category)));
    }
    public void deleteCategory(Long id){
        categoryRepository.deleteById(id);
    }

    public Category getCategoryById(Long id){
        return categoryRepository.findById(id).orElse(null);
    }
}