package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neutec.blog.Main;
import com.neutec.blog.model.login.LoginRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testLogin() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("admin@gmail.com");
        loginRequest.setPassword("Aaa123123123");
        mockMvc.perform(post("/common/login", loginRequest)
                .content(objectMapper.writeValueAsString(loginRequest))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.statusCode").value("0"));
    }

    @Test
    public void testLoginFailEmailFail() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("admin");
        loginRequest.setPassword("Aaa123123123");
        mockMvc.perform(post("/common/login", loginRequest)
                .content(objectMapper.writeValueAsString(loginRequest))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.statusCode").value("-1"));
    }

    @Test
    public void testLoginFailPasswordFail() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("admin@gmail.com");
        loginRequest.setPassword("Aaa");
        mockMvc.perform(post("/common/login", loginRequest)
                .content(objectMapper.writeValueAsString(loginRequest))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.statusCode").value("-1"));


        loginRequest.setPassword("Aaa123123123123");
        mockMvc.perform(post("/common/login", loginRequest)
                .content(objectMapper.writeValueAsString(loginRequest))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.statusCode").value("-1"));
    }


}
