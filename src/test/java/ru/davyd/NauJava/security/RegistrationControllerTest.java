package ru.davyd.NauJava.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.davyd.NauJava.entities.User;
import ru.davyd.NauJava.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testShowRegistrationForm() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk());
    }

    @Test
    public void testRegisterUser() throws Exception {
        mockMvc.perform(post("/register")
                        .param("username", "newUser")
                        .param("email", "new@example.com")
                        .param("password", "password"))
                .andExpect(status().isFound());

        User registeredUser = userRepository.findByUsername("newUser").orElse(null);
        if (registeredUser != null) {
            userRepository.delete(registeredUser);
        }
    }

    @Test
    public void testRegisterAdminUser() throws Exception {
        mockMvc.perform(post("/register")
                        .param("username", "newAdmin")
                        .param("email", "admin@example.com")
                        .param("password", "password")
                        .param("isAdmin", "true"))
                .andExpect(status().isFound());

        User registeredAdmin = userRepository.findByUsername("newAdmin").orElse(null);
        if (registeredAdmin != null) {
            assertTrue(registeredAdmin.isAdmin());
            userRepository.delete(registeredAdmin);
        }
    }
}
