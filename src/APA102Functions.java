

import java.awt.Color;
import java.io.IOException;
import java.util.Random;

import com.google.gson.JsonArray;

public class APA102Functions {
	
	APA102Setup ledDriver = new APA102Setup();
	
	private int MAX_BRIGHTNESS = ledDriver.getMAX_BRIGHTNESS();
	String REMOTE_IP = "192.168.0.13";
	
	int LED_COUNT = ledDriver.getLED_COUNT();
	int lastSection =0;
	
	boolean choiceContinue = true;
	
	boolean flash = true;
	
	boolean rainbow = true;
	boolean fade = true;
	public boolean showVisualisation = true;
	
	private int[][] ledColourArray = new int[LED_COUNT][3];
	int[] lastLedColourArray = new int[3];
	int lastStart =0;
	
	private int brightness = ledDriver.getMAX_BRIGHTNESS();
	
	JSONSend sender = new JSONSend();
	JSONCreate create = new JSONCreate();
	
	public String lastCommand = "singleColourAll";
	
	public String[] commands = {"singleColourAll", "fade", "colourChase", "flash", "visualiser"};
	
	public String getLastCommand() {
		return lastCommand;
	}

	public void setLastCommand(String lastCommand) {
		this.lastCommand = lastCommand;
	}

	public String[] getCommands() {
		return commands;
	}

	public void setCommands(String[] commands) {
		this.commands = commands;
	}

	public void singleColourAll()
	{
		Random randomGenerator = new Random();
	      int randomIntR = randomGenerator.nextInt(255);
	      int randomIntG = randomGenerator.nextInt(255);
	      int randomIntB = randomGenerator.nextInt(255);
	      
	      //System.out.println(randomInt + " " + randomInt2);
	      
	      for (int i=0; i< LED_COUNT; i++)
	      {
	    	  ledColourArray[i][0] = 0;
	    	  ledColourArray[i][1] = 0;
	    	  ledColourArray[i][2] = 0; 
	      }
	      
	      for (int i=0; i< LED_COUNT; i++)
	      {
	    	  ledColourArray[i][0] = randomIntR;
	    	  ledColourArray[i][1] = randomIntG;
	    	  ledColourArray[i][2] = randomIntB;
	    	  
	      }
	     
	      ledDriver.setLEDData(ledColourArray, brightness);
	      ledDriver.writeLEDs();
	      
	      lastCommand = "singleColourAll";
	    
	}
	
	public void singleColourAllFade(APA102Construct construct)
	{
		  Random randomGenerator = new Random();
	      int randomIntR = randomGenerator.nextInt(255);
	      int randomIntG = randomGenerator.nextInt(255);
	      int randomIntB = randomGenerator.nextInt(255);
	      
	      //System.out.println(randomInt + " " + randomInt2);
	      
	      for (int i=0; i< LED_COUNT; i++)
	      {
	    	  ledColourArray[i][0] = 0;
	    	  ledColourArray[i][1] = 0;
	    	  ledColourArray[i][2] = 0; 
	      }
	      
	      for (int i=0; i< LED_COUNT; i++)
	      {
	    	  ledColourArray[i][0] = randomIntR;
	    	  ledColourArray[i][1] = randomIntG;
	    	  ledColourArray[i][2] = randomIntB;
	    	  
	      }
	      
	    
	      try {
	    	  for (int i=0; i< MAX_BRIGHTNESS; i++)
	 	     {
	 	      ledDriver.setLEDData(ledColourArray, i);
	 	      ledDriver.writeLEDs();
			Thread.sleep(Integer.valueOf(construct.getDelay()));
	 	     }
			for (int i=MAX_BRIGHTNESS; i > 0; i--)
		     {
		      ledDriver.setLEDData(ledColourArray, i);
		      ledDriver.writeLEDs();
		      Thread.sleep(Integer.valueOf(construct.getDelay()));
		     }
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	      lastCommand = "singleColourAllFade";
	}
	
	public void singleColourSpecify(int r, int g, int b)
	{
		for (int i=0; i< LED_COUNT; i++)
	      {
	    	  ledColourArray[i][0] = 0;
	    	  ledColourArray[i][1] = 0;
	    	  ledColourArray[i][2] = 0; 
	      }
	      
	      for (int i=0; i< LED_COUNT; i++)
	      {
	    	  ledColourArray[i][0] = r;
	    	  ledColourArray[i][1] = g;
	    	  ledColourArray[i][2] = b;
	    	  
	      }
	      
	      System.out.println(r + g + b);
	     
	      ledDriver.setLEDData(ledColourArray, brightness);
	      ledDriver.writeLEDs();
	      lastCommand = "singleColourSpecify";
	}
	
	public void colourCycleAll()
	{
		
	}
	
	public void colourCycleSpecify(int[][] ledColourArray)
	{
		
	}
	
	public void colourChase()
	{
		choiceContinue = true;
		Thread threadColourChase = new Thread(new Runnable() {
		     public void run() {
		while (choiceContinue == true)
		{
		Random randomGenerator = new Random();
		
		for (int ii=0; ii< LED_COUNT-5; ii++)
		{
	      int randomIntR = randomGenerator.nextInt(255);
	      int randomIntG = randomGenerator.nextInt(255);
	      int randomIntB = randomGenerator.nextInt(255);
	      
	      //System.out.println(randomInt + " " + randomInt2);
	      for (int i=0; i< LED_COUNT; i++)
	      {
	    	  ledColourArray[i][0] = 0;
	    	  ledColourArray[i][1] = 0;
	    	  ledColourArray[i][2] = 0; 
	      }
	    	  ledColourArray[ii][0] = randomIntR;
	    	  ledColourArray[ii][1] = randomIntG;
	    	  ledColourArray[ii][2] = randomIntB;
	    	  ledColourArray[ii+1][0] = randomIntR;
	    	  ledColourArray[ii+1][1] = randomIntG;
	    	  ledColourArray[ii+1][2] = randomIntB;
	    	  ledColourArray[ii+2][0] = randomIntR;
	    	  ledColourArray[ii+2][1] = randomIntG;
	    	  ledColourArray[ii+2][2] = randomIntB;
	    	  ledColourArray[ii+3][0] = randomIntR;
	    	  ledColourArray[ii+3][1] = randomIntG;
	    	  ledColourArray[ii+3][2] = randomIntB;
	    	  ledColourArray[ii+4][0] = randomIntR;
	    	  ledColourArray[ii+4][1] = randomIntG;
	    	  ledColourArray[ii+4][2] = randomIntB;
	    	  ledColourArray[ii+5][0] = randomIntR;
	    	  ledColourArray[ii+5][1] = randomIntG;
	    	  ledColourArray[ii+5][2] = randomIntB;
	    	  
		      ledDriver.setLEDData(ledColourArray, brightness);
		      ledDriver.writeLEDs();
		      try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		}
		     }
		});
		threadColourChase.start();
		lastCommand = "colourChase";

	}

	public void colourFlash(APA102Construct construct)
	{
		flash = true;
		Thread threadFlash = new Thread(new Runnable() {
		     public void run() {
		while (flash == true)
		{
			singleColourAll();
			try {
				Thread.sleep(Integer.valueOf(construct.getDelay()));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		     }});
		threadFlash.start();
		lastCommand = "flash";
	}
	
	public void rainbow(APA102Construct construct)
	{
		rainbow = true;
		Thread threadRainbow = new Thread(new Runnable() {
		     public void run() {
		while (rainbow == true)
		{
			//singleColourSpecify();
			try {
				Thread.sleep(Integer.valueOf(construct.getDelay()));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		     }});
		threadRainbow.start();
		lastCommand = "rainbow";
	}
	
	public void colourFade(APA102Construct construct)
	{
		fade = true;
		Thread threadFade = new Thread(new Runnable() {
		     public void run() {
		while (fade == true)
		{
			singleColourAllFade(construct);
			try {
				Thread.sleep(Integer.valueOf(construct.getDelay()));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		     }});
		threadFade.start();
		lastCommand = "fade";
	}
	public void clear()
	{
		choiceContinue = false;
		flash = false;
		fade = false;
		rainbow = false;
		
		for (int i=0; i<25; i++)
		{
		ledDriver.clearData();
		ledDriver.writeLEDs();
		}
		
		lastCommand = "clear";
	}
	public void silentClear()
	{
		choiceContinue = false;
		flash = false;
		fade = false;
		rainbow = false;
		
		ledDriver.clearData();
		ledDriver.writeLEDs();
		
	}
	
	public void setBrightness(int input)
	{
		brightness = input;
		if (brightness > MAX_BRIGHTNESS)
		{
			brightness = MAX_BRIGHTNESS;
		}
		if (brightness <= 0)
		{
			brightness = 1;
		}
	}
	public int getBrightness()
	{
		return brightness;
	}
	
	public void visualiser2(double[] input, int range, int sections)
	{
		int sectionLength = LED_COUNT/sections;
		
		String[] params = new String[5];
		params[0] = "array";
		params[1] = REMOTE_IP;
		params[2] = "125";
		params[3] = "20";
		params[4] = "RGB/Visualiser";
		
		Runnable sendData = new Runnable() {
            public void run() {

        		try {
        			sender.sendData(create.createData(params, input), REMOTE_IP);
        		} catch (IOException e) {
        			// TODO Auto-generated catch block
        			//e.printStackTrace();
        		}
            }
        };
        Thread tSendData = new Thread (sendData);
        tSendData.start();
		
		
		for (int i=0; i< input.length; i++)
		{
			for (int ii=sectionLength*i; ii< (sectionLength*i)+(sectionLength); ii++)
			{
				if (ii < LED_COUNT)
				{
				Color color = getColour(input[i], input.length, range);
				ledColourArray[ii][0] = color.getRed();
		    	ledColourArray[ii][1] = color.getGreen();
		    	ledColourArray[ii][2] = color.getBlue();
				}
			}
		}
		
	
		ledDriver.setLEDData(ledColourArray, brightness);
	    ledDriver.writeLEDs();
	}
	public void arrayVisualisation(double[] input) 
	{
		int sectionLength = LED_COUNT/5;
		
		for (int i=0; i< input.length; i++)
		{
			for (int ii=sectionLength*i; ii< (sectionLength*i)+(sectionLength); ii++)
			{
				if (ii < LED_COUNT)
				{
				Color color = getColour(input[i], input.length, 400000);
				ledColourArray[ii][0] = color.getRed();
		    	ledColourArray[ii][1] = color.getGreen();
		    	ledColourArray[ii][2] = color.getBlue();
				}
			}
		}
		ledDriver.setLEDData(ledColourArray, brightness);
	    ledDriver.writeLEDs();
	}
	
	public Color getColour(double value, int length, int range)
	{
		//System.out.println(value);
		double param = range/length;
		if (value > 0 && value < param)
		{
			return new Color(73, 213, 255); //light blue
		}
		if (value > param && value < param*2)
		{
			return Color.GREEN;
		}
		if (value > param*2 && value < param*3)
		{
			return Color.YELLOW;
		}
		if (value > param*3 && value < param*4)
		{
			return Color.MAGENTA;
		}
		if (value > param*4 && value < param*5)
		{
			return Color.RED;
		}
		return Color.WHITE;
	}
	
	public void visualiser(Color color, int section, int colourCount)
	{		
		int sectionLength = LED_COUNT/colourCount;
		ledColourArray = clearArrData();
		int startColour = sectionLength*section;
		int endColour = startColour+sectionLength;
		
		for (int i=startColour; i<=endColour; i++)
		{
			ledColourArray[i][0] = color.getRed();
	    	ledColourArray[i][1] = color.getGreen();
	    	ledColourArray[i][2] = color.getBlue();
		}
		for (int i=lastStart; i<=lastStart+5; i++)
		{
			ledColourArray[i][0] = lastLedColourArray[0];
	    	ledColourArray[i][1] = lastLedColourArray[1];
	    	ledColourArray[i][2] = lastLedColourArray[2];
		}
		
		lastLedColourArray = ledColourArray[startColour];
		lastStart = startColour;
		
		ledDriver.setLEDData(ledColourArray, brightness);
	    ledDriver.writeLEDs();
	}
	
	private int[][] clearArrData()
	{
		Random randomGenerator = new Random();
		int randomIntR = randomGenerator.nextInt(255);
	    int randomIntG = randomGenerator.nextInt(255);
	    int randomIntB = randomGenerator.nextInt(255);
	    int[][] array = new int[LED_COUNT][3];
		for (int i=0; i<LED_COUNT; i++)
		{
			array[i][0] =17; //randomIntR;
	    	array[i][1] = 184;//randomIntG;
	    	array[i][2] =255; //randomIntB;
		}
		return array;
	}
	

}
