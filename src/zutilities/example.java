package zutilities;

import java.io.*;

public class example {
    public static void main(String args[]) {
	String Str = new String("Welcome to Tutorialspoint.com");

	System.out.print("Return Value :");
	System.out.println(Str.replaceFirst("(.*)Tutorials(.*)", "AMROOD"));

	System.out.print("Return Value :");
	System.out.println(Str.replaceFirst("Tutorials", "AMROOD"));
    }
}
