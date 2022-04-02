package org.example.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.UnavailableException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//http://localhost:8080
public class FrontServlet extends HttpServlet {
    private Map<String, Handler> routes;

    @Override
    public void init() throws ServletException {
        routes = (Map<String, Handler>) getServletContext().getAttribute(ContexAttributes.ROUTES_ATTR);

//        try {
//
//
//            InitialContext cxt = new InitialContext();
//
//            DataSource ds = (DataSource) cxt.lookup("java:/comp/env/jdbc/ds");
//
//            if (ds == null) {
//                throw new InitializationException("Data source not found!");
//            }
//
//            Jdbi jdbi = Jdbi.create(ds);
//            UserRepository userRepository = new UserRepository(jdbi);
//            final PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
//            final UserService userService = new UserService(userRepository, passwordEncoder);
//            final Gson gson = new Gson();
//
//            final UserController userController = new UserController(userService, gson);
//            final AccountRepository accountRepository = new AccountRepository(jdbi);
//            final AccountService accountService = new AccountService(accountRepository);
//            final AccountController accountController = new AccountController(accountService, gson);
//
//            routes.put("/users.getAll", userController::getAll);
//            routes.put("/users.register", userController::register);
//            routes.put("/accounts.getMy", accountController::getAll);
//            routes.put("/accounts.getById", accountController::getById);
//
//
//        } catch (Exception e){
//            throw new UnavailableException(e.getMessage());
//        }
    }

    // http://localhost:8080/users.getAll
    // https://127.0.0.1:8443/users.getAll
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //resp.getWriter().write("Hello world");
        final String url = req.getRequestURI();
        final Handler handler = routes.get(url);
        if (handler != null){
            try {
                handler.handle(req, resp);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }
}
