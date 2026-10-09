package com.nexa.business;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class NexaBusinessApplicationTests {
    @Autowired MockMvc mvc;

    private MockHttpSession login(String user,String pass) throws Exception {
        MvcResult r=mvc.perform(post("/api/auth/login").with(csrf()).param("username",user).param("password",pass))
                .andExpect(status().isOk()).andExpect(jsonPath("$.username").value(user)).andReturn();
        return (MockHttpSession)r.getRequest().getSession(false);
    }

    @Test void allFiveRolesHaveDedicatedWorkspace() throws Exception {
        String[][] cases={{"admin","admin123","/admin"},{"owner","owner123","/owner"},{"manager","manager123","/manager"},{"kasir","kasir123","/cashier"},{"gudang","gudang123","/warehouse"}};
        for(String[] c:cases){MockHttpSession s=login(c[0],c[1]);mvc.perform(get(c[2]).session(s)).andExpect(status().isOk());mvc.perform(get("/api/auth/me").session(s)).andExpect(status().isOk()).andExpect(jsonPath("$.redirect").value(c[2]));}
    }

    @Test void ownerIsReadOnlyManagerCanManageAndWarehouseCannotSell() throws Exception {
        MockHttpSession owner=login("owner","owner123");
        mvc.perform(get("/api/reports").session(owner)).andExpect(status().isOk());
        mvc.perform(post("/api/customers").session(owner).with(csrf()).contentType("application/json").content("{\"name\":\"Blocked\"}")) .andExpect(status().isForbidden());

        MockHttpSession manager=login("manager","manager123");
        mvc.perform(get("/api/customers").session(manager)).andExpect(status().isOk());

        MockHttpSession warehouse=login("gudang","gudang123");
        mvc.perform(post("/api/sales").session(warehouse).with(csrf()).contentType("application/json").content("{\"productId\":1,\"customerName\":\"X\",\"quantity\":1,\"paymentMethod\":\"Tunai\"}")) .andExpect(status().isForbidden());
    }

    @Test void dashboardAndProductsAreAvailableAfterLogin() throws Exception {
        MockHttpSession manager=login("manager","manager123");
        mvc.perform(get("/api/dashboard").session(manager)).andExpect(status().isOk()).andExpect(jsonPath("$.products").isNumber());
        mvc.perform(get("/api/products?page=0&size=1000").session(manager)).andExpect(status().isOk()).andExpect(jsonPath("$.content").isArray());
    }
}
