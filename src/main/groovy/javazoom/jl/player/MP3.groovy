
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
import javazoom.jl.player.SongChooser;

public class MP3 {
    private String filename;
    private Player player; 
    int playtime = 10; // default to 10 sec.s of play
    boolean gui = false;
    def songs = []

    // constructor that takes the name of an MP3 file
    public setName(String filename) {
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


    // figure out the command line options or if the parm is a valid .mp3 song that exists, put in into the songs list
    public void decodeArgs(args)
    {
	args.each{a->
		def at = a.trim()
		def atlc = at.toLowerCase();
		def flag = (atlc.startsWith('-')) ? true : false;

		if (flag)
		{
			switch(atlc)
			{
				case "-gui" : gui = true;
				break;
			} // end of switch
		}
		else
		{
			if (new File(at).exists() && (atlc.endsWith(".mp3")) )
			{
				this.songs += at;
			} // end of if
			else
			{
				if (at.isInteger())
				{
					try{ this.playtime = at as int; } catch(Exception x){ this.playtime = 15; }
					if ( this.playtime < 1 ) { this.playtime = 15; }
				} // end of if
				else
				{
					println "this is an unknown song, is not an mp3 file and is not a valid command option:"+a;
				} // end of else

			} // end of else

		} // end of if

	} // end of each

	// user asked to choose a song
	if (gui)
	{
		pickSong();
	}// end of if

    } // end of decode


    // =========================
    // song chooser
    def pickSong()
    {
		def sc = new SongChooser();
		// will return null or valid .mp3 filename that exists and is not a directory
        	def song = sc.getSong()
		if (song!=null)
		{
			this.songs += song.toString();
                 	println "you chose "+song.toString()
		} // end of if
    } // end of pickSong



    // =========================
    // song player from songs[] list
    def playSongs()
    {
	this.songs.each{song->
	        String filename = song;
        	this.setName(filename);

		def th = Thread.start 
		{
			println 'starting to play '+filename+" for "+playtime+" sec.s"
	        	this.play();
    			sleep this.playtime * 1000;
		        // when  done, stop playing it
        		this.close();
    			//throw new NullPointerException()
		} // end of thread start

		th.setDefaultUncaughtExceptionHandler({t,ex ->
    			println 'ignoring: ' + ex.class.name
		} as Thread.UncaughtExceptionHandler)

		th.join()

	} // end of each

    } // end of playSong



    // play client
    public static void main(String[] args) {
        MP3 mp3 = new MP3();
    	
	if (args.size() > 0)
	{
		mp3.decodeArgs(args);
	} // end of if
	else
	{
		mp3.pickSong();
	} // end of else

	if (mp3.songs.size() < 1)
	{	
		mp3.songs += "resources/ring.mp3";
	} // end of if

	try{
		mp3.playSongs()
	}
	catch(Exception x) {}

    } // end of main

} // end of class

