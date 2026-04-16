import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Random;

public class PasswordServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head><link rel='stylesheet' href='style.css'></head>");
        out.println("<body>");

        out.println("<div class='result'>");

        if (action.equals("generate")) {

            int length = Integer.parseInt(request.getParameter("length"));

            String chars = "";

            if (request.getParameter("upper") != null)
                chars += "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            if (request.getParameter("lower") != null)
                chars += "abcdefghijklmnopqrstuvwxyz";
            if (request.getParameter("numbers") != null)
                chars += "0123456789";
            if (request.getParameter("symbols") != null)
                chars += "!@#$%^&*";

            if (chars.isEmpty()) {
                out.println("<h3>Please select at least one option!</h3>");
            } else {
                Random rand = new Random();
                String password = "";

                for (int i = 0; i < length; i++) {
                    password += chars.charAt(rand.nextInt(chars.length()));
                }

                // Strength check
                int score = 0;
                if (password.length() >= 8) score++;
                if (password.matches(".*[A-Z].*")) score++;
                if (password.matches(".*[a-z].*")) score++;
                if (password.matches(".*[0-9].*")) score++;
                if (password.matches(".*[!@#$%^&*].*")) score++;

                String strengthClass;
                String strengthText;

                if (score <= 2) {
                    strengthText = "Weak";
                    strengthClass = "weak";
                } else if (score <= 4) {
                    strengthText = "Medium";
                    strengthClass = "medium";
                } else {
                    strengthText = "Strong";
                    strengthClass = "strong";
                }

                out.println("<h2>Generated Password</h2>");
                out.println("<h3>" + password + "</h3>");
                out.println("<p>Strength: <span class='" + strengthClass + "'>" + strengthText + "</span></p>");
            }

        } else if (action.equals("check")) {

            String password = request.getParameter("password");

            int score = 0;

            if (password.length() >= 8) score++;
            if (password.matches(".*[A-Z].*")) score++;
            if (password.matches(".*[a-z].*")) score++;
            if (password.matches(".*[0-9].*")) score++;
            if (password.matches(".*[!@#$%^&*].*")) score++;

            String strengthClass;
            String strengthText;

            if (score <= 2) {
                strengthText = "Weak";
                strengthClass = "weak";
            } else if (score <= 4) {
                strengthText = "Medium";
                strengthClass = "medium";
            } else {
                strengthText = "Strong";
                strengthClass = "strong";
            }

            out.println("<h2>Password Strength</h2>");
            out.println("<p>Strength: <span class='" + strengthClass + "'>" + strengthText + "</span></p>");
        }

        out.println("<a href='index.html' class='back-btn'>Go Back</a>");
        out.println("</div>");
        out.println("</body></html>");
    }
}