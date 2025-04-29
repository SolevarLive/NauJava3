package ru.davyd.NauJava.task_8;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Интеграционные тесты для проверки функционала регистрации, входа и выхода пользователя
 * в веб-приложении с использованием Selenium WebDriver
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginTest {

    @LocalServerPort
    private int port;

    private static WebDriver driver;

    /**
     * Инициализация WebDriver перед запуском всех тестов
     * Выполняется один раз, настраивает ChromeDriver с необходимыми опциями:
     * headless режим, размер окна, игнорирование ошибок сертификатов и неявные ожидания.
     */
    @BeforeAll
    public static void setup() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1200");
        options.addArguments("--ignore-certificate-errors");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


    /**
     * Тест успешной регистрации и последующего входа пользователя
     * Проверяет, что после логина пользователь перенаправляется на страницу /home
     * и отображается приветственное сообщение с именем пользователя
     */
    @Test
    public void testSuccessfulLogin() {
        driver.get("http://localhost:" + port + "/register");

        driver.findElement(By.id("username")).sendKeys("testuser");
        driver.findElement(By.id("email")).sendKeys("testuser@example.com");
        driver.findElement(By.id("password")).sendKeys("password123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        driver.get("http://localhost:" + port + "/login");

        driver.findElement(By.id("username")).sendKeys("testuser");
        driver.findElement(By.id("password")).sendKeys("password123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        assertTrue(driver.getCurrentUrl().contains("/home"));

        String welcomeText = driver.findElement(By.tagName("h1")).getText();
        assertTrue(welcomeText.contains("Добро пожаловать, testuser!"));
    }

    /**
     * Тест выхода пользователя из системы
     * Проверяет, что после выхода происходит перенаправление на страницу логина
     * и отображается форма входа
     */
    @Test
    public void testLogout() {

        driver.get("http://localhost:" + port + "/login");

        driver.findElement(By.id("username")).sendKeys("testuser");
        driver.findElement(By.id("password")).sendKeys("password123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        assertTrue(driver.getCurrentUrl().contains("/home"));

        driver.findElement(By.xpath("//button[text()='Выйти']")).click();

        assertTrue(driver.getCurrentUrl().contains("/login"));
        assertTrue(driver.getPageSource().contains("Вход в систему"));
    }

    @Test
    public void testFailedLogin() {
        driver.get("http://localhost:" + port + "/login");

        driver.findElement(By.id("username")).sendKeys("wronguser");
        driver.findElement(By.id("password")).sendKeys("wrongpassword");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Проверяем сообщение об ошибке
        assertTrue(driver.getPageSource().contains("Неверное имя пользователя или пароль"));
        assertTrue(driver.getCurrentUrl().contains("/login"));
    }
}
