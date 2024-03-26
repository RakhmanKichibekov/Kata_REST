package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dao.RoleRepository;
import ru.kata.spring.boot_security.demo.exception.NotCreatedException;
import ru.kata.spring.boot_security.demo.exception.NotUpdatedException;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/admin/api")
public class RestAdminController {
    private final UserService userService;
    private final RoleRepository roleDao;

    @Autowired
    public RestAdminController(UserServiceImpl userService, RoleRepository roleDao) {
        this.userService = userService;
        this.roleDao = roleDao;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/principal")
    public ResponseEntity<User> getPrincipalUser(Principal principal) {
        return new ResponseEntity<>(userService.findByUsername(principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/allRoles")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roleList = roleDao.findAll();
        return new ResponseEntity<>(roleList, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        User userById = userService.findUserById(id);
        return new ResponseEntity<>(userById, HttpStatus.OK);
    }

    @PostMapping("/users")
    public  ResponseEntity<HttpStatus> addNewUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new NotCreatedException(userService.createStringBuilder(bindingResult).toString());
        }
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<HttpStatus> updateUser(@PathVariable("id") long id,
                                                 @Valid @RequestBody User user, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new NotUpdatedException(userService.createStringBuilder(bindingResult).toString());
        }
        userService.updateUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable("id") long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
