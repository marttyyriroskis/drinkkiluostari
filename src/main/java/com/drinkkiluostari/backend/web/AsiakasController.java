package com.drinkkiluostari.backend.web;

import java.util.Comparator;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

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
    @GetMapping("/asiakasList")
    public String getAsiakkaat(Model model) {
        List<Asiakas> asiakkaat = asiakasRepository.findAllActive();
        asiakkaat.sort(Comparator.comparing(Asiakas::getId));
        model.addAttribute("asiakkaat", asiakkaat);
        return "/asiakasList";
    }

    // Add new asiakas
    @GetMapping("/asiakasNew")
    public String newAsiakas(Model model) {
        model.addAttribute("asiakas", new Asiakas());
        model.addAttribute("postinumerot", postinumeroRepository.findAll());
        model.addAttribute("tilaukset", tilausRepository.findAllActive());
        return "/asiakasNew";
    }

    // Save a new asiakas
    @PostMapping("/asiakasSave")
    public String saveAsiakas(@Valid @ModelAttribute("asiakas") Asiakas asiakas, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("asiakasEdit", asiakas);
            model.addAttribute("postinumerot", postinumeroRepository.findAll());
            model.addAttribute("tilaukset", tilausRepository.findAllActive());
            return "/asiakasNew";
        }
        asiakasRepository.save(asiakas);
        return "redirect:/asiakasList";
    }

    // Edit asiakas
    @GetMapping("/asiakasEdit/{id}")
    public String editAsiakas(@PathVariable("id") Long id, Model model) {
        model.addAttribute("asiakasEdit", asiakasRepository.findByIdActive(id));
        model.addAttribute("postinumerot", postinumeroRepository.findAll());
        model.addAttribute("tilaukset", tilausRepository.findAllActive());
        return "/asiakasEdit";
    }

    // Save an edited asiakas
    @PostMapping("/asiakasSaveEdited")
    public String saveEditedAsiakas(@Valid @ModelAttribute("asiakas") Asiakas asiakas, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("asiakasEdit", asiakas);
            model.addAttribute("postinumerot", postinumeroRepository.findAll());
            model.addAttribute("tilaukset", tilausRepository.findAllActive());
            return "/asiakasEdit";
        }

        if (asiakas.getPostinumero() != null && asiakas.getPostinumero().getId() == null) {
            postinumeroRepository.save(asiakas.getPostinumero());
        }

        asiakasRepository.save(asiakas);
        return "redirect:/asiakasList";
    }

    // Delete asiakas
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/asiakasDelete/{id}")
    public String deleteAsiakas(@PathVariable("id") Long id, Model model) {
        Asiakas asiakas = asiakasRepository.findByIdActive(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Asiakas not found"));

        asiakas.delete();

        asiakasRepository.save(asiakas);
        return "redirect:/asiakasList";
    }

}
