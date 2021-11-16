package wcs.cerebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wcs.cerebook.entity.CerebookMessage;
import wcs.cerebook.entity.CerebookUser;
import wcs.cerebook.repository.MessageRepository;
import wcs.cerebook.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class MessagingController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository msgRepository;

    @GetMapping("/messages")
    public String index(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/message/messaging";
    }

    @GetMapping("/message")
    public String tchat(Model model, @RequestParam(required = false) String username,
                        Principal principal) {
        model.addAttribute("user", userRepository.findByUsername(username));

        // récupération du user connecter
        String usernameCurrent = principal.getName();
        CerebookUser currentUser = userRepository.findByUsername(usernameCurrent);
        ;

        // user destinataire
        CerebookUser userDestinate = userRepository.getCerebookUserByUsername(username);

        // pour récupéré tous les messages envoyé par rapport au user connecter
        List<CerebookMessage> messages = msgRepository.getCerebookMessageByCurrentUseraAndUserDestination(currentUser,
                userDestinate);
        // pour récupéré tous les messages envoyer par rapport a l'utilisateur voulu
        List<CerebookMessage> messagesRecep = msgRepository.getCerebookMessageByCurrentUseraAndUserDestination(userDestinate,
                currentUser);

        return "message/tchat";
    }


    @RequestMapping("/createTchatmessage")
    public String tchatSave(Model model, Principal principal,
                            @Param("userfriend") Long userfriend,
                            @Param("contentMessage") String contentMessage,
                            RedirectAttributes redirectAttributes
    ) {
        // récupération du user connecter
        String usernameCurrent = principal.getName();
        CerebookUser currentUser = userRepository.findByUsername(usernameCurrent);
        //Date a l'heure ou le message est envoyé
        LocalDateTime now = LocalDateTime.now();
        //récupération du user qui va etre le destinataire du message
        CerebookUser userDestinate = userRepository.getById(userfriend);
        // sauvegarde du message en base de donnée
        CerebookMessage message = new CerebookMessage(contentMessage, now, currentUser);
        msgRepository.save(message);
        // une fois le message sauvegarder j'ajoute le destinataire au message et je le sauvegarde une nouvelle fois
        // en base de donnée.
        message.getUserDestination().add(userDestinate);
        msgRepository.save(message);

        redirectAttributes.addAttribute("usernameDestinate", userDestinate.getUsername());


        return "redirect:/message?username={usernameDestinate}";
    }
}
