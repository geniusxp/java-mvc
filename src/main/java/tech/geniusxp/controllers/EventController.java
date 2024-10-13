package tech.geniusxp.controllers;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tech.geniusxp.models.Event;
import tech.geniusxp.repositories.EventRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("events")
public class EventController {
    @Autowired
    private EventRepository eventRepository;

    @GetMapping("list")
    public String getEventListView(@RequestParam(value = "search", required = false) String searchTerm, Model model) {
        if (searchTerm != null) {
            model.addAttribute("events", eventRepository.findByNameContainingIgnoreCase(searchTerm));
            model.addAttribute("search", searchTerm);
            return "events/list";
        }

        model.addAttribute("events", eventRepository.findAll());
        return "events/list";
    }

    @GetMapping("create")
    public String createEventView(Event event, Model model) {
        return "events/create";
    }

    @PostMapping("create")
    @Transactional
    public String createEventAction(Event event, Model model) {
        eventRepository.save(event);
        model.addAttribute("message", "Evento cadastrado!");

        return "redirect:/events/list";
    }

    @GetMapping("edit/{id}")
    public String editEventView(@PathVariable("id") Long id, Model model) {
        model.addAttribute("event", eventRepository.findById(id));

        return "events/edit";
    }


    @PostMapping("edit")
    public String editEventAction(@Valid Event event, BindingResult result, RedirectAttributes redirectAttributes, Model model){
        if (result.hasErrors()) return "events/edit";

        eventRepository.save(event);
        redirectAttributes.addFlashAttribute("message", "Evento atualizado");

        return "redirect:/events/list";
    }

    @PostMapping("delete")
    @Transactional
    public String deleteEvent(Long id, RedirectAttributes redirectAttributes) {
        eventRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Evento removido");
        return "redirect:/events/list";
    }
}
