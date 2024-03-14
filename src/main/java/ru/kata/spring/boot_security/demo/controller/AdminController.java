package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dao.RoleRepository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;
import java.util.List;

@Controller
public class AdminController {
    private final UserService userService;
    private final RoleRepository roleDao;

    @Autowired
    public AdminController(UserServiceImpl userService, RoleRepository roleDao) {
        this.userService = userService;
        this.roleDao = roleDao;
    }

    @GetMapping("/adminPage")
    public String getAdminPage(@RequestParam(value = "userId", required = false) Long id,
                               Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("userInfo", user);
        model.addAttribute("user", new User());
        User userById = new User();
        if (id != null) {
            System.out.println("AAAAA " + id);
            userById = userService.findUserById(id);
        }
        model.addAttribute("userById", userById);
        model.addAttribute("users", userService.getAllUsers());
        List<Role> roleList = roleDao.findAll();
        model.addAttribute("allRoles", roleList);
        return "adminPage";
    }

    @PostMapping("/adminPage/create")
    public String createUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/adminPage#adminPanel";
    }

    @PostMapping("/adminPage/update")
    public String updateUser(@ModelAttribute("userById") User user) {
        userService.updateUser(user);
        return "redirect:/adminPage#adminPanel";
    }

    @PostMapping("/adminPage/delete")
    public String deleteUser(@RequestParam("userId") Long id) {
        userService.deleteUserById(id);
        return "redirect:/adminPage#adminPanel";
    }
}
