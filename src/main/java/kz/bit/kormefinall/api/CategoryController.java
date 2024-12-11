package kz.bit.kormefinall.api;

import kz.bit.kormefinall.dto.CategoryDTO;
import kz.bit.kormefinall.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/category")
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping
    public List<CategoryDTO> getcategory(){
        return categoryService.getCategories();
    }

    @GetMapping(value = "{id}")
    public CategoryDTO getcategory(@PathVariable(name = "id") Long id){
        return categoryService.getCategory(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public CategoryDTO addCategory(@RequestBody CategoryDTO category){
        return categoryService.addCategory(category);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public CategoryDTO updateCategory(@RequestBody CategoryDTO category){
        return categoryService.updateCategory(category);
    }

    @DeleteMapping(value = "{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void deleteCategory(@PathVariable(name = "id") Long id){
        categoryService.deleteCategory(id);
    }
}