package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleRepository;
import ru.kata.spring.boot_security.demo.dao.UserRepository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userDao;
    private final RoleRepository roleDao;

    @Autowired
    public UserService(UserRepository userDao, RoleRepository roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public User findUserById(long id) {
        return userDao.findUserById(id);
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Transactional
    public void saveUser(User user) {
        userDao.save(user);
    }

    @Transactional
    public void deleteUserById(long id) {
        userDao.deleteUserById(id);
    }

    @Transactional
    public void addUsers() {
        Role roleUser = roleDao.save(new Role("ROLE_USER"));
        List<Role> listRoleUser = new ArrayList<>();
        listRoleUser.add(roleUser);
        User user = new User("user", "$2a$12$2uQtsDRFW3Y0z6YkO3/Bw.sbypeZ1S.JVZ64sqw.xc.l1PwQK/gGq",
                "Rahman", "Kichibekov", (byte) 21, listRoleUser);

        Role roleAdmin = roleDao.save(new Role("ROLE_ADMIN"));
        List<Role> listRoleAdmin = new ArrayList<>();
        listRoleAdmin.add(roleAdmin);
        listRoleAdmin.add(roleUser);
        User admin = new User("admin", "$2a$12$JsAKl/AMpXLZUyNDrmnKW.vu2.j2qoiILIPNS67eAWvVDE9i0AS4m",
                "Admin", "Adminov", (byte) 45, listRoleAdmin);

        userDao.save(user);
        userDao.save(admin);
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }
}
