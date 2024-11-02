package tech.geniusxp.controllers;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tech.geniusxp.models.User;
import tech.geniusxp.repositories.RoleRepository;
import tech.geniusxp.repositories.UserRepository;
import tech.geniusxp.services.UserService;

@Controller
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    @GetMapping("list")
    public String getUserListView(@RequestParam(value = "roles", required = false) String searchTerm, Model model) {
        if (searchTerm != null) {
            model.addAttribute("users", userRepository.findByNameContainingIgnoreCase(searchTerm));
            model.addAttribute("search", searchTerm);
            return "users/list";
        }

        model.addAttribute("users", userRepository.findAll());
        return "users/list";
    }

    @GetMapping("edit/{id}")
    public String editUserView(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id));
        model.addAttribute("roles", roleRepository.findAll());

        return "users/edit";
    }


    @PostMapping("edit")
    public String editUserAction(@Valid User user, BindingResult result, RedirectAttributes redirectAttributes, Model model){
        if (result.hasErrors()) return "users/edit";

        userRepository.save(user);
        redirectAttributes.addFlashAttribute("message", "Usuário atualizado");

        return "redirect:/users/list";
    }

    @PostMapping("delete")
    @Transactional
    public String deleteUser(Long id, RedirectAttributes redirectAttributes) {
        userRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Usuário removido");
        return "redirect:/users/list";
    }

    @GetMapping("create")
    public String createUserView(User user, Model model) {
        model.addAttribute("roles", roleRepository.findAll());
        return "users/create";
    }

    @PostMapping("create")
    @Transactional
    public String createUserAction(@Valid User user, Model model) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);

        model.addAttribute("message", "Usuário cadastrado!");

        return "redirect:/users/list";
    }
}