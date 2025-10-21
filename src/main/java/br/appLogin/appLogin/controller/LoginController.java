package br.appLogin.appLogin.controller;

import br.appLogin.appLogin.model.Usuario;
import br.appLogin.appLogin.repository.UsuarioRepository;
import br.appLogin.appLogin.service.CookieService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@Controller

public class LoginController {
    @Autowired
    private UsuarioRepository ur;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/home")
    public String home(Model model, HttpServletRequest request) throws UnsupportedEncodingException {
        model.addAttribute("nome", CookieService.getCookie(request, "nomeUsuario"));
        return "index";
    }

    @PostMapping("/logar")
    public String loginUsuario(Usuario usuario, Model model, HttpServletResponse response) throws UnsupportedEncodingException {
        Usuario usuarioLogado = this.ur.login(usuario.getEmail(), usuario.getSenha());
        if(usuarioLogado != null){
            CookieService.setCookie(response, "UsuarioId", String.valueOf(usuarioLogado.getId()), 500);
            CookieService.setCookie(response, "nomeUsuario", String.valueOf(usuarioLogado.getNome()), 500);
            return "redirect:/home";
        }

        model.addAttribute("erro", "Usuário Inválido");
        return "login";
    }


    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String cadastrarUsuario(@Valid Usuario usuario, BindingResult result){
        if(result.hasErrors()){
            return "redirect:/register";
        }
        ur.save(usuario);
        return "redirect:/login";
    }
}
