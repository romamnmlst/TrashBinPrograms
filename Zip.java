//Author: Abbas Moghtanei 
//Date: 09/14/11
//Zip.java
 
//This file was provided by my instructor, for use in our programs.
//Modified by Matthew Usnick to include comments and documentation.
//This class is used in the Del and Undel programs.
 
import java.util.zip.*;
import java.io.*;

//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Zip@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
/** Task: This class contains two methods: zip() and unzip(). zip() creates a 
 *        new .zip file, from a given file. unzip() creates a new unzipped file
 *        from a given .zip file. 
 */
class Zip
{
    //********************************unzip()***********************************
	/** Task: This method unzips a given file, and creates a unzipped version
    *        of the file.
    *        
    * @param zipFile is the name of the zip file to unzip (String).         
    */
    public static void unzip(String zipFile)
    {
        try
        {
        	//create a ZipInputStream from the given file name
            ZipInputStream zis = 
                               new ZipInputStream(new FileInputStream(zipFile));
            
            //get the next entry from the ZipInputStream, and prepare it to be
            //read. 
            ZipEntry ze = zis.getNextEntry();
        
            //get the output file name (remove .zip from the file name)
            String outFilename = 
            	              zipFile.substring(0, zipFile.lastIndexOf(".zip"));

            //create a FileOutputStream from the file name
            FileOutputStream os = new FileOutputStream(outFilename);

            //create an array to store the bytes from the ZIS
            byte buf[] = new byte[1024];
         
            //variable to ensure there are bytes left to read
            int len;

            //while the ZIS still has data to read
            while((len = zis.read(buf)) > 0)
            {
            	//write the data to the FileOutputStream
                os.write(buf, 0, len);
            }
            
            //close the FileOutputStream
            os.close();
         
            //close the ZipInputStream
            zis.close();
            
        }catch(IOException e) 
        {
        	System.out.println("There was a problem unzipping the file: " 
        			         + zipFile);
        }
        
    }//end unzip()
    
    //**********************************zip()***********************************
    /** Task: This method creates a zipped version of a file.
    *        
    * @param fname is the name of the file to zip (String).         
    */
    public static void zip(String fname)
    {
        //create a byte array
        byte buf[] = new byte[1024];
        
        try
        {
            //create the file name for the zip file
            String outFilename = fname + ".zip";
            
            //create a new ZipOutputStream
            ZipOutputStream zos = 
                         new ZipOutputStream(new FileOutputStream(outFilename));
            
            //create a new FileInputStream from the given file
            FileInputStream fis = new FileInputStream(fname);
            
            //add a zip entry to the ZOS
            zos.putNextEntry(new ZipEntry(fname));

            //variable to ensure there are bytes left to read
            int len;
            
            //while there are bytes left to read
            while((len = fis.read(buf)) > 0)
            {
                //write the bytes to the ZOS
                zos.write(buf, 0, len);
            }
            
            //close the ZipOutputStream
            zos.close();
            
            //close the FileInputStream
            fis.close();
            
        }catch(IOException e)
        {
            System.out.println("There was a problem zipping the file: "+ fname);
        }
      
    }//end zip()
    
}//end Zip.java
