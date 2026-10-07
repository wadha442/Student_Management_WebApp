package app.studentmanagment.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.studentmanagment.exception.StudentAlreadyExistsException;
import app.studentmanagment.model.Student;
import app.studentmanagment.service.StudentService;
import app.studentmanagment.service.impl.StudentDBServiceImpl;

public class StudentServlet
        extends HttpServlet {

    private static final long serialVersionUID =
            1L;


    private StudentService studentService;


    @Override
    public void init()
            throws ServletException {

        studentService =
                new StudentDBServiceImpl();
    }


    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {


        request.setCharacterEncoding(
                "UTF-8"
        );


        String name =
                request.getParameter("name");


        String nationalId =
                request.getParameter("nationalId");


        String ageValue =
                request.getParameter("age");


        String gradeValue =
                request.getParameter("grade");


        try {

            int age =
                    Integer.parseInt(
                            ageValue
                    );


            double grade =
                    Double.parseDouble(
                            gradeValue
                    );


            Student student =
                    new Student(
                            name,
                            nationalId,
                            age,
                            grade
                    );


            boolean added =
                    studentService.addStudent(
                            student
                    );


            if (added) {

                /*
                 * Student added successfully.
                 */
                response.sendRedirect(
                        "students.html?success=true"
                );


            } else {

                response.sendError(
                        HttpServletResponse
                                .SC_INTERNAL_SERVER_ERROR,
                        "Failed to add student"
                );
            }


        } catch (
                StudentAlreadyExistsException e) {

            /*
             * Duplicate National ID.
             */
            response.sendError(
                    HttpServletResponse
                            .SC_BAD_REQUEST,
                    e.getMessage()
            );


        } catch (
                NumberFormatException e) {

            response.sendError(
                    HttpServletResponse
                            .SC_BAD_REQUEST,
                    "Age and grade must be valid numbers"
            );


        } catch (
                IllegalArgumentException e) {

            response.sendError(
                    HttpServletResponse
                            .SC_BAD_REQUEST,
                    e.getMessage()
            );


        } catch (Exception e) {

            response.sendError(
                    HttpServletResponse
                            .SC_INTERNAL_SERVER_ERROR,
                    "An error occurred while adding the student"
            );
        }
    }
}