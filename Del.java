//Matthew Usnick
//Fall 2011
//Del.java

import java.io.*;
import java.util.Scanner;

//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Del@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
/** A program that deletes a file. The deleted file is compressed and moved into 
*  an invisible folder named .TrashCan. The file name is changed to include the
*  path of the file. This allows files with the same name to exist in .TrashCan
*  at the same time.
* 
* @author Matthew Usnick
*/
public class Del 
{
    //variable to hold the .TrashCan file path
    static String trashDirectory;
	
    //******************************confirmDel()*******************************
    /** Task: Asks the user if they wish to delete the specified files. If no,
    *         then the program is terminated. Input validation prevents an 
    *         incorrect entry. 
    */
    public static void confirmDel()
    {
        //create a scanner
        Scanner scan = new Scanner(System.in);
    	
        //variable to store the users response
        String response;
    	
        //condition of the do while loop
        boolean unanswered = true;
    	
        //prompt
        System.out.print("Are you sure you want delete these files? (y/n): ");
        
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
                System.out.println("You have decided to abort the Del " 
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
	
    //***************************confirmFileExists()****************************
    /** Task: checks to see if the specified file exists. If a file does not 
    *         exist print a warning that the specified file does not exist.
    *       
    * @param fileToConfirm is a string of the filename whose existence is
    *        being confirmed
    *        
    * @return true if the file exists, false if the file does not exist 
    */	
    public static boolean confirmFileExists(String fileToConfirm)
    {
        //variable to track if there are any files that cannot be found
        boolean filesExist = true;

        //load the file
        File f = new File(fileToConfirm);
    	    
        //if the file doesn't exist
        if(!f.exists())
        {
            //set filesExist to false
            filesExist = false;
    		    
            //display a message that says the file does not exist 
            System.out.println("The file \"" + fileToConfirm 
                             + "\" does not exist or cannot be found.");
        }
    	
        //returns true if all files exist, and false if ANY file does not exist
        return filesExist;

    }//end confirmFilesExist()
    
    //************************confirmFileIsWritable()*************************
    /** Task: checks to see if the specified file is writable. If a file is not 
    *         writable, print a warning that the specified file cannot be 
    *         written to.
    *       
    * @param fileToConfirm is the a string of the file name whose permissions 
    *        are being confirmed.
    *        
    * @return true if the file is writable, false if not
    */	
    public static boolean confirmFileIsWritable(String fileToConfirm)
    {
        //variable to track if there are any files that cannot be written to
        boolean fileIsWritable = true;
		
        //load the file
        File f = new File(fileToConfirm);
    	    
        //if the file cannot be written to
        if(!f.canWrite())
        {
            //set filesAreWritable to false
            fileIsWritable = false;
    		    
            //display a message that says the file cannot be deleted to
            System.out.println("The file \"" + f.getName() 
                             + "\" cannot be deleted.");
        }

        //returns true if the file is writable, and false if not
        return fileIsWritable;
		
	}//end confirmFileIsWriteable()		
	
    //***************************configureTrashCan()****************************
    /** Task: Checks to see if the .TrashCan directory exists. If it does not, 
    *         then it is created.
    */	
    public static void configureTrashCan()
    {
        //get the home directory name	
        //append /.TrashCan to the home directory name 
        trashDirectory = (System.getProperty("user.home") + "/.TrashCan");
    	
        //create a File instance of the .TrashCan directory
        File trashDir = new File(trashDirectory);
    	
        //if .TrashCan directory doesn't exist
        if(!trashDir.exists())
        {
            //create the .TrashCan directory
            trashDir.mkdir();
        }
    }//end configureTrashCan()
	
    //******************************deleteFile()********************************
    /** Task: Renames a file to it's full path name, compresses the file moves 
    *         the compressed file to .TrashCan and finally, deletes the original
    *         file.
    *       
    * @throws FileNotFoundException 
    */	
    public static void deleteFile(String fileToDelete) 
                                                    throws FileNotFoundException
    {
    	//if the file exists and is writable
        if(confirmFileExists(fileToDelete) 
        && confirmFileIsWritable(fileToDelete))
        {
            //load the file
            File currentFile = new File(fileToDelete);
	    	
            //get the file path
            String path = currentFile.getAbsolutePath();
	    	
            //convert the path name /'s to @'s
            path = path.replace('/', '@' );
	    	
            //rename the file to the converted path name
            currentFile.renameTo(new File(path));
	    		    	
            //zip the file
            Zip.zip(path);
	    	
            //load the zipped file
            File zippedFile = new File(path + ".zip");
	    	
            //move the zipped file to the .TrashCan
            zippedFile.renameTo(new File(trashDirectory, zippedFile.getName()));

            //load the renamed file
            File renamedFile = new File(path);
	    	
            //delete the renamed file
            renamedFile.delete();
		}//end if
        
	}//end deleteFile()
	
	
    //*****************************displayHelp()********************************
    /** Task: Displays help information for the user.
    */	
    public static void displayHelp()
    {
        System.out.println("The following is the help information for the "
                         + " program 'Del'");
        System.out.println("----------------------------------------------"
                         + "---------------");
        System.out.println("'Del' is a program that deletes a file, and " 
                         + "moves a compressed copy of that file to an " 
                         + "invisable folder named .TrashCan");
        System.out.println("If the user specifies '-i' as an option on the "
                         + "command line, the user will be prompted to confirm" 
                         + " the deletion of the file.");
        System.out.println("You may enter as many files to delete as you "
                         + "like.");
        System.out.println("Other programs that work in conjunction with " 
                         + "\"Del\" are \"Show\" \"Undel\" and \"Purge\".");
        
        System.out.println();
		
    }//end displayHelp()
		
    //////////////////////////////////main()////////////////////////////////////
    /** Task: A program that deletes a file. The deleted file is compressed and 
    *         moved into an invisible folder named .TrashCan. The file name is 
    *         changed to include the path of the file. This allows files with 
    *         the same name to exist in .TrashCan at the same time.
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
        
        //check for -i
        if ( args[0].equals("-i"))
        {
        	//ask the user if they are sure they wish to delete the files
            confirmDel();
            
            //set counter to 1, so that the -i option will be excluded from the
            //list of files to delete
            counter = 1;
        }

        //check to see if .TrashCan exists in home directory
        //if not, make it
        configureTrashCan();
       
        //for each file
        while (counter < args.length)
        {
        	//delete the file
            deleteFile(args[counter]);
            
            //increase the counter
            counter++;
        }//end while
           	
    }//end main()
    
}//end Del class
