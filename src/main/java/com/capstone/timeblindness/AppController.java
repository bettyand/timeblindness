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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AppController {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private AttemptService attemptService;

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

    @GetMapping("/tasks/{id}")
    public String viewTask(@PathVariable("id") Long id, Model model) {
        Task task = taskService.getTaskById(id);
        model.addAttribute("task", task);
        model.addAttribute("listAttempts", attemptService.getByTaskId(id));
        return "view_task";
    }

    @GetMapping("/tasks/{id}/attempt/new")
    public String newAttempt(@PathVariable("id") Long id, Model model) {
        Task task = taskService.getTaskById(id);
        Attempt attempt = new Attempt();
        attempt.setTask(task);
        attemptService.saveAttempt(attempt);
        model.addAttribute("task", task);
        model.addAttribute("attempt", attempt);
        return "attempt";
    }

    @GetMapping("/tasks/{tId}/attempt/{aId}")
    public String viewAttempt(@PathVariable("tId") Long tId, @PathVariable("aId") Long aId, Model model) {
        Task task = taskService.getTaskById(tId);
        Attempt attempt = attemptService.getAttemptById(aId);
        model.addAttribute("task", task);
        model.addAttribute("attempt", attempt);
        return "attempt";
    }

    @PostMapping("/tasks/{tId}/attempt/{aId}")
    public String saveAttempt(@PathVariable("tId") Long tId, @PathVariable("aId") Long aId, @RequestParam String action, Model model) {
        Task task = taskService.getTaskById(tId);
        Attempt attempt = attemptService.getAttemptById(aId);

        if (action.equals("start")) {
            attempt.setStartTime(System.currentTimeMillis());
            attemptService.saveAttempt(attempt);
            model.addAttribute("task", task);
            model.addAttribute("attempt", attempt);

                return "redirect:/tasks/" + tId + "/attempt/" + aId;
        }

        if (action.equals("stop")) {
            attempt.setStopTime(System.currentTimeMillis());
            attemptService.saveAttempt(attempt);
            model.addAttribute("task", task);
            model.addAttribute("attempt", attempt);
            return "redirect:/tasks/" + tId + "/attempt/" + aId;
        }

        if (action.equals("save")) {
            attempt.calcDuration();
            attemptService.saveAttempt(attempt);

            if (task.getShortestTime() == 0) {
                task.setShortestTime(attempt.getDuration());
            } else if (attempt.getDuration() < task.getShortestTime()) {
                task.setShortestTime(attempt.getDuration());
            } else if (attempt.getDuration() > task.getLongestTime()) {
                task.setLongestTime(attempt.getDuration());
            }

            task.incrementNumAttempts();
            task.addToTotalTime(attempt.getDuration());
            task.updateAverageTime();

            taskService.saveTask(task);

            return "redirect:/tasks/" + tId + "?savesuccess";
        }

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
