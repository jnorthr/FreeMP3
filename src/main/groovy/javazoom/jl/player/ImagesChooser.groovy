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
    def path
    def of = new File("/Volumes/Data/dev/FreeMP3/build/dependency-cache/images.txt")
    
    public ImagesChooser()
    {
        // start to choose files from pwd
        initialPath = System.getProperty("user.dir");
        fc = new JFileChooser(initialPath);
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        of.write('// List of Image Files\n');
    } // end of constructor

    public getSaveDir()
    {
        //fc.setSelectedFile(new File("images.txt"));    
        int result = fc.showSaveDialog( null );
        switch ( result )
        {
            case JFileChooser.APPROVE_OPTION:
            File file = fc.getSelectedFile();

            path =  fc.getCurrentDirectory().getAbsolutePath();
            println "path="+path+"\nfile name="+file.toString();
            if (file.exists() && (file.isDirectory() )  )
            {                
                initialPath = file.toString();
                initialPath += "/images.txt";
                println "writing output to path:"+initialPath
            }
            break;

           case JFileChooser.CANCEL_OPTION:
           case JFileChooser.ERROR_OPTION:
           break;
        } // end of switch    
    
        return initialPath;
        
    } // end of getSaveDir
    
    
    public String getImages()
    {    
        String name = null;
        int result = fc.showOpenDialog( null );
        switch ( result )
        {
            case JFileChooser.APPROVE_OPTION:
            File file = fc.getSelectedFile();

            initialPath =  fc.getCurrentDirectory().getAbsolutePath();
            println "path="+initialPath+"\nfile name="+file.toString();
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
            if (flag) of.append(fi.toString()+'\n');
        } // end of each
        
    } // end of getImages

    def copy = { File src,File dest->
        def input = src.newInputStream
        def output = dest.newOutputStream()
 
        output << input
 
        input.close()
        output.close()
    }

    // 
    public boolean accept(File f) {
        boolean flag = f.isFile() 
        def fn = f.getName().toLowerCase()
        if (flag)
        {
            flag = (fn.endsWith(".gif") || fn.endsWith(".png") || fn.endsWith(".jpg") || fn.endsWith(".jpeg")  ) ? true : false;
        }
        return flag;
    }


    public static void main(String[] args)
    {
        println "--- starting ---"
        def sc = new ImagesChooser();
        
        def imagepath = sc.getImages()
        println "you picked "+imagepath.toString()
        sc.parseImages(imagepath.toString());
        
        println "--- ending ---"
    } // end of main

} // end of class
    