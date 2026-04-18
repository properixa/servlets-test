import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;

@WebServlet("/fileupload")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 15
)
public class FileUploadServlet extends HttpServlet {

    private String uploadPath;

    @Override
    public void init() throws ServletException {
       uploadPath = getServletContext().getRealPath("/") + "uploads";

       File uploadDir = new File(uploadPath);
       if (!uploadDir.exists()) {
           uploadDir.mkdirs();
       }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       resp.setContentType("text/html");
       try (PrintWriter out = resp.getWriter()) {
           out.println("<form method=\"POST\" enctype=\"multipart/form-data\" action=\"fileupload\">");
           out.println("<input name=\"file\" type=\"file\" />");
           out.println("<button type=\"submit\">Submit</button>");
           out.println("</form>");
       }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        try (PrintWriter out = resp.getWriter()) {
            Part filePart = req.getPart("file");

            if (filePart == null || filePart.getSize() == 0) {
                out.println("Файл не выбран");
                return;
            }

            filePart.write(uploadPath
                    + File.separator + Paths.get(filePart.getSubmittedFileName()).getFileName().toString());

            out.println("Успешно!");
        }
    }
}
