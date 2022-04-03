package org.example.listener;

import com.google.gson.Gson;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.UnavailableException;
import org.example.attribute.ContexAttributes;
import org.example.controller.AccountController;
import org.example.controller.UserController;
import org.example.exception.InitializationException;
import org.example.handler.Handler;
import org.example.repository.AccountRepository;
import org.example.repository.UserRepository;
import org.example.service.AccountService;
import org.example.service.UserService;
import org.jdbi.v3.core.Jdbi;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class ServletContextInitializationDestroyListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {

            InitialContext cxt = new InitialContext();

            DataSource ds = (DataSource) cxt.lookup("java:/comp/env/jdbc/ds");

            if (ds == null) {
                throw new InitializationException("Data source not found!");
            }

            Jdbi jdbi = Jdbi.create(ds);
            UserRepository userRepository = new UserRepository(jdbi);
            final PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
            final UserService userService = new UserService(userRepository, passwordEncoder);
            final Gson gson = new Gson();

            final UserController userController = new UserController(userService, gson);
            final AccountRepository accountRepository = new AccountRepository(jdbi);
            final AccountService accountService = new AccountService(accountRepository);
            final AccountController accountController = new AccountController(accountService, gson);

            final Map<String, Handler> routes = new HashMap<>();
            routes.put("/users.getAll", userController::getAll);
            routes.put("/users.register", userController::register);
            routes.put("/users.login", userController::login);
            routes.put("/accounts.getMy", accountController::getAll);
            routes.put("/accounts.getById", accountController::getById);

            final ServletContext servletContext = sce.getServletContext();
            servletContext.setAttribute(ContexAttributes.ROUTES_ATTR, routes);
            servletContext.setAttribute(ContexAttributes.AUTHENTICATOR_ATTR, userService);

        } catch (Exception e){
            throw new InitializationException(e.getMessage());
        }
    }
}
