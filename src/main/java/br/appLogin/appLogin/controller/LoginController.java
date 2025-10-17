package br.appLogin.appLogin.controller;

import br.appLogin.appLogin.model.Usuario;
import br.appLogin.appLogin.repository.UsuarioRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller

public class LoginController {
    @Autowired
    private UsuarioRepository ur;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/home")
    public String home(){
        return "index";
    }

    @PostMapping("/logar")
    public String loginUsuario(Usuario usuario, Model model, HttpServletResponse response){
        Usuario usuarioLogado = this.ur.login(usuario.getEmail(), usuario.getSenha());
        if(usuarioLogado != null){
            return "redirect:/home";
        }

        model.addAttribute("Erro", "Usuário Inválido");
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
