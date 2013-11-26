/*
 * 11/19/2004 : 1.0 moved to LGPL. 
 * 01/01/2004 : Initial version by E.B javalayer@javazoom.net
 *-----------------------------------------------------------------------
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

import java.io.InputStream;
import java.util.Properties;
import javazoom.jl.player.jlp;
import javazoom.jl.player.Player;
import javazoom.jl.decoder.JavaLayerException;
import junit.framework.TestCase;
import java.net.*;
import java.util.*;
import groovy.util.*;
import groovy.util.logging.Log
/**
 * Simple player unit test.
 * It takes around 3-6% of CPU and 10MB RAM under Win2K/PIII/1GHz/JDK1.5.0
 * It takes around 10-12% of CPU and 10MB RAM under Win2K/PIII/1GHz/JDK1.4.1
 * It takes around 08-10% of CPU and 10MB RAM under Win2K/PIII/1GHz/JDK1.3.1
 * @since 0.4 
 */
@Log
public class jlpTest extends TestCase
{
	private Properties props = null;
	private String filename = null;
	
	/**
	 * Constructor for jlpTest.
	 * @param arg0
	 */
	public jlpTest(String arg0)
	{
		super(arg0);
		log.info("jlpTest constructor")
	}
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception
	{
		super.setUp();
		log.info("jlpTest setUp()")
		 // Read in 'mail.groovy'.
        	final ConfigObject config = new ConfigSlurper().parse(new File("/Volumes/FHD-XS/JLayer1.0.1/resources/mp3.properties").toURI().toURL());

/*
		props = new Properties();
		InputStream pin = getClass().getClassLoader().getResourceAsStream("mp3.properties");
		props.load(pin);
*/ 
		String basefile = config.basefile;   // (String) props.getProperty("basefile");
		String name = config.filename;  // (String) props.getProperty("filename");		
		filename = basefile + name;	
		//log.info("jlpTest setUp() found mp.properties and filename=${filename}";);
	}
	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception
	{
		super.tearDown();
		log.info("jlpTest tearDown()")
	}

	public void testPlay()
	{
		String[] args = new String[1];
		args[0] = filename;
		log.info "testPlay()="+filename
		def player = jlp.createInstance(args);
		try
		{
			player.play();
			log.info("jlpTest.groovy did not fail on player.play()");
			//assertTrue("Play",true);	
		}
		catch (Exception e)
		{
			e.printStackTrace();
			//log.info("JavaLayerException : "+e.getMessage(),false);			
		}
	}
}
