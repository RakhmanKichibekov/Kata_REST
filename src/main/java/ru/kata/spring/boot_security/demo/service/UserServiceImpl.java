package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleRepository;
import ru.kata.spring.boot_security.demo.dao.UserRepository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userDao;
    private final RoleRepository roleDao;

    @Autowired
    public UserServiceImpl(UserRepository userDao, RoleRepository roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserById(long id) {
        return userDao.findUserById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        for(User us: getAllUsers()){
            if (us.getUsername().equals(user.getUsername())){
                return;
            }
        }
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        userDao.save(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        if (user != null) {
            user.setPassword(passwordEncoder().encode(user.getPassword()));
            userDao.save(user);
        }
    }

    @Override
    @Transactional
    public void deleteUserById(long id) {
        userDao.deleteUserById(id);
    }

    @Override
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Transactional
    public void addUsers() {
        Role roleUser = roleDao.save(new Role("ROLE_USER"));
        List<Role> listRoleUser = new ArrayList<>();
        listRoleUser.add(roleUser);
        User user = new User("user@mail.ru", "$2a$12$2uQtsDRFW3Y0z6YkO3/Bw.sbypeZ1S.JVZ64sqw.xc.l1PwQK/gGq",
                "Rahman", "Kichibekov", (byte) 21, listRoleUser);

        Role roleAdmin = roleDao.save(new Role("ROLE_ADMIN"));
        List<Role> listRoleAdmin = new ArrayList<>();
        listRoleAdmin.add(roleAdmin);
        listRoleAdmin.add(roleUser);
        User admin = new User("admin@mail.ru", "$2a$12$JsAKl/AMpXLZUyNDrmnKW.vu2.j2qoiILIPNS67eAWvVDE9i0AS4m",
                "Admin", "Adminov", (byte) 45, listRoleAdmin);

        userDao.save(user);
        userDao.save(admin);
    }
}
