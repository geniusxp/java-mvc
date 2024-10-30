package tech.geniusxp.controllers;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tech.geniusxp.models.TicketType;
import tech.geniusxp.repositories.TicketTypeRepository;
import tech.geniusxp.repositories.EventRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("ticketTypes")
public class TicketTypeController {
    @Autowired
    private TicketTypeRepository ticketTypeRepository;
    @Autowired
    private EventRepository eventRepository;

    @GetMapping("list")
    public String getTicketTypeListView(Model model) {
        model.addAttribute("ticketTypes", ticketTypeRepository.findAll());
        return "ticketTypes/list";
    }

    @GetMapping("create")
    public String createTicketTypeView(TicketType ticketType, Model model) {
        model.addAttribute("events", eventRepository.findAll());
        return "ticketTypes/create";
    }

    @PostMapping("create")
    @Transactional
    public String createTicketTypeAction(TicketType ticketType, Model model) {
        ticketTypeRepository.save(ticketType);
        model.addAttribute("message", "Tipo de ingresso cadastrado!");

        return "redirect:/ticketTypes/list";
    }

    @GetMapping("edit/{id}")
    public String editTicketTypeView(@PathVariable("id") Long id, Model model) {
        model.addAttribute("ticketType", ticketTypeRepository.findById(id));

        return "ticketTypes/edit";
    }


    @PostMapping("edit")
    public String editTicketTypeAction(@Valid TicketType ticketType, BindingResult result, RedirectAttributes redirectAttributes, Model model){
        if (result.hasErrors()) return "ticketTypes/edit";

        ticketTypeRepository.save(ticketType);
        redirectAttributes.addFlashAttribute("message", "Tipo de ingresso atualizado");

        return "redirect:/ticketTypes/list";
    }

    @PostMapping("delete")
    @Transactional
    public String deleteTicketType(Long id, RedirectAttributes redirectAttributes) {
        ticketTypeRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Tipo de ingresso removido");
        return "redirect:/ticketTypes/list";
    }
}
