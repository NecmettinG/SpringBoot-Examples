//API LAYER.
package com.example.demo.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/student") // we are declaring the url of our api with this annotation.
// localhost:8080/api/v1/student is the address.
public class StudentController {

    private final StudentService studentService; // we created an object of StudentService class as an attribute of controller.

    @Autowired /*We are automatically creating an instance of the class that is passed as a parameter. We haven't created studentService instance outside of this class.
    In common way, we are supposed to create an instance called studentService outside of this controller class and pass it into this constructor, but we didn't.
    We will create it automatically with using @Autowired annotation. And now we have an instance called studentService. This process is called dependency injection.*/
    public StudentController(StudentService studentService){ //constructor of this class. studentService object is created with using @Autowired.

        this.studentService = studentService; //attribute of this class which is an object of student service class.
    }
    @GetMapping
    public List<Student> getStudents(){ //Required annotation for Get request.

        return studentService.getStudents(); //using getStudents() method that is inside of student service class.
    }

    @PostMapping
    public void registerNewStudent(@RequestBody Student student){
        //we will map our json, that comes from post request, to student. @RequestBody does this job.


        studentService.addNewStudent(student);
    }
    @DeleteMapping(path = "{studentId}") // the address of this delete request is localhost8080:api/v1/student/{studentId}
    /* example: If we want to delete the student with the id 1; the address will be localhost8080:api/v1/student/1
    (student named neco in StudentConfig)*/

    public void deleteStudent(@PathVariable("studentId") Long studentId){ // mapping path variable with studentId Long variable.

        studentService.deleteStudent(studentId);
        // calling the deleteStudent method from StudentService and passing the mapped variable as parameter.
    }


    @PutMapping(path = "{studentId}")
    public void updateStudent(@PathVariable("studentId") Long studentId,
                              @RequestParam(required = false) String name, // it will take the value of key "name" in json.
                              @RequestParam(required = false) String email){ // it will take the value of key "email" in json.
        //@RequestParam will take the values of keys "name" and "email".
        /*Imagine We are supposed to change the name and email of the student with id 2. (his name is chris in StudentConfig).
        When we consider @PathVariable and @RequestParam annotations, the url will be:
        http://localhost:8080/api/v1/student/2?name=metin&email=metin@gmail.com
        Now, The student with id 2 will have the values metin for name attribute and column, metin@gmail.com for email attribute and column.
         */

        studentService.updateStudent(studentId, name, email); //we will pass these values into updateStudent method in StudentService.
    }

}
