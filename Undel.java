//Matthew Usnick
//Fall 2011
//Undel.java

import java.io.File;
import java.io.FileNotFoundException;

//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Undel@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
/** A program that restores the specified files in the hidden folder ".TrashCan"
*  The files are first unzipped, and then moved to their original location. The
*  zipped version of the file remains in the .TrashCan.
*  
* @author Matthew Usnick
*/
public class Undel 
{
    //class level array of Strings (to store all the file names in .TrashCan)
    static String[] filesInTrash;
	
    //***********************confirmTrashCanExistis()***************************
    /** Task: Checks to see if .TrashCan exists. If it does, the file names in 
    *         the .TrashCan are loaded into an array.
    *         
    * @return true if the .TrashCan exists, false if not.
    */	
    public static boolean confirmTrashCanExists()
    {
        //variable to track if the .TrashCan exists
        boolean trashCanExists = true;
    	
        //load the .TrashCan directory
        File trashDirectory = new File(System.getProperty("user.home") 
                                    + "/.TrashCan");

        //if the .TrashCan directory does not exist
        if(!trashDirectory.exists())
        {
            //set variable to false
            trashCanExists = false;
        }  
        //otherwise the .TrashCan exists 
        else
        {
            //get all of the files in the trashDirectory and store them
            //in an array
            filesInTrash = trashDirectory.list();
        }//else
        
        //return true if .TrashCan exists, false if not
        return trashCanExists;
	       
    }//confirmTrashCanExists()
    
    
    //*****************************displayHelp()********************************
    /** Task: Displays help information for the user.
    */	
    public static void displayHelp()
    {
        System.out.println("The following is the help information for the "
                         + " program 'Undel'");
        System.out.println("----------------------------------------------"
                         + "---------------");
        System.out.println("'Undel' is a program that restores the specified"
                         + " deleted files from the .TrashCan folder, to their" 
                         + " original destinations.");
        System.out.println("You may enter as many files to delete as you "
                         + "like.");
        System.out.println("Other programs that work in conjunction with " 
                         + "\"Undel\" are \"Show\" \"Del\" and \"Purge\".");
        
        System.out.println();
		
    }//end displayHelp()    
	
    //*****************************undeleteFile()*******************************
    /** Task: Checks to see if a file is in the .TrashCan. If it is, it is 
    *         uncompressed and copied back to it's original location. If the 
    *         file cannot be found, an error message is displayed.
    *
    * @param fileToUndelete is a string which is the name of the file that is to
    *        be undeleted.      
    *       
    * @throws FileNotFoundException 
    */	
    public static void undeleteFile(String fileToUndelete) 
                                                    throws FileNotFoundException
    {
        //get the name of the current file to delete and replace the / with
        //the @ delimiter
        String renamed = fileToUndelete.replace("/", "@");
        	
        //variable to track if the file was ever found in the .TrashCan
        boolean fileFound = false;   
      	
        //go through the files in .TrashCan
        for( int i=0; i<filesInTrash.length; i++)
        {
            //get a file name from .TrashCan
            String currentFile = filesInTrash[i];
                
            //if the file in the trash contains the filename the user
            //entered on the command line
            if(currentFile.contains(renamed))
            {
                //unzip the file in .TrashCan
                Zip.unzip(System.getProperty("user.home") 
                       + "/.TrashCan/" + currentFile);
            	
                //remove the .zip extension from String
                currentFile = currentFile.replace(".zip", "");
                
                //load the unzipped file
                File unzippedFile = new File(System.getProperty("user.home") 
                                          + "/.TrashCan/" + currentFile);
            	
                //move the unzipped file to its original location
                unzippedFile.renameTo(new File(currentFile.replace("@", "/")));

                //set fileFound to true
                fileFound = true;
            }//if
        }//for   
            
        //if the file was never found
        if(!fileFound)
        {
            //print an error message 
            System.out.println("\"" + fileToUndelete + "\"" 
                             + " could not be found in the .TrashCan!");
        }
  
    }//end undeleteFile()
	
    //////////////////////////////////main()////////////////////////////////////
    /** Task: Restores the specified deleted files from the .TrashCan folder to
    *         their original destinations.
    *       
    * @param args[] an array of strings, which is the command line arguments
    *        that the user has specified
    *        
    * @throws FileNotFoundException 
    */	
    public static void main(String args[]) throws FileNotFoundException
    {
        //if the user has not entered any commands or files to display
        if(args.length == 0)
        {
            //display the help information
            displayHelp();
			
            //and exit the program
            System.exit(1);
        }
        
        //counter for the while loop
        int counter = 0;
        
        //if the .TrashCan exists
        if(confirmTrashCanExists())
        {
            //for each file
            while (counter < args.length)
            {
                //delete the file
                undeleteFile(args[counter]);
                
                //increase the counter
                counter++;
            }//end while
        }
        //otherwise
        else
        {
            //display an error message to the user
            System.out.println("The .TrashCan directory cannot be found. "
                             + "Cannot undelete files.");
        }
        
    }//end main()
}//end Undel.class
