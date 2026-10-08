package app.studentmanagment.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.studentmanagment.service.StudentService;
import app.studentmanagment.service.impl.StudentDBServiceImpl;

public class SearchStudentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private StudentService studentService;


    @Override
    public void init() throws ServletException {

        studentService = new StudentDBServiceImpl();
    }


    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        response.setContentType(
                "text/html;charset=UTF-8"
        );


    
        // Get Search Data
        

        String studentName =
                request.getParameter("studentName");


        String language =
                request.getParameter("language");


        if (language == null ||
                (!language.equals("ar")
                && !language.equals("en"))) {

            language = "ar";
        }


        boolean arabic =
                language.equals("ar");


        PrintWriter out =
                response.getWriter();


        try {

          
            // Search Student
          

            String result =
                    studentService.searchStudent(
                            studentName
                    );


           
            // Translate Result
         

            String displayResult = result;


            if (arabic) {

                displayResult = result
                        .replace(
                                "Student Name:",
                                "اسم الطالب:"
                        )
                        .replace(
                                "National ID:",
                                "رقم الهوية الوطنية:"
                        )
                        .replace(
                                "Age:",
                                "العمر:"
                        )
                        .replace(
                                "Grade:",
                                "الدرجة:"
                        )
                        .replace(
                                "Grade Level:",
                                "المستوى:"
                        )
                        .replace(
                                "Status:",
                                "الحالة:"
                        )
                        .replace(
                                "Excellent",
                                "ممتاز"
                        )
                        .replace(
                                "Very Good",
                                "جيد جدًا"
                        )
                        .replace(
                                "Good",
                                "جيد"
                        )
                        .replace(
                                "Passed",
                                "ناجح"
                        )
                        .replace(
                                "Failed",
                                "راسب"
                        )
                        .replace(
                                "Pass",
                                "ناجح"
                        )
                        .replace(
                                "Fail",
                                "راسب"
                        );
            }


           
            // HTML
         

            out.println("<!DOCTYPE html>");


            out.println(
                    "<html lang='"
                    + language
                    + "' dir='"
                    + (arabic ? "rtl" : "ltr")
                    + "'>"
            );


            out.println("<head>");


            out.println(
                    "<meta charset='UTF-8'>"
            );


            out.println(
                    "<meta name='viewport' "
                    + "content='width=device-width, "
                    + "initial-scale=1.0'>"
            );


            
            // Title
          

            out.println(
                    "<title>"
                    + (arabic
                        ? "البحث عن طالب"
                        : "Search Student")
                    + "</title>"
            );


            // CSS
          

            out.println(
                    "<link rel='stylesheet' "
                    + "href='css/style.css?v=2'>"
            );


            out.println("</head>");


            out.println("<body>");


         
            // Header
            

            out.println(
                    "<header class='header'>"
            );


            out.println(
                    "<div class='header-container'>"
            );


            out.println(
                    "<div class='brand'>"
            );


            out.println(
                    "<h3>"
                    + (arabic
                        ? "نظام إدارة الطلاب والمقررات"
                        : "Student & Course Management System")
                    + "</h3>"
            );


            out.println(
                    "<span>"
                    + (arabic
                        ? "البحث عن طالب"
                        : "Search Student")
                    + "</span>"
            );


            out.println("</div>");


            out.println("</div>");


            out.println("</header>");


          
            // Page Header
            

            out.println(
                    "<section class='page-header'>"
            );


            out.println(
                    "<div class='page-header-content'>"
            );


            out.println("<div>");


            out.println(
                    "<span>"
                    + (arabic
                        ? "إدارة النظام"
                        : "System Management")
                    + "</span>"
            );


            out.println(
                    "<h1>"
                    + (arabic
                        ? "نتيجة البحث"
                        : "Search Result")
                    + "</h1>"
            );


            out.println(
                    "<p>"
                    + (arabic
                        ? "نتيجة البحث عن الطالب"
                        : "Search result for the student")
                    + "</p>"
            );


            out.println("</div>");


            // =========================
            // Back Button
            // =========================

            out.println(
                    "<a href='students.html' "
                    + "class='back-button'>"
            );


            out.println(
                    arabic
                    ? "العودة للطلاب"
                    : "Back to Students"
            );


            out.println("</a>");


            out.println("</div>");


            out.println("</section>");


            // =========================
            // Search Result
            // =========================

            out.println(
                    "<main class='students-container'>"
            );


            out.println(
                    "<section class='content-card'>"
            );


            out.println(
                    "<div class='section-title'>"
            );


            out.println("<div>");


            out.println(
                    "<h2>"
                    + (arabic
                        ? "نتيجة البحث"
                        : "Search Result")
                    + "</h2>"
            );


            out.println(
                    "<span>"
                    + (arabic
                        ? "بيانات الطالب"
                        : "Student Information")
                    + "</span>"
            );


            out.println("</div>");


            out.println("</div>");


            // =========================
            // Student Result
            // =========================

            out.println(
                    "<div class='search-result'>"
            );


            out.println(
                    "<div class='student-result-details'>"
                    + displayResult.replace(
                            ", ",
                            "<br>"
                    )
                    + "</div>"
            );


            out.println("</div>");


            // =========================
            // Back Button
            // =========================

            out.println(
                    "<div class='form-actions'>"
            );


            out.println(
                    "<a href='students.html' "
                    + "class='back-button'>"
            );


            out.println(
                    arabic
                    ? "العودة للطلاب"
                    : "Back to Students"
            );


            out.println("</a>");


            out.println("</div>");


            out.println("</section>");


            out.println("</main>");


            // =========================
            // Footer
            // =========================

            out.println(
                    "<footer class='footer'>"
            );


            out.println(
                    "<span>"
                    + (arabic
                        ? "نظام إدارة الطلاب والمقررات © 2026"
                        : "Student & Course Management System © 2026")
                    + "</span>"
            );


            out.println("</footer>");


            out.println("</body>");


            out.println("</html>");


        } catch (Exception e) {

            e.printStackTrace();


            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    arabic
                    ? "حدث خطأ أثناء البحث عن الطالب"
                    : "Failed to search for student"
            );
        }
    }
}