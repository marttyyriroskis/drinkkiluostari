package com.drinkkiluostari.backend.web;

//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.drinkkiluostari.backend.domain.Asiakas;
import com.drinkkiluostari.backend.repository.AsiakasRepository;
import com.drinkkiluostari.backend.repository.PostinumeroRepository;
import com.drinkkiluostari.backend.repository.TilausRepository;

import jakarta.validation.Valid;

@Controller
@Validated
public class AsiakasController {
    private final AsiakasRepository asiakasRepository;
    private final PostinumeroRepository postinumeroRepository;
    private final TilausRepository tilausRepository;
    
    public AsiakasController(AsiakasRepository asiakasRepository, PostinumeroRepository postinumeroRepository, TilausRepository tilausRepository) {
        this.asiakasRepository = asiakasRepository;
        this.postinumeroRepository = postinumeroRepository;
        this.tilausRepository = tilausRepository;
    }

    // Get asiakkaat
    @GetMapping("asiakasList")
    public String getAsiakkaat(Model model) {
        model.addAttribute("asiakkaat", asiakasRepository.findAll());
        return "asiakasList";
    }

    // Add new asiakas
    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("asiakasNew")
    public String newAsiakas(Model model) {
        model.addAttribute("asiakas", new Asiakas());
        model.addAttribute("postinumerot", postinumeroRepository.findAll());
        model.addAttribute("tilaukset", tilausRepository.findAll());
        return "asiakasNew";
    }

    // Save a new asiakas
    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("asiakasSave")
    public String saveAsiakas(@Valid @ModelAttribute("asiakas") Asiakas asiakas, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("asiakasEdit", asiakas);
            model.addAttribute("postinumerot", postinumeroRepository.findAll());
            model.addAttribute("tilaukset", tilausRepository.findAll());
            return "asiakasNew";
        }
        asiakasRepository.save(asiakas);
        return "redirect:asiakasList";
    }

    // Edit asiakas
    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("asiakasEdit/{id}")
    public String editAsiakas(@PathVariable("id") Long id, Model model) {
        model.addAttribute("asiakasEdit", asiakasRepository.findById(id));
        model.addAttribute("postinumerot", postinumeroRepository.findAll());
        model.addAttribute("tilaukset", tilausRepository.findAll());
        return "asiakasEdit";
    }

    // Save an edited asiakas
    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("asiakasSaveEdited")
    public String saveEditedAsiakas(@Valid @ModelAttribute("asiakas") Asiakas asiakas, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("asiakasEdit", asiakas);
            model.addAttribute("postinumerot", postinumeroRepository.findAll());
            model.addAttribute("tilaukset", tilausRepository.findAll());
            return "asiakasEdit";
        }
        asiakasRepository.save(asiakas);
        return "redirect:asiakasList";
    }

    // Delete asiakas
    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("asiakasDelete/{id}")
    public String deleteAsiakas(@PathVariable("id") Long id, Model model) {
        asiakasRepository.deleteById(id);
        return "redirect:asiakasList";
    }

}
