package com.capstone.timeblindness;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AppController {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @GetMapping("/")
    public String viewHomePage() {
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/process_registration")
    public String processRegistration(User user) {
        userService.registerDefaultUser(user);
        return "redirect:/?registrationsuccess";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> listUsers = userRepo.findAll();
        model.addAttribute("listUsers", listUsers);
        return "users";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        User user = userService.get(id);
        List<Role> listRoles = userService.listRoles();
        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);
        return "edit_user";
    }

    @PostMapping("/users/save")
    public String saveUser(User user) {
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/tasks")
    public String viewTasks(Model model) {
        model.addAttribute("listTasks", taskService.getAllTasks());
        return "tasks";
    }

    @GetMapping("/tasks/new")
    public String newTask(Model model) {
        Task task = new Task();
        model.addAttribute("task", task);
        return "new_task";
    }

    @GetMapping("/tasks/edit/{id}")
    public String editTask(@PathVariable("id") Long id, Model model) {
        Task task = taskService.getTaskById(id);
        model.addAttribute("task", task);
        return "edit_task";
    }

    @PostMapping("/tasks/save")
    public String saveTask(@ModelAttribute("task") Task task) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomUserDetails userDetails = (CustomUserDetails) principal;
        User user = userRepo.findByEmail(userDetails.getUsername());
        task.setUser(user);
        taskService.saveTask(task);
        return "redirect:/tasks?editsuccess";
    }

    @GetMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable("id") Long id) {
        this.taskService.deleteTaskById(id);
        return "redirect:/tasks?deletesuccess";
    }
}
