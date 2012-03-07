//Matthew Usnick
//Fall 2011
//Purge.java

import java.io.*;
import java.util.Scanner;

//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Purge@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
/** A program that deletes all of the files in the hidden folder ".TrashCan". If 
*   the user specifies specific files to delete, only those files are deleted. 
*   The user is always asked if they are sure they want to delete the files.
* 
* @author Matthew Usnick
*/
class Purge
{
	
    //class level array of Strings (to store all the file names in .TrashCan)
    static String[] filesInTrash;
    //variable to track if the user has specified which files they would like to
    //delete
    static boolean specificFiles = false;
	
    //******************************confirmPurge()******************************
    /** Task: Asks the user if they wish to delete the specified files. If no,
    *         then the program is terminated. Input validation prevents an 
    *         incorrect entry. 
    */
    public static void confirmPurge()
    {
        //create a scanner
        Scanner scan = new Scanner(System.in);
    	
        //variable to store the users response
        String response;
    	
        //condition of the do while loop
        boolean unanswered = true;
    	
        //prompt
        System.out.print("Are you sure you want to delete the files in "
                       + "the TrashCan? (y/n): ");
        
        do
        {
            //store users input in variable. 
            response = scan.nextLine();
            //extract first character from the response
            response = ("" + response.charAt(0));

            //if the character is n (answer is 'no')
            if(response.equals("n") || response.equals("N"))
            {
                //inform the user that the operation is being aborted
                System.out.println("You have decided to abort the Purge " 
                                 + "operation");
                //end the program
                System.exit(0);
            }
            
            //if the character is NOT y (any character besides y or n)
            else if(!response.equals("y") && !response.equals("Y"))
            {
                //ask user to enter a new response, and repeat loop
                System.out.println("Please enter y or n: ");
            }
            
            //otherwise the character is y or Y (answer is 'yes')
            else
            {
                //end loop, and continue the program
                unanswered = false;
            }
        }while(unanswered);
	    
    }//end confirmWipe()
		
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
       
    //******************************deleteFile()********************************
    /** Task: Converts the filename to match the file name in .TrashCan and then
    *         deletes that file.
    *         
    * @param fileToDelete is a string which is the name of the file to be
    *        deleted.      
    * @throws FileNotFoundException 
    */	
    public static void deleteFile(String fileToDelete) 
                                                    throws FileNotFoundException
    {
        //convert / to the @ delimiter
        fileToDelete = fileToDelete.replace("/", "@");
    	
        //load the file
        File currentFile = new File(System.getProperty("user.home") 
                                  + "/.TrashCan/" + fileToDelete);
        
        //delete the file
        currentFile.delete();    
        
    }//end deleteFile()
        
    //****************************purgeAllFiles()*******************************
    /** Task: Deletes all of the files in the .TrashCan
    * 
    * @throws FileNotFoundException 
    */
    public static void purgeAllFiles() throws FileNotFoundException
    {
        //for each file in .TrashCan
        for( int i=0; i<filesInTrash.length; i++)
        {
            //delete the file
            deleteFile(filesInTrash[i]);
        }//end for 
    }//end purgeAllFiles()
       
    //*************************purgeSpecifiedFiles()****************************
    /** Task: Deletes only the files passed into this method. If the files are 
    *         not in the .TrashCan an error message is displayed.
    *
    * @param filesToDelete is an array of strings, which are the names of the 
    *        files that the user wants to delete.
    * @throws FileNotFoundException 
    */
    public static void purgeSpecifiedFiles(String [] filesToDelete) 
                                                    throws FileNotFoundException
    {
        //counter for the while loop
        int counter = 0;
        
        //for each file that the user specified
        while(counter < filesToDelete.length)
        {
            //get the name of the current file to delete and replace the / with
            //the @ delimiter
            String renamed = filesToDelete[counter].replace("/", "@");
        	
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
                   //delete the file from the .TrashCan
                   deleteFile(filesInTrash[i]);
                   
                   //set fileFound to true
                   fileFound = true;
                }//if
            }//for   
            
            //if the file was never found
            if(!fileFound)
            {
                //print an error message 
                System.out.println("\"" + filesToDelete[counter] + "\"" 
                                 + " could not be found in the .TrashCan!");
            }
        	
            //increase the counter
            counter++;
        }//while 
        
    }//showSpecifiedFiles()
    
    //////////////////////////////////main()////////////////////////////////////
    /** Task: Deletes all the files in the .TrashCan folder, if it exists. The 
    *         user is prompted to confirm the purge operation. If the user has 
    *         entered specific files to purge, only those files will be deleted. 
    *         If the specified file is not in the .TrashCan folder, an error
    *         message will be displayed.
    *       
    * @param args[] an array of strings, which is the command line arguments
    *        that the user has specified(the files the user wishes to delete)
    * @throws FileNotFoundException 
    */	
    public static void main (String[] args) throws FileNotFoundException 
    {
        //if there are arguments from the command line
        if(args.length > 0)
        {
            //set specificFiles variable to true
            specificFiles = true;
        }
        
        //confirm that the user wants to purge files
        //if no, the program terminates immediately
        confirmPurge();

        //if the .TrashCan exists
        if(confirmTrashCanExists())
        {     
            //if the user specified files to purge
            if(specificFiles)
            {
                //purge the specified files
                purgeSpecifiedFiles(args);  		
            }
            //otherwise 
            else
            {
                //purge all files
                purgeAllFiles();
            }//end else
        }
        //otherwise
        else
        {
            //display an error message to the user
            System.out.println("The .TrashCan directory cannot be found. "
                             + "Cannot purge files.");
        }//end else
    }//end main()
}//end Purge.class


