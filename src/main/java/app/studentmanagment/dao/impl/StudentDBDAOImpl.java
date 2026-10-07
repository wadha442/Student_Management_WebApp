package app.studentmanagment.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.studentmanagment.model.Student;
import app.studentmanagment.util.DBConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StudentDBDAOImpl {

    private static final Logger logger =
            LogManager.getLogger(StudentDBDAOImpl.class);


    /*
     * Add Student
     *
     * National ID must be unique.
     * Student name can be duplicated.
     */
    public boolean addStudent(Student student)
            throws SQLException {

        String sql =
                "INSERT INTO Student "
                + "(national_id, Student_name, Student_age, Student_grade) "
                + "VALUES (?, ?, ?, ?)";


        logger.info(
                "Starting addStudent. Student name: {}",
                student.getName()
        );


        try (
            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    student.getNationalId()
            );

            statement.setString(
                    2,
                    student.getName()
            );

            statement.setInt(
                    3,
                    student.getAge()
            );

            statement.setDouble(
                    4,
                    student.getGrade()
            );


            int rowsAffected =
                    statement.executeUpdate();


            if (rowsAffected > 0) {

                logger.info(
                        "Student saved successfully. National ID: {}",
                        student.getNationalId()
                );

                return true;
            }
        }


        return false;
    }


    /*
     * Get Student By National ID
     *
     * Used to check if the National ID
     * already exists before inserting.
     */
    public Student getStudentByNationalId(
            String nationalId)
            throws SQLException {

        String sql =
                "SELECT Student_id, national_id, "
                + "Student_name, Student_age, Student_grade "
                + "FROM Student "
                + "WHERE national_id = ?";


        try (
            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    nationalId
            );


            try (
                ResultSet resultSet =
                        statement.executeQuery()
            ) {

                if (resultSet.next()) {

                    return new Student(
                            resultSet.getString(
                                    "Student_name"
                            ),

                            resultSet.getString(
                                    "national_id"
                            ),

                            resultSet.getInt(
                                    "Student_age"
                            ),

                            resultSet.getDouble(
                                    "Student_grade"
                            ),

                            resultSet.getInt(
                                    "Student_id"
                            )
                    );
                }
            }
        }


        return null;
    }


    /*
     * Get All Students
     */
    public List<Student> getAllStudent()
            throws SQLException {

        List<Student> students =
                new ArrayList<Student>();


        String sql =
                "SELECT Student_id, national_id, "
                + "Student_name, Student_age, Student_grade "
                + "FROM Student";


        try (
            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            ResultSet resultSet =
                    statement.executeQuery()
        ) {

            while (resultSet.next()) {

                Student student =
                        new Student(

                                resultSet.getString(
                                        "Student_name"
                                ),

                                resultSet.getString(
                                        "national_id"
                                ),

                                resultSet.getInt(
                                        "Student_age"
                                ),

                                resultSet.getDouble(
                                        "Student_grade"
                                ),

                                resultSet.getInt(
                                        "Student_id"
                                )
                        );


                students.add(student);
            }
        }


        return students;
    }


    /*
     * Get Student By Name
     *
     * Name is NOT unique.
     * Therefore duplicate names are allowed.
     */
    public Student getStudentByName(
            String studentName)
            throws SQLException {

        String sql =
                "SELECT TOP 1 Student_id, national_id, "
                + "Student_name, Student_age, Student_grade "
                + "FROM Student "
                + "WHERE Student_name = ?";


        try (
            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    studentName
            );


            try (
                ResultSet resultSet =
                        statement.executeQuery()
            ) {

                if (resultSet.next()) {

                    return new Student(
                            resultSet.getString(
                                    "Student_name"
                            ),

                            resultSet.getString(
                                    "national_id"
                            ),

                            resultSet.getInt(
                                    "Student_age"
                            ),

                            resultSet.getDouble(
                                    "Student_grade"
                            ),

                            resultSet.getInt(
                                    "Student_id"
                            )
                    );
                }
            }
        }


        return null;
    }


    /*
     * Update Student
     */
    public boolean updateStudent(
            String nationalId,
            Student student)
            throws SQLException {

        String sql =
                "UPDATE Student "
                + "SET Student_name = ?, "
                + "Student_age = ?, "
                + "Student_grade = ? "
                + "WHERE national_id = ?";


        try (
            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    student.getName()
            );

            statement.setInt(
                    2,
                    student.getAge()
            );

            statement.setDouble(
                    3,
                    student.getGrade()
            );

            statement.setString(
                    4,
                    nationalId
            );


            int rowsAffected =
                    statement.executeUpdate();


            return rowsAffected > 0;
        }
    }


    /*
     * Delete Student
     */
    public boolean deleteStudent(
            String nationalId)
            throws SQLException {

        String sql =
                "DELETE FROM Student "
                + "WHERE national_id = ?";


        try (
            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    nationalId
            );


            int rowsAffected =
                    statement.executeUpdate();


            return rowsAffected > 0;
        }
    }
}