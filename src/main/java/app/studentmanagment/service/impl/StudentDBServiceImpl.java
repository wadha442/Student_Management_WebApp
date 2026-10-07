package app.studentmanagment.service.impl;

import java.util.ArrayList;
import java.util.List;

import app.studentmanagment.dao.impl.StudentDBDAOImpl;
import app.studentmanagment.exception.StudentAlreadyExistsException;
import app.studentmanagment.model.Student;
import app.studentmanagment.service.StudentService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StudentDBServiceImpl
        implements StudentService {

    private StudentDBDAOImpl studentDAO;

    private StudentFileDBServiceImpl studentFileService;

    private static final Logger logger =
            LogManager.getLogger(
                    StudentDBServiceImpl.class
            );


    public StudentDBServiceImpl() {

        studentDAO =
                new StudentDBDAOImpl();

        studentFileService =
                new StudentFileDBServiceImpl();
    }


    /*
     * Add Student
     *
     * Student name can be duplicated.
     *
     * National ID must be unique.
     */
    @Override
    public boolean addStudent(Student student)
            throws StudentAlreadyExistsException {

        try {

            /*
             * Check National ID only.
             */
            Student existingStudent =
                    studentDAO.getStudentByNationalId(
                            student.getNationalId()
                    );


            /*
             * If National ID already exists,
             * throw duplicate exception.
             */
            if (existingStudent != null) {

                throw new StudentAlreadyExistsException(
                        "Student with National ID "
                        + student.getNationalId()
                        + " already exists"
                );
            }


            /*
             * Name is NOT checked.
             *
             * Therefore the same name
             * can be used for another student.
             */
            return studentDAO.addStudent(student);


        } catch (StudentAlreadyExistsException e) {

            /*
             * Do NOT use file fallback
             * when National ID is duplicated.
             */
            throw e;


        } catch (Exception e) {

            /*
             * If database fails,
             * use file storage as fallback.
             */
            logger.warn(
                    "Failed to add student to database. "
                    + "Using file storage.",
                    e
            );

            return studentFileService.addStudent(
                    student
            );
        }
    }


    @Override
    public List<Student> showStudents()
            throws Exception {

        List<Student> students =
                new ArrayList<Student>();


        try {

            logger.info(
                    "Retrieving students from database."
            );


            students =
                    studentDAO.getAllStudent();


            logger.info(
                    "Students retrieved successfully "
                    + "from database."
            );


        } catch (Exception e) {

            logger.error(
                    "Failed to retrieve students "
                    + "from database. "
                    + "Falling back to file.",
                    e
            );


            students =
                    studentFileService.showStudents();
        }


        return students;
    }


    @Override
    public String searchStudent(
            String studentName)
            throws Exception {

        try {

            logger.info(
                    "Searching for student: {}",
                    studentName
            );


            Student student =
                    studentDAO.getStudentByName(
                            studentName
                    );


            if (student == null) {

                logger.warn(
                        "Student not found: {}",
                        studentName
                );

                return "Student not found";


            } else {

                logger.info(
                        "Student found: {}",
                        studentName
                );

                return student.studentInfo();
            }


        } catch (Exception e) {

            logger.error(
                    "Failed to retrieve student "
                    + "from database.",
                    e
            );


            return studentFileService.searchStudent(
                    studentName
            );
        }
    }


    @Override
    public boolean updateStudent(
            String nationalId,
            Student student) {

        try {

            return studentDAO.updateStudent(
                    nationalId,
                    student
            );


        } catch (Exception e) {

            logger.warn(
                    "Failed to update student "
                    + "in database. "
                    + "Using file storage.",
                    e
            );


            return studentFileService.updateStudent(
                    nationalId,
                    student
            );
        }
    }


    @Override
    public boolean deleteStudent(
            String nationalId) {

        try {

            return studentDAO.deleteStudent(
                    nationalId
            );


        } catch (Exception e) {

            logger.warn(
                    "Failed to delete student "
                    + "in database. "
                    + "Using file storage.",
                    e
            );


            return studentFileService.deleteStudent(
                    nationalId
            );
        }
    }
}