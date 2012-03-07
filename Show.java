//Matthew Usnick
//Fall 2011
//Show.java

import java.io.*;

//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Show@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
/** A program that displays all the files in the hidden folder ".TrashCan". If 
*   the user specifies specific files to display, only those files are displayed
*   if they are currently in the .TrashCan folder. 
* 
* @author Matthew Usnick
*/
class Show
{
    //class level array of Strings (to store all the file names in .TrashCan)
    static String[] filesInTrash;
	
    //***********************confirmTrashCanExistis()***************************
    /** Task: Checks to see if .TrashCan exists. If it does not, the program 
    *         terminates. Otherwise, the file names in the .TrashCan are loaded 
    *         into an array.
    */	
    public static void confirmTrashCanExists()
    {
        //load the .TrashCan directory
        File trashDirectory = new File(System.getProperty("user.home") 
                                    + "/.TrashCan");

        //if the .TrashCan directory does not exist
        if(!trashDirectory.exists())
        {
            //display an error message to the user
            System.out.println("The .TrashCan directory cannot be found. "
                             + "Cannot show files.");

            //exit the program
            System.exit(1);
        }//if
	       
        //otherwise
        else
        {
            //get all of the files in the trashDirectory and store them
            //in an array
            filesInTrash = trashDirectory.list();
        }//else
	       
    }//confirmTrashCanExists()
	
    //****************************showAllFiles()********************************
    /** Task: Displays all of the files in the .TrashCan directory. The file 
    *         names are converted back to their original paths.
    */	
    public static void showAllFiles()
    {
        //for the number of files in the TrashCan
        for( int i=0; i<filesInTrash.length; i++)
        {
            //get the file name and replace the @ delimiter with /
            String renamedFile = filesInTrash[i].replace('@', '/');
            
            //remove .zip from the end of the file name
            renamedFile = renamedFile.replaceAll(".zip", "");

            //display the renamed file
            System.out.println(renamedFile);
        }//for
        
    }//showAllFiles()
	
    //*************************showSpecifiedFiles()*****************************
	/** Task: Displays only the files in the .TrashCan directory that the user 
	*         specified in the command line. The file names are converted back 
	*         to their original paths.
    *
    * @param filesToShow is an array of strings, which are the names of the 
    *        files that the user wants displayed.
    */
    public static void showSpecifiedFiles(String [] filesToShow)
    {
        //counter for the while loop
        int counter = 0;
 	   
        //for each file that the user specified
        while(counter < filesToShow.length)
        {
            //go through the files in .TrashCan
            for( int i=0; i<filesInTrash.length; i++)
            {
                //get a file from .TrashCan and replace @ delimiter with /
                String renamedFile = filesInTrash[i].replace('@', '/');
                
                //remove .zip from the file name
                renamedFile = renamedFile.replaceAll(".zip", "");

                //if the file in the trash contains the filename the user
                //entered on the command line
                if(renamedFile.contains(filesToShow[counter]))
                {
                    //display the renamed file from the .TrashCan
                    System.out.println(renamedFile);
                }//if
            }//for   
            
            //increase the counter
            counter++;
        }//while 
 	   
    }//showSpecifiedFiles()
	
    //////////////////////////////////main()////////////////////////////////////
    /** Task: Displays the files that are currently in the .TrashCan folder. If
    *         a user specifies a file(s) to show in the command line, only the 
    *         specified file(s) will be shown, if they are in the .TrashCan 
    *         folder
    *       
    * @param args[] an array of strings, which is the command line arguments
    *        that the user has specified
    */	
    public static void main(String args[])
    {
        //variable to keep track if the program is showing all of the files
        boolean showAll = true;

        //if args has items in the array,
        if(args.length > 0)
        {
            //set showAll to false
            showAll = false;
        }

        //confirm that .TrashCan exists
        confirmTrashCanExists();

        //if showAll is true
        if(showAll)
        {
            //show all files
            showAllFiles();
        }

        //otherwise 
        else
        {
            //show only the specified files
            showSpecifiedFiles(args);          
        }//else
       
    }//main
}//class


