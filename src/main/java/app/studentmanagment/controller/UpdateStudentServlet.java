package app.studentmanagment.controller;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.studentmanagment.model.Student;
import app.studentmanagment.service.StudentService;
import app.studentmanagment.service.impl.StudentDBServiceImpl;

public class UpdateStudentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private StudentService studentService;

    @Override
    public void init() throws ServletException {
        studentService = new StudentDBServiceImpl();
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String originalNationalId =
                request.getParameter("originalNationalId");

        String name =
                request.getParameter("name");

        String nationalId =
                request.getParameter("nationalId");

        String ageValue =
                request.getParameter("age");

        String gradeValue =
                request.getParameter("grade");

        try {
            if (originalNationalId == null
                    || originalNationalId.trim().isEmpty()
                    || name == null
                    || name.trim().isEmpty()
                    || nationalId == null
                    || nationalId.trim().isEmpty()
                    || ageValue == null
                    || gradeValue == null) {

                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Required student information is missing"
                );
                return;
            }

            int age = Integer.parseInt(ageValue);
            double grade = Double.parseDouble(gradeValue);

            Student student = new Student(
                    name,
                    nationalId,
                    age,
                    grade
            );

            boolean updated = studentService.updateStudent(
                    originalNationalId,
                    student
            );

            if (updated) {
                response.sendRedirect("students.html?updated=true");
            } else {
                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Failed to update student"
                );
            }

        } catch (NumberFormatException e) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Age and grade must be valid numbers"
            );

        } catch (IllegalArgumentException e) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    e.getMessage()
            );
        }
    }
}
