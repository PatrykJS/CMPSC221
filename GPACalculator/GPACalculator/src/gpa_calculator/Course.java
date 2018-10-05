package gpa_calculator;

/**
 *
 * @author Patryk Smith
 */
public class Course {
    private String name;
    private int weight;
    private float grade;
    
    Course(String n, int w, float g){
        setName(n);
        setWeight(w);
        setGrade(g);
    }
    
    public void setName (String n){
        name = n;
    }
    public void setWeight(int w){
        weight = w;
    }
    public void setGrade(float g){
        grade = g;
    }
    
    public void getInfo(){
        System.out.println(name+ "\t" + grade + "\t" + weight);
    }
    public int getWeight(){
        return weight;
    }
    public float getGrade(){
        return grade;
    }
    public String getName(){
        return name;
    }
}
