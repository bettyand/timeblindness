package com.capstone.timeblindness;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void registerDefaultUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        Role roleUser = roleRepo.findByName("User");
        user.addRole(roleUser);

        userRepo.save(user);
    }

    public User get(Long id) {
        return userRepo.findById(id).get();
    }

    public List<Role> listRoles() {
        return roleRepo.findAll();
    }

    public void save(User user) {
        userRepo.save(user);
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User getByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public Page<User> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) 
            ? Sort.by(sortField).ascending() 
            : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.userRepo.findAll(pageable);
    }
}
