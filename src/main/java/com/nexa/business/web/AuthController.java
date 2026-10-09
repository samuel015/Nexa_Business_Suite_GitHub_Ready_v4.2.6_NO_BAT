package com.nexa.business.web;

import com.nexa.business.domain.AppUser;
import com.nexa.business.dto.LoginResponse;
import com.nexa.business.repository.AppUserRepository;
import com.nexa.business.service.AuditService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.*;
import java.util.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationManager manager; private final AppUserRepository users; private final AuditService audit;
    public AuthController(AuthenticationManager manager,AppUserRepository users,AuditService audit){this.manager=manager;this.users=users;this.audit=audit;}
    @GetMapping("/csrf") public Map<String,String> csrf(@RequestAttribute(name="_csrf") CsrfToken token){Map<String,String> m=new LinkedHashMap<>();m.put("token",token.getToken());m.put("headerName",token.getHeaderName());return m;}

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username,@RequestParam String password,HttpServletRequest req){
        try{
            Authentication auth=manager.authenticate(new UsernamePasswordAuthenticationToken(username.trim(),password));
            SecurityContext context=SecurityContextHolder.createEmptyContext();context.setAuthentication(auth);SecurityContextHolder.setContext(context);
            req.getSession(true).setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,context);
            AppUser u=users.findByUsernameIgnoreCase(auth.getName()).orElseThrow();audit.log(u.getUsername(),"LOGIN","AUTH","Login berhasil sebagai "+u.getDisplayName());log.info("Login success username={} role={}",u.getUsername(),u.getRole());return ResponseEntity.ok(view(u,auth));
        }catch(AuthenticationException e){audit.log(username==null?"unknown":username,"LOGIN_FAILED","AUTH","Percobaan login gagal");log.warn("Login failed username={}",username==null?"unknown":username);Map<String,Object> m=new LinkedHashMap<>();m.put("status",401);m.put("error","Unauthorized");m.put("message","Username atau password salah.");return ResponseEntity.status(401).body(m);}
    }
    @PostMapping("/logout") public Map<String,Object> logout(Authentication a,HttpServletRequest req){if(a!=null)audit.log(a.getName(),"LOGOUT","AUTH","Keluar dari aplikasi");HttpSession s=req.getSession(false);if(s!=null)s.invalidate();SecurityContextHolder.clearContext();return Collections.singletonMap("loggedOut",true);}
    @GetMapping("/me") public LoginResponse me(Authentication a){AppUser u=users.findByUsernameIgnoreCase(a.getName()).orElseThrow();return view(u,a);}
    private LoginResponse view(AppUser u,Authentication a){return new LoginResponse(u.getUsername(),u.getDisplayName(),u.getJobTitle(),u.getRole(),WorkspaceController.workspace(a));}
}
