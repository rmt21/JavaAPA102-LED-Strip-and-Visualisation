

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import com.google.gson.*;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;



public class Server {

	private ServerSocket serverSocket;
	int count = 0;
	APA102Functions function = new APA102Functions();
	JsonProcessor process = new JsonProcessor(function);
	GpioPinDigitalInput  display, reset;
	
	public Server() throws IOException {
		serverSocket = new ServerSocket(6066);
		serverSocket.setSoTimeout(5000000);
		function.clear();
		function.singleColourAll();
		function.clear();
		
		 Runnable createRecording = new Runnable() {
	            public void run() {
	            	JavaSoundRecorder jsr = new JavaSoundRecorder(function);
	                jsr.capture();
	            }
	        };
	        
	        Thread system = new Thread (createRecording);
	        //system.start();
		
	}
			public void processServer() {
				

				final GpioController gpio = GpioFactory.getInstance();
				
				display = gpio.provisionDigitalInputPin(RaspiPin.GPIO_04, PinPullResistance.PULL_DOWN);
				reset = gpio.provisionDigitalInputPin(RaspiPin.GPIO_05, PinPullResistance.PULL_DOWN);
				
				display.setShutdownOptions(true);
				reset.setShutdownOptions(true);
				
				display.addListener(new GpioPinListenerDigital() {

					@Override
					public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
						if (event.getState().isHigh())
						sendCommand(event);	
					}
				});
				reset.addListener(new GpioPinListenerDigital() {

					@Override
					public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
						if (event.getState().isHigh())
						sendCommand(event);	
					}
				});

			while (true) {			
					//receive from sender, based on type of send on what it requires
					System.out.println("Ready on address: " + serverSocket.getLocalSocketAddress() +":"+ serverSocket.getLocalPort());
					try {
						Socket server;
						server = serverSocket.accept();		
						System.out.println("Connected " + server.getRemoteSocketAddress());
					DataInputStream in = new DataInputStream(server.getInputStream());
					//get from handler
					JsonObject input = (new JsonParser()).parse(in.readUTF()).getAsJsonObject();
					process.processJSON(input, function);
					DataOutputStream out = new DataOutputStream(server.getOutputStream());
					 out.writeBytes("received");
					 out.close();
					server.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}			
			}
			}
			
			public void sendCommand(GpioPinDigitalStateChangeEvent event)
			{
				String compare = event.getPin().getName().substring(5, event.getPin().getName().length());				
				if (compare.equals("2"))
				{
					function.silentClear();
					String result = getNextCommand(function);
					process.processGPIO(result);
					System.out.println(result);
				}
				if (compare.equals("3"))
				{
					process.processGPIO("clear");
				}
			}
			
			public String getNextCommand(APA102Functions function)
			{
				String[] temp = function.getCommands();
				// {"singleColourAll", "fade", "colourChase", "flash"};
				
				for (int i=0; i< temp.length; i++)
				{
					if (temp[i].equals(function.getLastCommand()))
					{
						if (!temp[i+1].isEmpty())
						{
							return temp[i+1];
						}
						else
						{
							return temp[0];
						}
					}
				}
				return temp[0];
			
			}
}

	



