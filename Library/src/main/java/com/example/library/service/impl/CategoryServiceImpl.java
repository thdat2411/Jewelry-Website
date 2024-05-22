package com.example.library.service.impl;

import com.example.library.model.Category;
import com.example.library.repository.CategoryRepository;
import com.example.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CancellationException;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category save(Category category) {
        Category categorySave = new Category(category.getCategoryName());
        return categoryRepository.save(categorySave);
    }


    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).get();
    }

    @Override
    public Category update(Category category){
        Category categoryUpdate= null;
        try{
            categoryUpdate = categoryRepository.findById(category.getCategoryId()).get();
            categoryUpdate.setCategoryName(category.getCategoryName());
            categoryUpdate.setCategoryIsActivated(false);
            categoryUpdate.setCategoryIsDeleted(true);
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return categoryRepository.save(categoryUpdate);
    }

    @Override
    public void deleteById(Long id) {
        Category category = categoryRepository.getReferenceById(id);
        category.setCategoryIsDeleted(true);
        category.setCategoryIsActivated(false);
        categoryRepository.save(category);
    }

    @Override
    public void enabledById(Long id) {
    Category category = categoryRepository.getReferenceById(id);
    category.setCategoryIsActivated(true);
    category.setCategoryIsDeleted(false);
    categoryRepository.save(category);
    }

    @Override
    public List<Category> findAllByActivated() {
        return categoryRepository.findAllByActivated();
    }


}
