


import java.io.UnsupportedEncodingException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;


public class JsonProcessor {

	Gson gson = new Gson();
	String saveDirection = "stop";
	APA102Construct construct = new APA102Construct();
	APA102Functions function;
	String delay = "100";
	
	public JsonProcessor(APA102Functions function)
	{
		this.function = function;
		function.setBrightness(15);
		construct.setDelay(delay);
	}
	
	public void processJSON(JsonObject input, APA102Functions function)
	{	 
		String type = input.get("type").getAsString();
		delay = input.get("delay").getAsString();
		//function.setBrightness(Integer.valueOf(input.get("brightness").getAsString()));
		processOptions(type, input);
	}
	
	public void processGPIO(String input)
	{
		function.setBrightness(15);
		processOptions(input, null);
	}
		
		private void processOptions(String type, JsonObject input)
		{
		
			if (type.equals("Brightness+"))
			{
				int newBrightness = function.getBrightness() + 1;
				function.setBrightness(newBrightness);
			}
			if (type.equals("Brightness-"))
			{
				int newBrightness = function.getBrightness() - 1;
				function.setBrightness(newBrightness);
			}
			
		if (!type.equals("array"))
		{
			function.clear();
			function.showVisualisation = false;
		}
			
		if (type.equals("singleColourAll"))
		{
			construct.setType(type);
			function.singleColourAll();
		}
		if (type.equals("default"))
		{
			construct.setType(type);
			function.singleColourSpecify(255, 103, 23);
		}
		if (type.equals("colourChase"))
		{
			construct.setType(type);
			function.colourChase();
		}
		if (type.equals("clear"))
		{
			construct.setType(type);
			function.clear();
		}
		if (type.equals("flash"))
		{
			
			construct.setType(type);
			construct.setDelay(delay);
			function.colourFlash(construct);
		}
		if (type.equals("fade"))
		{
			construct.setType(type);
			construct.setDelay(delay);
			function.colourFade(construct);
		}
		if (type.equals("visualiser"))
		{
			construct.setType(type);
			function.showVisualisation = true;
		}
		if (type.equals("array"))
		{
			construct.setType(type);
			double[] output = gson.fromJson(input.get("array").getAsString(), double[].class);
			function.arrayVisualisation(output);
		}
		if (type.startsWith("chooseColour"))
		{
			construct.setType(type);
			String r,g,b;
			r = input.get("r").getAsString();
			g = input.get("g").getAsString();
			b = input.get("b").getAsString();
			function.singleColourSpecify(Integer.valueOf(r), Integer.valueOf(g), Integer.valueOf(b));
		}
		}
	}

