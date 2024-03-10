package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dao.RoleRepository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping(value = "/admin/users")
public class AdminController {
    private final UserService userService;
    private final RoleRepository roleDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserService userService, RoleRepository roleDao, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping()
    public String getAllUsers(Model model){
        model.addAttribute("users", userService.getAllUsers());
        return "allUsers";
    }

    @GetMapping(value = "/new")
    public String showNewUserForm(Model model) {
        model.addAttribute("user", new User());
        List<Role> roleList = roleDao.findAll();
        model.addAttribute("allRoles", roleList);
        return "newUser";
    }

    @GetMapping(value = "/edit")
    public String showEditUserForm(Model model, @RequestParam int id) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        List<Role> roleList = roleDao.findAll();
        model.addAttribute("allRoles", roleList);
        return "editUser";
    }

    @PostMapping(value = "/create")
    public String createUser(@ModelAttribute("user") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    @PostMapping(value = "/update")
    public String updateUser(@ModelAttribute("user") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    @PostMapping(value = "/delete")
    public String deleteUser(@RequestParam int id) {
        userService.deleteUserById(id);
        return "redirect:/admin/users";
    }
}
