package com.max.alfboot.alfresco;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * Created by Maxim on 16-May-15.
 */
@Service
public class AlfrescoService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${alfresco.url}")
    private String alfrescoUrl;

    public AlfrescoUser retrieveUser(UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        String userName = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        Alfresco alfresco = isToken(password) ? new Alfresco(restTemplate, alfrescoUrl, password) :
                new Alfresco(restTemplate, alfrescoUrl, userName, password);

        AlfUser alfUser = alfresco.get("/api/people/" + userName, AlfUser.class);
        return new AlfrescoUser(alfUser.userName, password, alfUser.enabled, alfresco.token(), Collections.<GrantedAuthority>emptyList());
    }

    public Alfresco alfresco() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = authentication.getCredentials().toString();
        return new Alfresco(restTemplate, alfrescoUrl, token);
    }

    private boolean isToken(String password) {
        return password.startsWith("TICKET_");
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class AlfUser {
        public String userName;
        public String firstName;
        public String lastName;
        public boolean enabled;
    }
}
