package com.nexa.business.web;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WorkspaceController {
    @GetMapping("/") public String root(Authentication a){return a==null?"redirect:/login":"redirect:"+workspace(a);}
    @GetMapping("/login") public String login(Authentication a){return a==null?"login":"redirect:"+workspace(a);}
    @GetMapping("/admin") public String admin(Authentication a){return viewOrRedirect(a,"ROLE_ADMIN","admin");}
    @GetMapping("/owner") public String owner(Authentication a){return viewOrRedirect(a,"ROLE_OWNER","owner");}
    @GetMapping("/manager") public String manager(Authentication a){return viewOrRedirect(a,"ROLE_MANAGER","manager");}
    @GetMapping("/cashier") public String cashier(Authentication a){return viewOrRedirect(a,"ROLE_CASHIER","cashier");}
    @GetMapping("/warehouse") public String warehouse(Authentication a){return viewOrRedirect(a,"ROLE_WAREHOUSE","warehouse");}
    private String viewOrRedirect(Authentication a,String role,String view){if(a==null)return "redirect:/login";boolean ok=a.getAuthorities().stream().anyMatch(x->x.getAuthority().equals(role));return ok?view:"redirect:"+workspace(a);}
    public static String workspace(Authentication a){if(a==null)return "/login";for(org.springframework.security.core.GrantedAuthority g:a.getAuthorities()){switch(g.getAuthority()){case "ROLE_OWNER":return "/owner";case "ROLE_MANAGER":return "/manager";case "ROLE_CASHIER":return "/cashier";case "ROLE_WAREHOUSE":return "/warehouse";default:}}return "/admin";}
}
