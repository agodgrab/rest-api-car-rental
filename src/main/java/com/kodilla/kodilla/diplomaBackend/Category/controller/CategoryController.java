package com.kodilla.kodilla.diplomaBackend.Category.controller;

import com.kodilla.kodilla.diplomaBackend.Category.domain.Category;
import com.kodilla.kodilla.diplomaBackend.Category.dto.CategoryDto;
import com.kodilla.kodilla.diplomaBackend.Category.mapper.CategoryMapper;
import com.kodilla.kodilla.diplomaBackend.Category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryMapper categoryMapper;

    @GetMapping(value ="/categories")
    public List<Category> showAllCategories(){
        return categoryService.getCategories();
    }

    @PostMapping(value="/category")
    public CategoryDto addNewCategory(@RequestBody CategoryDto categoryDto){
        return categoryMapper.mapToCategoryDto(categoryService.addCategory(categoryMapper.mapToCategory(categoryDto)));
    }

    @PutMapping(value = "/category")
    public CategoryDto updateCategory(@RequestBody CategoryDto categoryDto){
        return categoryMapper.mapToCategoryDto(categoryService.updateCategory(categoryMapper.mapToCategory(categoryDto)));
    }

    @DeleteMapping(value = "/category")
    public void deleteCategory(@RequestParam long categoryId){
        categoryService.deleteCategory(categoryId);
    }
}