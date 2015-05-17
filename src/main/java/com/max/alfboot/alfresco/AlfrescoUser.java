package com.max.alfboot.alfresco;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Created by Maxim on 16-May-15.
 */
public class AlfrescoUser extends User {

    private String token;

    public AlfrescoUser(String username, String password, boolean enabled, String token, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, true, true, true, authorities);
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
