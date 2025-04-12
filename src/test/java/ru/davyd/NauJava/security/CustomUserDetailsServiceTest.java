package ru.davyd.NauJava.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.davyd.NauJava.entities.User;
import ru.davyd.NauJava.repository.UserRepository;
import ru.davyd.NauJava.service.security.CustomUserDetailsService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CustomUserDetailsServiceTest {

    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testLoadUserByUsername() {
        User testUser = new User();
        testUser.setUsername("testUser");
        testUser.setEmail("test@example.com");
        testUser.setPassword("password");
        testUser = userRepository.save(testUser);

        UserDetails userDetails = userDetailsService.loadUserByUsername("testUser");
        assertNotNull(userDetails);

        userRepository.delete(testUser);
    }

    @Test
    public void testLoadNonExistingUserByUsername() {
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("nonExistingUser"));
    }

    @Test
    public void testLoadAdminUserByUsername() {
        User testAdmin = new User();
        testAdmin.setUsername("testAdmin");
        testAdmin.setEmail("admin@example.com");
        testAdmin.setPassword("password");
        testAdmin.setAdmin(true);
        testAdmin = userRepository.save(testAdmin);

        UserDetails userDetails = userDetailsService.loadUserByUsername("testAdmin");
        assertNotNull(userDetails);

        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN")));

        userRepository.delete(testAdmin);
    }

}
