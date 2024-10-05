package mk.ukim.finki.eshop.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.eshop.service.CategoryService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet(name="servlet",urlPatterns = "/servlet/category")
public class CategoryServlet extends HttpServlet {
    private final SpringTemplateEngine springTemplateEngine;
    private final CategoryService categoryService;

    public CategoryServlet(SpringTemplateEngine springTemplateEngine, CategoryService categoryService) {
        this.springTemplateEngine = springTemplateEngine;
        this.categoryService = categoryService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange webExchange= JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req,resp);
        WebContext context=new WebContext(webExchange);
        context.setVariable("categories",categoryService.listCategories());
        context.setVariable("ipAddress",req.getRemoteAddr());

        Integer userViews = (Integer) getServletContext().getAttribute("userViews");
        getServletContext().setAttribute("userViews",++userViews);
        context.setVariable("userViews", getServletContext().getAttribute("userViews"));

        springTemplateEngine.process("categories.html",
                context,
                resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name=req.getParameter("cname");
        String description=req.getParameter("cdesc");
        this.categoryService.create(name,description);
        resp.sendRedirect("/servlet/category");
    }
}
