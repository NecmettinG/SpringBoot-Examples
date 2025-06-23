//DATA ACCESS LAYER. (Spring Data Jpa)
package com.example.demo.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    //Student is the type of the object and Long is the datatype for the primary id. There will be 2 parameters.


    //@Query("SELECT s FROM Student s WHERE s.email = ?1") //This is not SQL. It is called JPQL. Student is our Student class!
    // And it performs same operation just like Optional<Student> findStudentByEmail(String email); But I took it into comment.

    // SELECT * FROM student WHERE email = ? (BOTTOM EXPRESSION WILL TRANSFORM INTO THIS QUERY VIA @Repository.) (? = passed parameter)
    Optional<Student> findStudentByEmail(String email);
    //Optional is a container object that contains and handles potentially null values to avoid "NullPointerException".

} //This interface is responsible for data access to our database, and we can perform CRUD (findAll, deleteAll, save etc.) operations with this repository interface.
//We will use this interface in our service class.
