import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class JSONCreate {

    public JsonObject createData(String[] params, double[] dArray) {
        JsonObject object = new JsonObject();
        Gson gson = new Gson();

        String brightness = params[3];
        object.addProperty("brightness", brightness);
        String type = params[0];
        String delay = params[2];

        if (params[4].equals("LED"))
        {
            if (type.equals("AllOn"))
            {
                object.addProperty("type", "all");
            }
            if (type.equals("AllOff"))
            {
                object.addProperty("type", "off");
            }
            if (type.equals("red"))
            {
                object.addProperty("type", "red");
            }
            if (type.equals("green"))
            {
                object.addProperty("type", "green");
            }
            if (type.equals("blue"))
            {
                object.addProperty("type", "blue");
            }
            if (type.equals("white"))
            {
                object.addProperty("type", "white");
            }
            if (type.equals("cycle"))
            {
                object.addProperty("type", "cycle");
            }
            if (type.equals("pulse"))
            {
                object.addProperty("type", "pulse");
            }
        }

        if (params[4].equals("Billboard"))
        {
            if (type.equals("AllOn"))
            {
                String parameter = "ON";
                object.addProperty("type", "billboardScreenStatus");
                object.addProperty("parameter", parameter);
            }
            if (type.equals("AllOff"))
            {
                String parameter = "OFF";
                object.addProperty("type", "billboardScreenStatus");
                object.addProperty("parameter", parameter);
            }
            if (type.equals("singleColourAll"))
            {
                object.addProperty("type", type);
            }
            if (type.equals("colourChase"))
            {
                object.addProperty("type", type);
            }
            if (type.equals("clear"))
            {
                object.addProperty("type", type);
            }
            if (type.equals("flash"))
            {

                object.addProperty("type", type);
                object.addProperty("delay", delay);
            }
            if (type.equals("fade"))
            {
                object.addProperty("type", type);
                object.addProperty("delay", delay);
            }
            if (type.startsWith(":"))
            {
                object.addProperty("type", type);
            }
        }

        if (params[4].equals("RGB/Visualiser"))
        {
            if (type.equals("Single Colour"))
            {
                object.addProperty("type", "singleColourAll");
                object.addProperty("delay", delay);
            }
            if (type.equals("On"))
            {
                object.addProperty("type", "singleColourAll");
                object.addProperty("delay", delay);
            }
            if (type.equals("Off"))
            {
                object.addProperty("type", "clear");
                object.addProperty("delay", delay);
            }
            if (type.equals("colourChase"))
            {
                object.addProperty("type", type);
                object.addProperty("delay", delay);
            }
            if (type.equals("clear"))
            {
                object.addProperty("type", type);
                object.addProperty("delay", delay);
            }
            if (type.equals("Flash"))
            {
                delay = params[2];
                object.addProperty("type", "flash");
                object.addProperty("delay", delay);
            }
            if (type.equals("Fade"))
            {
                delay = params[2];
                object.addProperty("type", "fade");
                object.addProperty("delay", delay);
            }
            if (type.equals("array"))
            {
                delay = params[2];
                object.addProperty("type", "array");
                object.addProperty("delay", delay);
                String input = gson.toJson(dArray);
                object.addProperty("array", input );
            }
            if (type.startsWith(":"))
            {
                object.addProperty("type", type);
                object.addProperty("delay", delay);
            }
        }

        return object;
    }
    
}


