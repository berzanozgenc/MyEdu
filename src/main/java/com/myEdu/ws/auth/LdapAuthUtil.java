package com.myEdu.ws.auth;

import org.springframework.stereotype.Component;
import javax.naming.directory.DirContext;
import javax.naming.ldap.InitialLdapContext;
import java.util.Hashtable;

@Component
public class LdapAuthUtil {

    private String ldapUrl = "ldap://193.255.45.5:389";
    private String baseDn = "dc=baskent,dc=edu,dc=tr";
    private String searchBase = "ou=akademikveidari";

    public boolean authenticate(String username, String password) {
        String userDn = String.format("uid=%s,%s,%s", username, searchBase, baseDn);
        Hashtable<String, String> env = new Hashtable<>();
        env.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(javax.naming.Context.PROVIDER_URL, ldapUrl);
        env.put(javax.naming.Context.SECURITY_AUTHENTICATION, "simple");
        env.put(javax.naming.Context.SECURITY_PRINCIPAL, userDn);
        env.put(javax.naming.Context.SECURITY_CREDENTIALS, password);

        try {
            DirContext ctx = new InitialLdapContext(env, null);
            ctx.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}