package app.studentmanagment.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.studentmanagment.model.Student;
import app.studentmanagment.service.StudentService;
import app.studentmanagment.service.impl.StudentDBServiceImpl;

public class ShowStudentsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private StudentService studentService;

	@Override
	public void init() throws ServletException {

		studentService = new StudentDBServiceImpl();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json;charset=UTF-8");

		PrintWriter out = response.getWriter();

		try {

			List<Student> students = studentService.showStudents();

			out.print("[");

			for (int i = 0; i < students.size(); i++) {

				Student student = students.get(i);

				out.print("{");

				out.print("\"name\":\"" + student.getName() + "\",");

				out.print("\"nationalId\":\"" + student.getNationalId() + "\",");

				out.print("\"age\":" + student.getAge() + ",");

				out.print("\"grade\":" + student.getGrade());

				out.print("}");

				
				if (i < students.size() - 1) {
					out.print(",");
				}
			}

			out.print("]");

		} catch (Exception e) {

			e.printStackTrace();

			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to retrieve students");
		}
	}
}