// groovy sample to choose one file using java's  JFileChooser
// would only allow choice of a single directory by setting another JFileChooser feature
// http://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html
// see more examples in above link to include a file filter
package javazoom.jl.player;

import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter


// ------------------------------------------
public class SongChooser
{
    // start to choose files from pwd
    def initialPath
    JFileChooser fc

    public SongChooser()
    {
        // start to choose files from pwd
        initialPath = System.getProperty("user.dir");
        fc = new JFileChooser(initialPath);

        // fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fc.setFileFilter(new MP3filter());
    } // end of constructor

    public String getSong()
    {    
        String name = null;
        int result = fc.showOpenDialog( null );
        switch ( result )
        {
            case JFileChooser.APPROVE_OPTION:
            File file = fc.getSelectedFile();

            def path =  fc.getCurrentDirectory().getAbsolutePath();
            println "path="+path+"\nfile name="+file.toString();
            if (file.exists() && (!file.isDirectory() ) && (file.toString().endsWith(".mp3")) )
            {                
                name = file.toString();
            }
            break;

           case JFileChooser.CANCEL_OPTION:
           case JFileChooser.ERROR_OPTION:
           break;
        } // end of switch
        
        return name;
    } // end of getSong


    public static void main(String[] args)
    {
        println "--- starting ---"
        def sc = new SongChooser();
        def song = sc.getSong()
        println "you chose "+song.toString()
        println "--- ending ---"
    } // end of main

} // end of class

class MP3filter extends javax.swing.filechooser.FileFilter {
    public boolean accept(File f) {
        return f.isDirectory() || f.getName().toLowerCase().endsWith(".mp3");
    }
    
    public String getDescription() {
        return "MP3 files";
    }
} // end of class
