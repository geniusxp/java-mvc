package tech.geniusxp.controllers;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tech.geniusxp.models.Speaker;
import tech.geniusxp.repositories.SpeakerRepository;
import tech.geniusxp.repositories.SpeakerRepository;

@Controller
@RequestMapping("speakers")
public class SpeakerController {
    @Autowired
    private SpeakerRepository speakerRepository;

    @GetMapping("list")
    public String getSpeakerListView(@RequestParam(value = "search", required = false) String searchTerm, Model model) {
        if (searchTerm != null) {
            model.addAttribute("speakers", speakerRepository.findByNameContainingIgnoreCase(searchTerm));
            model.addAttribute("search", searchTerm);
            return "speakers/list";
        }

        model.addAttribute("speakers", speakerRepository.findAll());
        return "speakers/list";
    }

    @GetMapping("create")
    public String createSpeakerView(Speaker speaker, Model model) {
        return "speakers/create";
    }

    @PostMapping("create")
    @Transactional
    public String createSpeakerAction(@Valid Speaker speaker, Model model) {
        speakerRepository.save(speaker);
        model.addAttribute("message", "Palestrante cadastrado!");

        return "redirect:/speakers/list";
    }

    @GetMapping("edit/{id}")
    public String editSpeakerView(@PathVariable("id") Long id, Model model) {
        model.addAttribute("speaker", speakerRepository.findById(id));

        return "speakers/edit";
    }


    @PostMapping("edit")
    public String editSpeakerAction(@Valid Speaker speaker, BindingResult result, RedirectAttributes redirectAttributes, Model model){
        if (result.hasErrors()) return "speakers/edit";

        speakerRepository.save(speaker);
        redirectAttributes.addFlashAttribute("message", "Palestrante atualizado");

        return "redirect:/speakers/list";
    }

    @PostMapping("delete")
    @Transactional
    public String deleteSpeaker(Long id, RedirectAttributes redirectAttributes) {
        speakerRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Palestrante removido");
        return "redirect:/speakers/list";
    }
}