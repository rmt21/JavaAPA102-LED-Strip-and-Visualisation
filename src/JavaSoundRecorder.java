import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;



	public class JavaSoundRecorder {
		
		APA102Functions led;
		AudioFormat format;
		
		public JavaSoundRecorder(APA102Functions led)
		{
			this.led = led;
			format = getAudioFormat();

		}
		
		AudioProcessor ap = new AudioProcessor(led);
		
	    // record duration, in milliseconds
	    static final long RECORD_TIME_VIS = 125;  
	    static final long RECORD_TIME_SPEECH = 5000; 
	    // path of the wav file
	    //File wavFile = new File("RecordAudio.wav");
	    AudioInputStream  ais;
	    ByteArrayOutputStream out;
	 
	    // format of audio file
	    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
	 
	    // the line from which audio data is captured
	    TargetDataLine line;
	 
	    /**
	     * Defines an audio format
	     */
	    AudioFormat getAudioFormat() {
	        float sampleRate = 8000;
	        int sampleSizeInBits = 8;
	        int channels = 2;
	        boolean signed = true;
	        boolean bigEndian = true;
	        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
	                                             channels, signed, bigEndian);
	        return format;
	    }
	    
	    Thread complete = new Thread(new Runnable() {
	    	public void run() {
	    		stop();
	    	}
	    });
	 
	    /**
	     * Captures the sound and record into a WAV file
	     */
	    public void capture() {
	        try {
	            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
	 
	            // checks if system supports the data line
	            if (!AudioSystem.isLineSupported(info)) {
	                System.out.println("Line not supported");
	                System.exit(0);
	            }
	            line = (TargetDataLine) AudioSystem.getLine(info);
	            line.open(format);
	            line.start();   // start capturing
	 
	           // System.out.println("Start capturing...");
	 
	            ais = new AudioInputStream(line);
	            out = new ByteArrayOutputStream();
	            int numBytesRead;
	            byte[] data = new byte[line.getBufferSize() / 5];
	 
	           // System.out.println("Start recording...");
	            Thread complete = new Thread(new Runnable() {
	            	public void run() {
	            		stop();
	            	}
	            });
	            complete.start();
	            //AudioSystem.write(ais, fileType, out);
	            while(line.isOpen())
	            {
	            	numBytesRead = line.read(data, 0, data.length);
	            	out.write(data, 0, numBytesRead);
	            }	                  
	        } catch (LineUnavailableException ex) {
	            ex.printStackTrace();
	        }
	    }
	 
	    /**
	     * Closes the target data line to finish capturing and recording
	     */
	   public void stop() {
		   if (led.showVisualisation == true)
	        {
	    	try {
				Thread.sleep(RECORD_TIME_VIS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        line.stop();
	        line.close();
	        
	        	ap.processSoundVis(ais, out.toByteArray());
	        }
	        else
	        {
	        	try {
					Thread.sleep(RECORD_TIME_SPEECH);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        line.stop();
		        line.close();
	        	ap.processSoundSpeech(ais, out.toByteArray());
	        }
	        capture();
	    }
	}

