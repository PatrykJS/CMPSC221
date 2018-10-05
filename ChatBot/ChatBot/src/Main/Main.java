package Main;

import java.util.Scanner;

/**
 * A simple ChatBot that can be expanded upon further coding.
 * @author Patryk Smith
 * @version 1.0
 * 
 */
enum Accent {ENGLISH, AMERICAN_ENGLISH, AUSTRALIAN, AFRICAN, LATINO, SPANISH, FRENCH};
enum OS {WINDOWS, MACINTOSH, LINUX, UNIX, OTHER};

public class Main {

    public static void main(String[] args) {
        ChatBot jarvis = new ChatBot("Jarvis", Accent.ENGLISH, 10);
        Scanner scnr = new Scanner(System.in);
        jarvis.sayGreeting(scnr);
        
    }
}

