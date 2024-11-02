package tech.geniusxp.controllers;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tech.geniusxp.models.Coupon;
import tech.geniusxp.repositories.CouponRepository;
import tech.geniusxp.repositories.EventRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("coupons")
public class CouponController {
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private EventRepository eventRepository;

    @GetMapping("list")
    public String getCouponListView(@RequestParam(value = "search", required = false) String searchTerm, Model model) {
        if (searchTerm != null) {
            model.addAttribute("coupons", couponRepository.findByNameContainingIgnoreCase(searchTerm));
            model.addAttribute("search", searchTerm);
            return "coupons/list";
        }

        model.addAttribute("coupons", couponRepository.findAll());
        return "coupons/list";
    }

    @GetMapping("create")
    public String createCouponView(Coupon coupon, Model model) {
        model.addAttribute("events", eventRepository.findAll());
        return "coupons/create";
    }

    @PostMapping("create")
    @Transactional
    public String createCouponAction(@Valid Coupon coupon, Model model) {
        couponRepository.save(coupon);
        model.addAttribute("message", "Cupom cadastrado!");

        return "redirect:/coupons/list";
    }

    @GetMapping("edit/{id}")
    public String editCouponView(@PathVariable("id") Long id, Model model) {
        model.addAttribute("coupon", couponRepository.findById(id));
        model.addAttribute("events", eventRepository.findAll());

        return "coupons/edit";
    }


    @PostMapping("edit")
    public String editCouponAction(@Valid Coupon coupon, BindingResult result, RedirectAttributes redirectAttributes, Model model){
        if (result.hasErrors()) return "coupons/edit";

        couponRepository.save(coupon);
        redirectAttributes.addFlashAttribute("message", "Cupom atualizado");

        return "redirect:/coupons/list";
    }

    @PostMapping("delete")
    @Transactional
    public String deleteCoupon(Long id, RedirectAttributes redirectAttributes) {
        couponRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Cupom removido");
        return "redirect:/coupons/list";
    }
}
