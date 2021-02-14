package com.cloud.examsystem.authentication.controller;

import com.cloud.examsystem.user.service.UserAuthService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
@Log4j2
@RequestMapping("/")
public class AuthenticationController {
    private final UserAuthService userAuthService;


    @GetMapping
    public String root(){
        return "redirect:/login";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/session")
    public ResponseEntity<?> getSession(Authentication authentication) {
        return ResponseEntity.ok(authentication);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        SecurityContextHolder.getContext().setAuthentication(null);
    }


    @PreAuthorize("!isAuthenticated()")
    @GetMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        /// Credential hatasını basmak için gerekli.
        boolean isLocked = false;
        HttpSession session = request.getSession(false);
        String errorMessage = (String) model.getAttribute("message");
        if (session != null) {
            AuthenticationException ex = (AuthenticationException) session
                    .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

            if (ex != null) {
                if (ex instanceof LockedException) {
                    isLocked = true;
                }

                errorMessage = ex.getMessage();
                session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            }
        }

        model.addAttribute("isLocked", isLocked);
        model.addAttribute("message", errorMessage);

        return "login";
    }

    @GetMapping("/homepage")
    public String getHomePage(Model model){
        int a=1;
        if(userAuthService.getCurrentUser().isInstructor()){
            return "redirect:/instructor";
        }
        return "redirect:/exam/home";
    }

//    @GetMapping()
//    public String Index(){
//        return "redirect: /login";
//
//    }
}
