package Main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.Scanner;

/**
 * A ChatBot can tell you the time, a joke, or the weather!
 * @author Patryk Smith
 * 
 */
public class ChatBot {    
    //-- Properties --//
    private static final String API_URL = "https://www.tensorflow.org/api_docs/java/reference/org/tensorflow/package-summary";
    private String name;
    private OS os;
    private int volume;
    private Accent accent;
    private ArrayList<String> shoppingCart;
    
    //-- Constructors --//
    public ChatBot(){
      accent = accent.ENGLISH;
      volume = 5;
      shoppingCart = new ArrayList<String>();
    }
    public ChatBot(String _name, Accent _accent, int _volume){
      name = _name;
      accent = _accent;
      volume = _volume;
      shoppingCart = new ArrayList<String>();
    }
    //--  Methods --//
    //-- Modifiers--//
    public void setName (String _name){
        name = _name;
    }
    public void setVolume (int _volume){
        volume = _volume;
    }
    public void setOS (OS _os){
        os = _os;
    }
    public void setAccent(Accent _accent){
        accent = _accent;
    }
    //-- Accessors --//
    public String getName(){
        return name;
    }
    public int getVolume(){
        return volume;
    }
    public OS getOS(){
        return os;
    }
    public Accent getAccent(){
        return accent;
    }
    public ArrayList<String> getShoppingList(){
        return shoppingCart;
    }
    public String getAPI_URL(){
        return API_URL;
    }
    //-- Responders --//
    public void getTime(Scanner s){
        System.out.println("Do you want to know the time? (y/n)");
        String temp = s.next();
        if((temp.toLowerCase().contains("y"))){
            Calendar C = Calendar.getInstance();
            System.out.println(C.getTime());
        }else if((temp.toLowerCase().contains("n"))){
            System.out.println("Times up.");
        }
    }
    public void tellJoke(Scanner s){
        System.out.println("Do you want to hear a joke? (y/n)");
        String temp = s.next();
        if((temp.toLowerCase().contains("y"))){
            System.out.println("Why did the chicken cross the road?");
            temp = s.next();
            System.out.println("To get a job, becasue its hard to be a chicken in this economy!");
        }else if((temp.toLowerCase().contains("n"))){
            System.out.println("Oh well.");
        }
    }
    public void tellWeather(Scanner s){
        Random r = new Random();
        System.out.println("Do you want to hear the weather report for today? (y/n)");
        String temp = s.next();
        int t1 = r.nextInt(20) + 60;
        int t2 = r.nextInt(20) + 40;
        if((temp.toLowerCase().contains("y"))){
            System.out.println("Partly Cloudy with possible showers. High of " + t1 + "°F and Low of "+t2+"°F. Enjoy!");
        }else if((temp.toLowerCase().contains("n"))){
            System.out.println("Ok then.");
        }
    
    }
    public void shutDown(Scanner s){
        System.out.println("Do you want REALLY want me to DIE??? (y/n)");
        String temp = s.next();
        if(temp.toLowerCase().contains("y")){
            System.out.println("Goodbye cruel world!");
            System.out.println("[ ENTITY : \"Jarvis\" DELETED ]");
            System.exit(0);
        }else if(temp.toLowerCase().contains("n")){
            System.out.println("Good cause I'm afraid of death...");
        }
        
    }
    public void sayGreeting(Scanner s){
        System.out.println("Hello, whats your name?");
        String _name = s.nextLine();
        System.out.println("Hello, "+ _name);
        Random r = new Random();
        ask(r.nextInt(4), s);
        
    }
    public void shopping(Scanner s){
        System.out.print("Would you like to go shopping? (y/n):");
        String temp = s.next();
        if(temp.toLowerCase().contains("y")){
            System.out.print("Ok, what do you want to add to your shopping list? :");
            temp = s.next();
            if(!temp.isEmpty()){
                shoppingCart.add(temp);
                do {
                    System.out.print("Anything else? (y/n):");
                    temp = s.next();
                    if(temp.toLowerCase().contains("y")){
                        System.out.print("Whats the item? :");
                        temp = s.next();
                        if(!temp.isEmpty()){
                            shoppingCart.add(temp);
                        }else {
                          System.out.println("If you don't type anything, I can't help you...");
                           temp = "n";
                        }
                    } 
                }while(!temp.toLowerCase().contains("n"));
            }else {
                System.out.println("If you don't type anything, I can't help you...");
            }
        }else if(temp.toLowerCase().contains("n")){
            System.out.print("Well would you like to see your list? (y/n):");
            temp = s.next();
            if(temp.toLowerCase().contains("y")){
                System.out.println("Okedoke: ");
                for(String str:shoppingCart){
                    System.out.println(str);
                }
            }else {
                System.out.println("Happy day!");
            }
        }
    }
    public void diplayFunctions(Scanner s){
        System.out.println("Here are my availible functions at the time:");
        System.out.println("|Option |          |      Name    |");
        System.out.println("|---------------------------------|");
        System.out.println("|   1   |----------|    Get Time  |");
        System.out.println("|   2   |----------|   Tell Joke  |");
        System.out.println("|   3   |----------| Tell Weather |");
        System.out.println("|   4   |----------| Go Shopping  |");
        System.out.println("|   5   |----------|   Turn Off   |");
        System.out.println("|---------------------------------|");
        System.out.print("Choice: ");
        int t = s.nextInt();
        switch (t){
            case 1:
                getTime(s);
                break;
            case 2:
                tellJoke(s);
                break;
            case 3:
                tellWeather(s);
                break;
            case 4:
                shopping(s);
                break;
            case 5:
                shutDown(s);
                break;    
            default:
                System.out.println("<-------- ERROR! --------->");
                diplayFunctions(s);
                break;
        }
        diplayFunctions(s);
    }
     private void ask(int i, Scanner s){
        String t;
        switch(i){
            case 0:
                System.out.println("How are you doing today?");
                String temp = s.next();
                if((temp.toLowerCase().contains("good")) ||((temp.toLowerCase().contains("great")))){
                    System.out.println("That's good.");
                }else if((temp.toLowerCase().contains("bad")) ||((temp.toLowerCase().contains("terrible")))){
                    System.out.println("I'm sorry you are having a bad day.");
                }
                diplayFunctions(s);
                break;
            case 1:
                tellWeather(s);
                diplayFunctions(s);
               break;
            case 2:
                tellJoke(s);
                diplayFunctions(s);
              break;
            case 3:
               getTime(s);
               diplayFunctions(s);
               break;
            case 4:
               shopping(s);
               diplayFunctions(s);
               break;
            default:
                diplayFunctions(s);
                break;
        }
        diplayFunctions(s);
    
    }
}
