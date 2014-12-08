package zutilities;

/* this scripts is to remove the prefix for videos downloaded from "http://www.downvids.net"
 * prefix: WWW.DOWNVIDS.NET-
 * 
 */

import java.io.*;
import java.io.IOException;

public class downvidsRename {

    public static void main(String[] args) throws IOException {

	String dir = "D:\\1_eCourse\\cs183b\\";
	String namePrefix = "WWW.DOWNVIDS.NET-"; // prefix to be removed

	File folder = new File(dir);
	File[] listOfFiles = folder.listFiles();

	int count = 0;

	for (int i = 0; i < listOfFiles.length; i++) {

	    if (listOfFiles[i].isFile()) {

		String oldName = listOfFiles[i].getName();
		String newName = oldName.replace(namePrefix, "");

		// System.out.println(oldName + "\n" + namePrefix + "\n" +
		// newName);
		if (oldName == newName) {
		    continue;
		} else {
		    File f = new File(dir + oldName);
		    File f2 = new File(dir + newName);

		    if (f.renameTo(f2)) {
			f = f2;
			count++;
			System.out.println(oldName + "\n" + ">>Renamed to: "
				+ f.getName() + "\n");
		    } else {
			System.out.println(f.getName() + ": rename failed!");
		    }

		}
	    }
	}
	if (count == 0)
	    System.out.println("No file needs to be renamed.");

	else
	    System.out.println("\nRenaming completed: " + count
		    + " files renamed.");
    }
}
