//CLASS
package com.example.demo.student;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;

@Entity //for hibernating the objects to database. We mapped this student class to our table in database.
@Table(name = "students") //for creating table for database and the name of the table is students.
public class Student {
    @Id // Annotation for primary key.
    @SequenceGenerator( //primary id value generator.
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1 //I do not know what is it doing.
    )
    @GeneratedValue( // sequence strategy for primary id.
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    private long id;
    private String name;

    @Transient //With this annotation, we said to database that there is no need to create a column for age attribute.
                // We won't enter an age value manually in constructor. We will use LocalDate for auto age calculation.
                //And we will be able to see this age attribute in json, NOT IN DATABASE BECAUSE THERE IS NO COLUMN FOR AGE!
    private int age;
    private LocalDate date_of_birth; //Localdate datatype is for datetimes.
    private String email;

    public Student(){ //You have to create an empty constructor to avoid a certain error type!!!

    }

//    public Student(long id, //id is not needed.
//                   String name,
//                   //int age, //delete the age attributes from constructors.
//                   LocalDate date_of_birth,
//                   String email) {
//
//        this.id = id; //id is not needed.
//        this.name = name;
//        //this.age = age;
//        this.date_of_birth = date_of_birth;
//        this.email = email;
//
//    }

    public Student(String name, //This constructor will work when we create instances in StudentConfig class.
                                //id will be created automatically via sequence generator and generated value annotations.

                   //int age, //delete the age attributes from constructors.
                   LocalDate date_of_birth,
                   String email) {

        this.name = name;
        //this.age = age;
        this.date_of_birth = date_of_birth;
        this.email = email;
    }

    //These getters are for json output in our local url. The json output is not related with database.
    //Because there is no column for age attribute in database while we can see the age attribute in json in our browser.
    public long getId(){
        return id;
    }

    public void setId(long id){

        this.id = id;
    }


    public String getName(){

        return name;
    }

    public void setName(String name){

        this.name = name;
    }

    public int getAge(){

        return Period.between(this.date_of_birth, LocalDate.now()).getYears();
        //we will automatically calculate the ages for json via date_of_birth attribute.
    }

    public void setAge(int age){

        this.age = age;
    }

    public LocalDate getDate_of_birth(){

        return date_of_birth;
    }

    public void setDate_of_birth(LocalDate date_of_birth){

        this.date_of_birth = date_of_birth;
    }

    public String getEmail(){

        return email;
    }

    public void setEmail(String email){

        this.email = email;
    }

    public String toString(){

        return "Student{" +
                "id=" + id +
                ", name= " + name + '\'' +
                ", email= " + email + '\'' +
                ", age= " + age +
                ", date of birth= " + date_of_birth +
                '}';
    }


}
