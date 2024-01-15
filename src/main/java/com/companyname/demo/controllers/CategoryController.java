package com.companyname.demo.controllers;

import com.companyname.demo.dto.CategoryDTO;
import com.companyname.demo.services.CategoryService;
import com.companyname.demo.security.validators.CategoryValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories") //by REST rules plural
public class CategoryController {

    //constructor injection
    private final CategoryService categoryService;
    private final CategoryValidator categoryValidator;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll(@RequestParam(required = false) String type) {
        //information of user who is currently logged in
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();//getName=username
        //and username is unique on the user table, and also we can call
        //UserRepository to get more data of currently logged user
        return ResponseEntity.ok(categoryService.findAll(type));
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoryDTO> byId(@PathVariable Integer id,@RequestParam String type){
        return ResponseEntity.ok(categoryService.findById(id, type));
    }
    @PostMapping
    @PreAuthorize("@customAuth.hasPermission()") //@Valid activates validation annotations
    public ResponseEntity<Void> create(@RequestBody @Valid CategoryDTO categoryDTO, @RequestParam String type) {
        Errors errors = new BeanPropertyBindingResult(categoryDTO, "categoryDTO");
        ValidationUtils.invokeValidator(categoryValidator, categoryDTO, errors);

        categoryService.save(categoryDTO,type); //ignore id
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody CategoryDTO categoryDTO) {
        categoryService.update(id, categoryDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("@customAuth.hasPermissionToDelete(id)")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        categoryService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
