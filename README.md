FreeMP3
=======
Travis Build Status for Master Branch: [![Build Status](https://travis-ci.org/jnorthr/FreeMP3.svg?branch=master)](https://travis-ci.org/jnorthr/FreeMP3)

UChen Users see bototm of this post
------------

A Clone of JLayer MP3 Decoder w/Mods to use on JDK1.5+ JVM systems. This version of JLayer includes a revised file directory structure that is compatible with the gradle build tool. Additional modifications by FairChild Computing Ltd, U.K. (jnorthr)

 JLayer 1.0.2
 JavaZOOM 1999-2008

 Project Homepage :
   http://www.javazoom.net/javalayer/javalayer.html 

 JAVA and MP3 online Forums :
   http://www.javazoom.net/services/forums/index.jsp
-----------------------------------------------------

DESCRIPTION :
-----------
JLayer is a library that decodes/plays/converts MPEG 1/2/2.5 Layer 1/2/3
(i.e. MP3) in real time for the JAVA(tm) platform. This is a non-commercial project 
and anyone can add his contribution. JLayer is licensed under LGPL (see LICENSE.txt).


FAQ : 
---

- How to install JLayer ?
  Before running JLayer you must set PATH and CLASSPATH for JAVA
  and you must add jl1.0.1.jar to the CLASSPATH.

- Do I need JMF to run JLayer player ?
  No, JMF is not required. You just need a JVM JavaSound 1.0 compliant.
  (i.e. JVM1.3 or higher).

- How to run the MP3TOWAV converter ?
  java javazoom.jl.converter.jlc -v -p output.wav yourfile.mp3
  (Note : MP3TOWAV converter should work under jdk1.1.x or higher)

- How to run the simple MP3 player ?
  java javazoom.jl.player.jlp localfile.mp3
   or
  java javazoom.jl.player.jlp -url http://www.aserver.com/remotefile.mp3
  Note : MP3 simple player only works under JVM that supports JavaSound 1.0 (i.e JDK1.3.x+)

- How to run the advanced (threaded) MP3 player ?
  java javazoom.jl.player.advanced.jlap localfile.mp3

- Does simple MP3 player support streaming ?
  Yes, use the following command to play music from stream :
  java javazoom.jl.player.jlp -url http://www.shoutcastserver.com:8000
  (If JLayer returns without playing SHOUTcast stream then it might mean 
   that the server expect a winamp like "User-Agent" in HTTP request).

- Does JLayer support MPEG 2.5 ?
  Yes, it works fine for all files generated with LAME.

- Does JLayer support VBR ?
  Yes, It supports VBRI and XING VBR header too. 

- How to get ID3v1 or ID3v2 tags from JLayer API ?
  The API provides a getRawID3v2() method to get an InputStream on ID3v2 frames.

- How to skip frames to have a seek feature ?
  See javazoom.jl.player.advanced.jlap source to learn how to skip frames.

- How much memory/CPU JLayer needs to run ?
  Here are our benchmark notes :
    - Heap use range : 1380KB to 1900KB - 370 classes loaded. 
    - Footprint : ~8MB under WinNT4/Win2K + J2SE 1.3 (Hotspot).
                  ~10MB under WinNT4/Win2K + J2SE 1.4.1 (Hotspot).
    - CPU usage : ~12% under PIII 800Mhz/WinNT4+J2SE 1.3 (Hotspot).
                  ~8% under PIII 1Ghz/Win2K+J2SE 1.3.1 (Hotspot).
                  ~12% under PIII 1Ghz/Win2K+J2SE 1.4.1 (Hotspot).
                  ~1% under PIII 1Ghz/Win2K+J2SE 1.5.0 (Hotspot).

- How to contact JLayer developers ?
  Try to post a thread on Java&MP3 online forums at :
  http://www.javazoom.net/index.shtml
  You can also contact us at jlayer@javazoom.net for contributions.
 

KNOWN PROBLEMS :
--------------
99% of MP3 plays well with JLayer but some (1%) return an ArrayIndexOutOfBoundsException 
while playing. It might come from invalid audio frames. 
Workaround : Just try/catch ArrayIndexOutOfBoundsException in your code to skip non-detected invalid frames.

--------
= UChen Links 

== Leanne's Repos

 * https://github.com/leannenorthrop/classical-tibetan[Classical Tibetan]
 * https://github.com/leannenorthrop/markdown-js/blob/wylie/src/dialects/wylie/wmd2uchen.js[UChen Map]
 * http://leannenorthrop.github.io/classical-tibetan/editor/?layout=contrib#[Markdowb Tibetan Editor]
 * http://leannenorthrop.github.io/classical-tibetan/[Sample Page ]of Tibetan
 * http://bohoomil.com/doc/05-fonts/[Bohoomil Fonts]
 * https://sites.google.com/site/chrisfynn2/home/fonts/jomolhari[Jomolhari Fonts]
 * http://groovy.codehaus.org/jsr/spec/AltChapter03LexicalStructure.html[Groovy Fonts] Overview
 * http://www.unicode.org/versions/Unicode7.0.0/ch13.pdf[Unicode Overview of UChen Syntax] - see page 499-501
 * http://unicode.org/charts/PDF/U0F00.pdf[Unicode PDF] - see page 2 and 3

== UChen Logic

Have done an addition to the FreeMP3 suite. This logic allows the pronunciation of UChen (or any) sound fragments. The new groovy module named MP3Player has been included to play these sound fragments. A simple code sample to use this feature would look like this:

.Play A Sound
[source,groovy]
----
MP3Player mp3 = new MP3Player();
mp3.playSound('sounds/F5E.mp3') 
mp3.play('F53')  // actually translated to look for a sound fragment as 'sounds/F53.mp3' 
----

To use this MP3Player, we need an .mp3 file. These can be created on Apple Mac OSX systems using the QuickTime Player to record audio thru the Mac microphone. Then save it as a .m4a audio (not movie) file. This must be changed into a .mp3 file using a free tool here http://www.nch.com.au/switch/index.html?gclid=CKbEtY6Ex7QCFW3KtAod-TYAlQ[called Switch]. That tool reads a .m4a file and writes a .mp3 equivalent. 

See *'resources/fontmap.uchen'* for a map of UChen sylables to unicode characters. The Unicode character like, for example, *UChenMap["ch"] = "\u0F46";* would need a sound file named 'sounds/F46.mp3' so convert the sound fragment and name it according to the unicode character it represents.

== Apple Mac OSX and Sound Synthesis

There are tools on the Apple to allow disabled users better access. One of these tools if http://www.apple.com/accessibility/osx/voiceover/[VoiceOver for OSX]. It offers a nice demo of text-to-speech features. Tools like this can be accessed from the javax.accessibility package with Apple's version of Java. You can hear one such sample here: +++
<h3>Anna</h3>
<P>
<script language="JavaScript" src="http://www.explainthatstuff.com/audio/audio-player.js"></script><object type="application/x-shockwave-flash" data="http://www.explainthatstuff.com/audio/player.swf" id="audioplayer3" height="24" width="290">
<param name="movie" value="http://www.explainthatstuff.com/audio/player.swf">
<param name="FlashVars" value="playerID=3&amp;bg=0xBCB19B&amp;leftbg=0xFF0000&amp;lefticon=0x111111&amp;rightbg=0xFF0000&amp;rightbghover=0xDCDBDC&amp;righticon=0x111111&amp;righticonhover=0xCC4321&amp;text=0x666666&amp;slider=0x666666&amp;track=0xFFFFFF&amp;border=0x666666&amp;loader=0x9FFFB8&amp;soundFile=http://www.explainthatstuff.com/audio/iloveanna.mp3">
<param name="quality" value="high">
<param name="menu" value="false">
<param name="wmode" value="transparent">
</object> 
</P>
+++ and it's called Anna. See http://www.explainthatstuff.com/how-speech-synthesis-works.html[Explain That Stuff] for more info.

In the 1980's, Texas Instruments developed a tool to let children learn to spell english words by speaking to the child. This tool was the *Speak-and-Spell* and you can http://www.speaknspell.co.uk/speaknspell.html[try it out yourself here.]

''' 


