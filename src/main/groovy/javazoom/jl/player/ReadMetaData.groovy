 /*-----------------------------------------------------------------------
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU Library General Public License as published
 *   by the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Library General Public License for more details.
 *
 *   You should have received a copy of the GNU Library General Public
 *   License along with this program; if not, write to the Free Software
 *   Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *----------------------------------------------------------------------
 */

package javazoom.jl.player;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import javazoom.jl.decoder.Bitstream
import javazoom.jl.decoder.Header
import javazoom.jl.decoder.BitstreamException
import groovy.util.*;

public class ReadMetaData
{
    private Bitstream bin = null;
    private FileInputStream mp3in;
    public def out=null;
    public String filename = null;
    public String propertyfilename = null;
    def conf   // new ConfigSlurper
    def config // parsed config content
    def ok = false; // set true when valid song was found, and metadata was written
    def int playtime = 10 // seconds

    public ReadMetaData()
    {
    } // end of default constructor

    public ReadMetaData(String nm)
    {
	this.setMetaData(nm);
        this.readStream()
    } // end of named constructor


    // return play time int as seconds if available(not null) else return 10 sec.s
    public getPlayTime()
    {
	return playtime;
    } // end of seconds


    // return play time int as seconds if available(not null) else return 10 sec.s
    public getPlayTimeMinutes()
    {
	int m = (playtime>59) ? (playtime / 60) : 0 ;
	int s = playtime - (m*60);
	return (m>0) ? "$m min.s ${s} sec.s" :  "${s} sec.s";
    } // end of minutess


    // declare name of .mp3 file to discovery
    public setMetaData(String ifn) throws IOException
    {
	this.filename = ifn;
	String fn = new File(ifn).canonicalPath;

	int i = fn.lastIndexOf('\\');
	int j = fn.lastIndexOf('/');
	int k = (j<0)?i:j;
	k = (k<0) ? 0 : k ;
	this.propertyfilename = "resources"+fn.substring(k)+ ".properties"
	this.out = new File(propertyfilename);

	println "i=$i j=$j readMetaData properties:"+this.propertyfilename+" from "+fn
	//this.out = new File(fn);

        try
        {
            mp3in = new FileInputStream(this.filename);
            bin = new Bitstream(mp3in);
	    this.out.write("filename=\""+fn+"\"\n");
	    this.ok = true;
        }
        catch (IOException x)
        {
            System.err.println("BitstreamTest(${filename}) not found");
            //x.printStackTrace();
            //System.exit(0);
        } // end of catch
    } // end of method


    // ----------------------------------
    // read song and produce metadata
    public void readStream()
    {
	if (!ok) {return;}

        try
        {
            InputStream id3in = bin.getRawID3v2();
            int size = id3in.available();
            Header header = bin.readFrame();

            if (out != null)
            {
                out.append("ID3v2Size=\""+size+"\"\n");
                out.append("version=\""+header.version()+"\"\n");
                out.append("version_string=\""+header.version_string()+"\"\n");
                out.append("layer=\""+header.layer()+"\"\n");
                out.append("frequency=\""+header.frequency()+"\"\n");
                out.append("frequency_string=\""+header.sample_frequency_string()+"\"\n");
                out.append("bitrate=\""+header.bitrate()+"\"\n");
                out.append("bitrate_string=\""+header.bitrate_string()+"\"\n");
                out.append("mode=\""+header.mode()+"\"\n");
                out.append("mode_string=\""+header.mode_string()+"\"\n");
                out.append("slots=\""+header.slots()+"\"\n");
                out.append("vbr=\""+header.vbr()+"\"\n");
                out.append("vbr_scale=\""+header.vbr_scale()+"\"\n");
                out.append("max_number_of_frames=\""+header.max_number_of_frames(mp3in.available()) +"\"\n");
                out.append("min_number_of_frames=\""+header.min_number_of_frames(mp3in.available()) +"\"\n");
                out.append("ms_per_frame=\""+header.ms_per_frame()+"\"\n");
                out.append("frames_per_second=\""+(float) ((1.0 / (header.ms_per_frame())) * 1000.0)+"\"\n");
                out.append("total_ms=\""+header.total_ms(mp3in.available())+"\"\n");
                out.append("SyncHeader=\""+header.getSyncHeader()+"\"\n");
                out.append("checksums=\""+header.checksums()+"\"\n");
                out.append("copyright=\""+header.copyright()+"\"\n");
                out.append("original=\""+header.original()+"\"\n");
                out.append("padding=\""+header.padding()+"\"\n");
                out.append("framesize=\""+header.calculate_framesize()+"\"\n");
                out.append("number_of_subbands=\""+header.number_of_subbands()+"\"\n");                
            }

            // To load this into a readable config you can do:
    	    def u = new File(this.propertyfilename).toURI().toURL();

	    conf = new ConfigSlurper()
            config = conf.parse(u);
	    playtime = 	(config.total_ms) ? ( ( new Float(config.total_ms) / 1000F ) as int) + 1 :  10;
	    println this.filename+" suggested a playtime of "+getPlayTimeMinutes();
        }
        catch (BitstreamException e)
        {
            assertTrue("BitstreamException : "+e.getMessage(),false);
        }        
        catch (IOException e)
        {
            assertTrue("IOException : "+e.getMessage(),false);
        }
   } // end of method
   
    public static void main(String[] args)
    {
        println "--- starting ---"
        def filename = "resources/ring.mp3";    
        def rmd = new ReadMetaData();
	rmd.setMetaData(filename);
        rmd.readStream()
        println "--- got it ---"

        // This was loaded into a readable config you can do:
        println "${filename} song time in sec.s:"+rmd.getPlayTime();
        println "${filename} song time in m/s:"+rmd.config.total_ms;
        println "${filename} song time is:"+rmd.getPlayTimeMinutes();

        println "--- got it ---"
        filename = "resources/Love.mp3";    
        rmd = new ReadMetaData(filename);
        println "${filename} song time in sec.s:"+rmd.getPlayTime();
        println "${filename} song time in m/s:"+rmd.config.total_ms;
        println "${filename} song time is:"+rmd.getPlayTimeMinutes();


        println "--- try non-existent file ---"
        filename = "resources/Lovex.mp3";    
        rmd = new ReadMetaData(filename);
	println "${filename} song time in sec.s:"+rmd.getPlayTime(); 
        if (rmd.ok) { println "${filename} song time in m/s:"+rmd.config.total_ms; }
        println "${filename} song time is:"+rmd.getPlayTimeMinutes();

        println "--- ending ---"
    } // end of main

   
} // end of class
   