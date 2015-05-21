package com.max.alfboot.main;

import com.max.alfboot.alfresco.AlfrescoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;

/**
 * Created by Maxim on 16-May-15.
 */
@RestController
public class Controller {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private AlfrescoService alfrescoService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!!!!!!!!!!!";
    }

    @RequestMapping("/gateway")
    public @ResponseBody AuditApplicationEvent page(HttpServletRequest request) {
        AuditApplicationEvent event = new AuditApplicationEvent(request.getUserPrincipal().getName(), "simple", "My page opened");
        publisher.publishEvent(event);
        return event;
    }

    @RequestMapping("/alfresco")
    public @ResponseBody String alfresco(@RequestParam("path") String path) {
        return alfrescoService.alfresco().get(path, String.class);
    }

    @RequestMapping("/auth")
    public ModelAndView authenticate(@RequestParam("ticket") String ticket, @RequestParam(value = "redirectTo", defaultValue = "/hello") String redirectTo) {
        String[] result = new String(Base64.getDecoder().decode(ticket)).split(":");

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(result[0], result[1]));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ModelAndView("redirect:" + redirectTo);
    }

    /*@RequestMapping("/auth")
    public ModelAndView authenticate(@RequestParam("username") String username, @RequestParam("ticket") String ticket, @RequestParam(value = "redirectTo", defaultValue = "/hello") String redirectTo) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, ticket));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ModelAndView("redirect:" + redirectTo);
    }*/

}