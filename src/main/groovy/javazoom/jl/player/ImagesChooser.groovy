import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter


// ------------------------------------------
public class ImagesChooser
{
    // start to choose files from pwd
    def initialPath
    JFileChooser fc

    public ImagesChooser()
    {
        // start to choose files from pwd
        initialPath = System.getProperty("user.dir");
        fc = new JFileChooser(initialPath);

        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        //fc.setFileFilter(new MP3filter());
    } // end of constructor

    public String getImages()
    {    
        String name = null;
        int result = fc.showOpenDialog( null );
        switch ( result )
        {
            case JFileChooser.APPROVE_OPTION:
            File file = fc.getSelectedFile();

            def path =  fc.getCurrentDirectory().getAbsolutePath();
            println "path="+path+"\nfile name="+file.toString();
            if (file.exists() && (file.isDirectory() )  )
            {                
                name = file.toString();
            }
            break;

           case JFileChooser.CANCEL_OPTION:
           case JFileChooser.ERROR_OPTION:
           break;
        } // end of switch
        
        return name;
    } // end of getImages

    public void parseImages(String dir)
    {
        new File(dir).eachFile{fi ->
            if (fi.isDirectory()) parseImages(fi.toString());
            boolean flag = accept(fi);
            if (flag) println "image="+fi.toString()
        } // end of each
        
    } // end of getImages


    // 
    public boolean accept(File f) {
        return f.isFile() && f.getName().toLowerCase().endsWith(".jpg");
    }


    public static void main(String[] args)
    {
        println "--- starting ---"
        def sc = new ImagesChooser();
        def Images = sc.getImages()
        println "you chose "+Images.toString()
        sc.parseImages(Images.toString());
        
        println "--- ending ---"
    } // end of main

} // end of class
    