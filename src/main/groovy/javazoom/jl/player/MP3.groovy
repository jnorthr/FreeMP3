
/*************************************************************************
 *  Compilation:  javac -classpath .:jl1.0.jar MP3.java         (OS X)
 *                javac -classpath .;jl1.0.jar MP3.java         (Windows)
 *  Execution:    java -classpath .:jl1.0.jar MP3 filename.mp3  (OS X / Linux)
 *                java -classpath .;jl1.0.jar MP3 filename.mp3  (Windows)
 *  
 *  Plays an MP3 file using the JLayer MP3 library.
 *
 *  Reference:  http://www.javazoom.net/javalayer/sources.html
 *
 *
 *  To execute, get the file jl1.0.jar from the website above or from
 *
 *      http://www.cs.princeton.edu/introcs/24inout/jl1.0.jar
 *
 *  and put it in your working directory with this file MP3.java.
 *
 *************************************************************************/
package javazoom.jl.player;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javazoom.jl.player.Player;


public class MP3 {
    private String filename;
    private Player player; 
    int playtime = 10; // default to 10 sec.s of play

    // constructor that takes the name of an MP3 file
    public MP3(String filename) {
        this.filename = filename;
    }

    public void close() { if (player != null) player.close(); }

    // play the MP3 file to the sound card
    public void play() {
        def fh = new File(filename); 
        if (fh.exists() )
        {
		println "playing "+fh.canonicalFile.toString()
        }
        else
        {
        	println "cannot find $filename to play"
        	System.exit(1);
        }
        
        try {
            FileInputStream fis     = new FileInputStream(filename);
            BufferedInputStream bis = new BufferedInputStream(fis);
            player = new Player(bis);
        }
        catch (Exception e) {
            System.out.println("Problem playing file " + filename);
            System.out.println(e);
        }

        // run in new thread to play in background
        new Thread() {
            public void run() {
                try { player.play(); }
                catch (Exception e) { System.out.println(e); }
            }
        }.start();


    } // end of play

    // play client
    public static void main(String[] args) {
    
        String filename = (args.size() > 0) ? args[0] : "resources/Love.mp3";
        MP3 mp3 = new MP3(filename);

        String playtimes = (args.size() > 1) ? args[1] : "10";
	try{ mp3.playtime = playtimes as int; } catch(Exception x){ mp3.playtime = 10; }

	def th = Thread.start 
	{
		println 'starting to play '+filename
	        mp3.play();
    		sleep mp3.playtime * 1000;
    		//throw new NullPointerException()
	} // end of thread start

	th.setDefaultUncaughtExceptionHandler({t,ex ->
    		println 'ignoring: ' + ex.class.name
	} as Thread.UncaughtExceptionHandler)

	th.join()

        // when  done, stop playing it
        mp3.close();
    }

}

