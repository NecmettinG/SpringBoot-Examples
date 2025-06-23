package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static java.time.Month.FEBRUARY;
import static java.time.Month.JULY;

@Configuration
public class StudentConfig {

    @Bean //this is a bean. I think this is for auto instantiation for the object called repository. DEPENDENCY INJECTION!
    CommandLineRunner commandLineRunner(StudentRepository repository){ //this is a special type, unique type of springboot.
        //Also, this CommandLineRunner will be automatically executed and save student objects to db when we start the program.
        //We basically used CRUD operation for saving students. We inherited CRUD operations from StudentRepository.

        return args -> {
            Student neco = new Student( //ctrl+alt+v ile direkt object'in adı name'deki değer oluyor. Bu bir shortcut.
                    //1L, //The id attribute is not needed because we declared a sequence generator for automatic id values.
                    "neco",
                    //22, //we used @Transient for age attribute. We won't see an age column in database.
                    LocalDate.of(2002, JULY, 6), //Month.JULY' a alt+enter yapıp static import yaptım.
                    "gedikligaming61@gmail.com"
            );

            Student chris = new Student(
                    "chris",
                    //23, //we used @Transient for age attribute. We won't see an age column in database.
                    LocalDate.of(1999, FEBRUARY, 7),
                    "chris@gmail.com"
            );

            repository.saveAll( //this saves a list of student objects.
                    List.of(neco, chris)
            );
        };
    }
}
