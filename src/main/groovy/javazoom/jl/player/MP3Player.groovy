
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

public class MP3Player {
    private String filename;
    private Player player;   
    FileInputStream fis;     
    BufferedInputStream bis;

    int playtime = 40; // default to 10 sec.s of play

    def UChenMap=[:]
    def UChenMapLigatures = [:];

    public MP3Player()
    {
        UChenMap["UChenMap.Ligatures"] = UChenMapLigatures;

        UChenMap["D"] = "\u0F4C";
        UChenMap["zh"] = "\u0F5E";
        UChenMap["k"] = "\u0F40";
        UChenMap["kh"] = "\u0F41";
        UChenMap["g"] = "\u0F42";
        UChenMap["n"] = "\u0F53";
        UChenMap["p"] = "\u0F54";
        UChenMap["ph"] = "\u0F55";
        UChenMap["y"] = "\u0F61";
        UChenMap["r"] = "\u0F62";
        UChenMap["l"] = "\u0F63";
        UChenMap["sh"] = "\u0F64";
        UChenMap["A"] = "\u0F71";
    } // end of default constructor

    // =========================
    // sound player 
    def playUChen(String uchenfragment)
    {   
        String namefragment = UChenMap[uchenfragment];
        playSound("sounds${File.separator}${namefragment}.mp3");
    } // end of method

    def play(String namefragment)
    {
        playSound("sounds${File.separator}${namefragment}.mp3");
    } // end of method

    def playSound(String song)
    {
	        String filename = song;
		    def secs = 0.7;  //songlength;

            try {
                fis = new FileInputStream(filename);
                //println "size of $filename is ${new File(filename).length()} bytes"
                BufferedInputStream bis = new BufferedInputStream(fis);
                player = new Player(bis);
                //println "gonna play $filename for $secs sec.s"
            }
            catch (Exception e) 
            {
                System.out.println("Problem playing file " + filename);
                System.out.println(e);
            } // end of catch

    		def th = Thread.start 
        	{
    			//println 'starting to play '+filename+" for "+secs+" sec.s"
	        	player.play();
    			//sleep secs * 1000;  // playtime is in seconds so multi by 1000 to get milles used in sleep;
		        // when  done, stop playing it
        		player.close();
                //println "playtime is over"
    			//throw new NullPointerException()
		    } // end of thread start

    		th.setDefaultUncaughtExceptionHandler({t,ex ->
    			println 'ignoring: ' + ex.class.name
            } as Thread.UncaughtExceptionHandler)

    		th.join()
    } // end of playSong

    // play client
    public static void main(String[] args) {

        MP3Player mp3 = new MP3Player();
        //println "MP3Player trying to play song=[${ args[0] }];"
    	
    	if (args.size() > 0)
	    {
            def ok = new File(args[0]).exists();

            println "trying to play song=[${ args[0] }] exists ?="+ok
            try
            {
                if (ok) 
                { 
                    mp3.playSound(args[0]);
                    mp3.playSound('sounds/F5E.mp3') 
                    mp3.play('F53') 
                    mp3.playUChen('F55')
                    def files = new File("sounds")
                    files.each
                    {
                        if (it.endsWith(".mp3"))
                        {
                                println "found .mp3 ="+it
                        }
                    } // end of each

                };
            }

            catch(Exception x) 
            {
                println "could not play ${ args[0] } due to :"+x.message
            } // end of catch
    	} // end of if
    } // end of main

} // end of class



/* groovy timer and timertask
// http://mrhaki.blogspot.fr/2009/11/groovy-goodness-run-code-at-specified.html
import java.util.timer.*  
  
class TimerTaskExample extends TimerTask {  
        public void run() {  
            println new Date()  
        }  
}  
  
int delay = 5000   // delay for 5 sec.  
int period = 1000  // repeat every sec.  
Timer timer = new Timer()  
timer.scheduleAtFixedRate(new TimerTaskExample(), delay, period) 

//--------------------
// or
// File: newtimer.groovy
class GroovyTimerTask extends TimerTask {
    Closure closure
    void run() {
        closure()
    }
}

class TimerMethods {
    static TimerTask runEvery(Timer timer, long delay, long period, Closure codeToRun) {
        TimerTask task = new GroovyTimerTask(closure: codeToRun)
        timer.schedule task, delay, period
        task
    }
}

use (TimerMethods) {
    def timer = new Timer()
    def task = timer.runEvery(1000, 5000) {
        println "Task executed at ${new Date()}."
    }
    println "Current date is ${new Date()}."
}

*/
