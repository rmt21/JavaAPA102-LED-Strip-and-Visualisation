import java.awt.Color;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.sound.sampled.AudioInputStream;

import org.jtransforms.fft.DoubleFFT_1D;

import ai.kitt.snowboy.SnowboyDetect;



public class AudioProcessor {
	
	int NUM_LEDS = 37;
	int COLOURS = 5;
	int LED_SECTION_LENGTH = NUM_LEDS/COLOURS;
	APA102Functions led =  new APA102Functions();
	SnowboyDetect detector;


	public AudioProcessor(APA102Functions led)
	{
		//this.led = led;
		System.loadLibrary("snowboy-detect-java");
		 detector= new SnowboyDetect("common.res","OKGoogle.pmdl");
	}
	
	public void processSoundVis(AudioInputStream stream, byte[] bytes)
	{
		int frameLength = bytes.length/2;
		int frameSize = (int) stream.getFormat().getFrameSize();
		int sampleSize = (int)(Math.round(stream.getFormat().getSampleRate()));

		int numChannels = stream.getFormat().getChannels();
		int[][] toReturn = new int[numChannels][frameLength];
		int sampleIndex = 0;
		for (int i=0; i < bytes.length;)
		{
			for (int ii=0; ii < numChannels; ii++)
			{
				int low = (int) bytes[i];
				i++;
				int high = (int) bytes[i];
				i++;
				int sample = get16(high, low);
				toReturn[ii][sampleIndex] = sample;
			}
			sampleIndex++;
		}
		analyseData(toReturn, sampleSize);
	}
	public void processSoundSpeech(AudioInputStream stream, byte[] bytes)
	{
		int frameLength = bytes.length/2;
		int frameSize = (int) stream.getFormat().getFrameSize();
		int sampleSize = (int)(Math.round(stream.getFormat().getSampleRate()));

		short[] audioData = new short[bytes.length / 2];
		ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(audioData);
		analyseDetection(audioData, sampleSize);
	}
	public void analyseDetection(short[] data, int sampleSize)
	{
		detector.SetSensitivity("1.0");
		int result = detector.RunDetection(data, data.length);
		System.out.println(result);
		if (result > 0)
		{
			System.out.println("Detect");
		}
	}
	
	public void analyseData(int[][] data, int sampleSize)
	{
		// led strip is divided into the colour count, currently 7 colours as per the rainbow. Sounds scale of 5000 either way to categorise colours. 1428 per colour.
		//Negative to positive......-5000-3572 pink, -3571-2143 Cyan, -2142-714 Blue, -713-715 Green, 716-2144 Yellow, 2145-3573 Orange, 3574-5000 Red
		//                            0-1428            1429-2857           2858-4286        4287-5715     5716-7143        7144-8571         8572-10000
		//bass, mid bass, mid upmid, high
		double[] freq_bin= {5, 10, 20, 40, 80, 160};
		double[] amp = new double[sampleSize*2];
		
		for (int i=0;i< data[0].length; i++)
		{
			amp[i] = (double) data[0][i];
		}
		DoubleFFT_1D fft = new DoubleFFT_1D(sampleSize);
		fft.realForward(amp);
		double[] peakMaxArray = new double[6];
		for (int i=0; i< sampleSize/2; i++)
		{
			double real = amp[2*i];
			double img = amp[2*i+1];
			double mag = Math.sqrt(real*real+img*img);
			double freq = i*sampleSize/amp.length;
			for (int ii=0; ii< 5; ii++)
			{
				
				if ((freq>freq_bin[ii]) && (freq<=freq_bin[ii+1]))
				{
					if (mag > peakMaxArray[ii])
					{
						peakMaxArray[ii] = mag;
					}
				}
			}
		}
		
		int maxPeak =0;
		for (int i=1;i<= 5; i++)
		{
			if (peakMaxArray[i] > peakMaxArray[i-1])
			{
				maxPeak = i;
			}
		}
		
		
		
		final int fmaxPeak = maxPeak;
		final double[] visArray = peakMaxArray;
		Thread ledData = new Thread(new Runnable() {
        	public void run() {
        		//sendLedData(fmaxPeak);
        		led.visualiser2(visArray, 400000, COLOURS);
        	}
        });
        ledData.start();
		//sendLedData(maxPeak);
		//System.out.println(maxPeak);
	}
	
	
	public void sendLedData(int value)
	{
		Color color = null;
		if (value==0)
		{
			color = Color.PINK;
			led.visualiser(color, 0, COLOURS);
		}
		if (value==1)
		{
			color = Color.BLUE;
			led.visualiser(color, 1, COLOURS);
		}
		if (value==2)
		{
			color = Color.GREEN;
			led.visualiser(color, 2, COLOURS);
		}
		if (value==3)
		{
			color = Color.YELLOW;
			led.visualiser(color, 3, COLOURS);
		}
		if (value==4)
		{
			color = Color.RED;
			led.visualiser(color, 4, COLOURS);
		}
	}
	
	public int get16 (int high, int low)
	{
		return (high << 8) + (low & 0x00ff);
	}

}
