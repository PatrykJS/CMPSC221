package gpa_calculator;

import java.util.Scanner;

/**
 * @author Patryk Smith
 * @version 1.0
 */

/*
        Update with:
        File I/O
        Set Grades
        Semester GPA
        GUI
*/
public class GPA_Calculator {

    public static void main(String[] args) {
        int NUM_COURSES = 20;
        Course[] course = new Course[NUM_COURSES-1];
        Scanner scnr = new Scanner(System.in);
       
        for(int i = 0; i < NUM_COURSES-1; i++){
            System.out.println("Enter Course information:");
            System.out.print("NAME : ");
            String n = scnr.next();
            System.out.print("GRADE : ");
            String temp = scnr.next();
            float g = 0.0f;
            if(temp.toLowerCase().equals("a+")){
                g = 4.0f;
            }else if(temp.toLowerCase().equals("a")){
                g = 4.0f;
            }else if(temp.toLowerCase().equals("a-")){
                g = 3.67f;
            }else if(temp.toLowerCase().equals("b+")){
                g = 3.33f;
            }else if(temp.toLowerCase().equals("b")){
                g = 3.0f;
            }else if(temp.toLowerCase().equals("b-")){
                g = 2.67f;
            }else if(temp.toLowerCase().equals("c+")){
                g = 2.33f;
            }else if(temp.toLowerCase().equals("c")){
                g = 2.0f;
            }else if(temp.toLowerCase().equals("d")){
                g = 1.0f;
            }else {
                g = 0.0f;
            }
            System.out.print("WEIGHT : ");
            int w = scnr.nextInt();
            course[i] = new Course(n, w, g);
            System.out.println("");
        }
        System.out.println("Great, now lets compute your score...");
        double totCredits = 0;
        double totGrades = 0;
        for(int i = 0; i < NUM_COURSES-1; i++){
            totGrades += (course[i].getGrade()*course[i].getWeight());
            totCredits += course[i].getWeight();
        }
        double GPA = totGrades/totCredits;
        System.out.println("GPA : " + GPA);
        for(int i = 0; i < NUM_COURSES-1; i++){
            course[i].getInfo();
        }
        
    }
}
