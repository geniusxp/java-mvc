package tech.geniusxp.controllers;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tech.geniusxp.models.Lecture;
import tech.geniusxp.repositories.LectureRepository;
import tech.geniusxp.repositories.EventRepository;
import tech.geniusxp.repositories.SpeakerRepository;
import jakarta.validation.Valid;

import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("lectures")
public class LectureController {
    @Autowired
    private LectureRepository lectureRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private SpeakerRepository speakerRepository;

    @GetMapping("list")
    public String getLectureListView(@RequestParam(value = "search", required = false) String searchTerm, Model model) {
        if (searchTerm != null) {
            model.addAttribute("lectures", lectureRepository.findByNameContainingIgnoreCase(searchTerm));
            model.addAttribute("search", searchTerm);
            return "lectures/list";
        }

        model.addAttribute("lectures", lectureRepository.findAll());
        return "lectures/list";
    }

    @GetMapping("create")
    public String createLectureView(Lecture lecture, Model model) {
        model.addAttribute("events", eventRepository.findAll());
        model.addAttribute("speakers", speakerRepository.findAll());
        return "lectures/create";
    }

    @PostMapping("create")
    @Transactional
    public String createLectureAction(Lecture lecture, Model model) {
        lectureRepository.save(lecture);
        model.addAttribute("message", "Palestra cadastrada!");

        return "redirect:/lectures/list";
    }

    @GetMapping("edit/{id}")
    public String editLectureView(@PathVariable("id") Long id, Model model) {
        Lecture lecture = lectureRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid lecture Id:" + id));
        model.addAttribute("lecture", lecture);
        model.addAttribute("events", eventRepository.findAll());
        model.addAttribute("speakers", speakerRepository.findAll());

        // Formatar a data para o formato esperado pelo input datetime-local
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        model.addAttribute("formattedDate", lecture.getDate().format(formatter));

        return "lectures/edit";
    }




    @PostMapping("edit")
    public String editLectureAction(@Valid Lecture lecture, BindingResult result, RedirectAttributes redirectAttributes, Model model){
        if (result.hasErrors()) return "lectures/edit";

        lectureRepository.save(lecture);
        redirectAttributes.addFlashAttribute("message", "Palestra atualizada");

        return "redirect:/lectures/list";
    }

    @PostMapping("delete")
    @Transactional
    public String deleteLecture(Long id, RedirectAttributes redirectAttributes) {
        lectureRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Palestra removida");
        return "redirect:/lectures/list";
    }
}
