
import java.io.IOException;

import com.pi4j.io.spi.SpiChannel;
import com.pi4j.io.spi.SpiDevice;
import com.pi4j.io.spi.SpiFactory;
import com.pi4j.wiringpi.Spi;

public class APA102Setup {
	
	private int LED_COUNT = 60;
	private int MAX_BRIGHTNESS = 20;
	
	private short reset[];
	private short resetM[];
	private short resetC[];
	private short frameData[][];
	
	public int getLED_COUNT() {
		return LED_COUNT;
	}

	public void setLED_COUNT(int lED_COUNT) {
		LED_COUNT = lED_COUNT;
	}

	public int getMAX_BRIGHTNESS() {
		return MAX_BRIGHTNESS;
	}

	public void setMAX_BRIGHTNESS(int mAX_BRIGHTNESS) {
		MAX_BRIGHTNESS = mAX_BRIGHTNESS;
	}
	
	public APA102Setup()
	{
		// SPI device
	    SpiDevice spi = null;

	    // ADC channel count
	    short ADC_CHANNEL_COUNT = 8;  // MCP3004=4, MCP3008=8
	    
	    try {
			spi = SpiFactory.getInstance(SpiChannel.CS0,
					32000000, // default spi speed 1 MHz
			        SpiDevice.DEFAULT_SPI_MODE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // default spi mode 0
	    
	    reset = new short[] {
	    	(short) 0x0	
	    };
	    resetM = new short[] {
		    	(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    	
		    };
	    resetC = new short[] {
	    		(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    	(short) 0x0,
		    };
	    
	    frameData = new short[LED_COUNT][];
	    clearData();
  
	}
	
	public void clearData()
	{
		for (int ii=0; ii< LED_COUNT; ii++)
		{
		for (int i=0; i<LED_COUNT; i++)
		{
			frameData[i] = new short[] {
					(short) 0xff,
	                (short) 0x0,   							
	                (short) 0x0,   							
	                (short) 0x0
			};
		}
		}
	}
	
	public void writeLEDs()
	{
		// 32 0s, data, 32 0s, 0s to push data
			Spi.wiringPiSPIDataRW(0, resetM, 32);
		for (int i=0; i<LED_COUNT; i++)
		{
			Spi.wiringPiSPIDataRW(0, frameData[i]);
		}
			Spi.wiringPiSPIDataRW(0, resetM);
			Spi.wiringPiSPIDataRW(0, resetC, 19);
	}
	
	public void setLED(int led, short[] value)
	{
		frameData [led] = value;
	}
	
	public void setLEDData(int[][] ledColourArray, int brightness)
	{
		for (int i=0; i<LED_COUNT; i++)
		{
			frameData[i] = new short[] {
					(short) (0xE0 + brightness),
	                (short)ledColourArray[i][2],   							
	                (short)ledColourArray[i][1],   							
	                (short)ledColourArray[i][0]
			};
		}
	}
	
	public void testLED(int ledTotal, int dispLed)
	{
		byte data[] = new byte[] {
                (byte) 0x0
        };
		
		for (int i=0; i<4; i++)
		{
			Spi.wiringPiSPIDataRW(0, data);
		}
		
		byte setData[] = new byte[] {
				(byte) 0xff
		};
		//Spi.wiringPiSPIDataRW(0, setData);
		
		byte frameData[] = new byte[] {
				(byte) 0xff,
                (byte) 0x9b,   							
                (byte) 0x0,   							
                (byte) 0x0,
                (byte) 0xff,
                (byte) 0x0,   							
                (byte) 0x0,   							
                (byte) 0x0,
                (byte) 0xff,
                (byte) 0x0,   							
                (byte) 0x0,   							
                (byte) 0x0,
                (byte) 0xff,
                (byte) 0x0,   							
                (byte) 0x0,   							
                (byte) 0x0,
                (byte) 0xff,
                (byte) 0x0,   							
                (byte) 0x0,   							
                (byte) 0x0,
                (byte) 0xff,
                (byte) 0x0,   							
                (byte) 0x0,   							
                (byte) 0x0,
                (byte) 0xff,
                (byte) 0x0,   							
                (byte) 0x0,   							
                (byte) 0x0,
                (byte) 0xff,
                (byte) 0x0,   							
                (byte) 0x0,   							
                (byte) 0x0,
                (byte) 0xff,
                (byte) 0x0,   							
                (byte) 0x0,   							
                (byte) 0x0,
                (byte) 0xff,
                (byte) 0x0,   							
                (byte) 0x0,   							
                (byte) 0x0,
                (byte) 0xff,
                (byte) 0x0,   							
                (byte) 0x0,   							
                (byte) 0x0,
                (byte) 0xff,
                (byte) 0x0,   							
                (byte) 0x0,   							
                (byte) 0x0
        };
		
		Spi.wiringPiSPIDataRW(0, frameData);
		
		for (int i=0; i<4; i++)
		{
			Spi.wiringPiSPIDataRW(0, data);
		}
		
		
	}

}
