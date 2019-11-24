package servlet;

import model.User;
import service.UserService;
import util.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class LoginServlet extends HttpServlet {

    private UserService service = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getPage(response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = new User(email, password);
        service.authUser(user);

        getPage(response);
    }

    private void getPage(HttpServletResponse response) throws IOException {
        response.getWriter()
                .println(PageGenerator.getInstance().getPage("authPage.html", Collections.emptyMap()));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}