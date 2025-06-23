//SERVICE LAYER.
package com.example.demo.student;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service /*We declared this class as a service class with @Service annotation. That means this class has to be instantiated. Has to be a spring bean.
In the controller class, we used @Autowired for automatic instantiation. But @Autowired has to know the bean to instantiate. Without @Service, We'll have error.
@Service annotation declaring this class as a service bean, and we can create instances from this class automatically in controller class.*/
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository){

        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents(){

//        return List.of(
//                new Student(
//                        1L,
//                        "neco",
//                        22,
//                        LocalDate.of(2002, Month.JULY, 6),
//                        "gedikligaming61@gmail.com"
//                )
//        );
//right now, we don't need to return a list of an object like this. We are now able to use CRUD operation(findAll, deleteAll ,save etc.) methods via StudentRepository interface.
        return studentRepository.findAll(); //This returns a list of all objects in database. We don't need to execute the statement above. We can fetch all students from db.
    }

    public void addNewStudent(Student student) {

        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());

        if(studentOptional.isPresent()){

            throw new IllegalStateException("email taken");
        }

        studentRepository.save(student);

    }

    public void deleteStudent(Long studentId) { // for deleting a specific student from database.

        boolean exists = studentRepository.existsById(studentId); // for checking if the student with particular id exists.

        if(!exists){ // if student does not exist:

            throw new IllegalStateException("There is no student with this id:" + studentId);
            //throw exception if student doesn't exist.
        }

        studentRepository.deleteById(studentId); // delete the particular student from database.
    }


    @Transactional //we don't need to write query because of @Transactional annotation. (JPA özelliği)
    // It is enough to change the values of attributes via setters. Thanks to the @Entity annotation.
    public void updateStudent(Long studentId, String name, String email) {

        Student student = studentRepository.findById(studentId) // We fetched a specific student from database according to id.
                // If it doesn't exist, Throw exception.
                .orElseThrow(()-> new IllegalStateException("There is no student with this id:" + studentId));

        if(name != null && name.length() > 0 && !Objects.equals(student.getName(), name)){
            //If you pass empty value in json, it will be considered null.
            //If you are supposed to compare two strings, use Objects.equals(string1, string2)

            student.setName(name); //change the value of name attribute using setters. No need to write query for database for this change.
        }

        if(email != null && email.length() > 0 && !Objects.equals(student.getEmail(), email)){

            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
            //We used Optional for containing possible students or null values to avoid "NullPointerException".

            if(studentOptional.isPresent()){ //if student with particular id exists inside of studentOptional container:

                throw new IllegalStateException("the email you entered is same with the present one");
                //Throw exception when student with particular id exists.
            }

            student.setEmail(email); //change the value of email attribute using setters. No need to write query for database for this change.
        }
    }
}
