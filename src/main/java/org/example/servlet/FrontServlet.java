package org.example.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.UnavailableException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.controller.UserController;
import org.example.exception.InitializationException;
import org.example.handler.Handler;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.jdbi.v3.core.Jdbi;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//http://localhost:8080
public class FrontServlet extends HttpServlet {
    private final Map<String, Handler> routes = new HashMap<>();

    @Override
    public void init() throws ServletException {
        try {


            InitialContext cxt = new InitialContext();

            DataSource ds = (DataSource) cxt.lookup("java:/comp/env/jdbc/ds");

            if (ds == null) {
                throw new InitializationException("Data source not found!");
            }

            Jdbi jdbi = Jdbi.create(ds);
            UserRepository userRepository = new UserRepository(jdbi);
            final UserService userService = new UserService(userRepository);
            final Gson gson = new Gson();
            final UserController userController = new UserController(userService, gson);
            routes.put("/users.getAll", userController::getAll);



        } catch (Exception e){
            throw new UnavailableException(e.getMessage());
        }
    }

    // http://localhost:8080/users.getAll
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
