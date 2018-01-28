import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.google.gson.JsonObject;

public class JSONSend {
	  public void sendData(JsonObject data, String ipAddress) throws UnknownHostException, IOException {
	        //server contact and send
	        String serverName = ipAddress;
	        int port = Integer.parseInt("6066");
	        Socket client = new Socket(serverName, port);
	        OutputStream outToServer = null;
	        DataOutputStream out = null;
	        BufferedReader in = null;
	        boolean retry = true;
	        while (retry == true) {
	            outToServer = client.getOutputStream();
	            out = new DataOutputStream(outToServer);
	            out.writeUTF(data.toString());
	            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
	            String line = null;
	            String response = "";
	            while ((line = in.readLine()) != null) {
	                response = response + line;
	            }
	            if (response.equals("received"))
	            {
	                retry = false;
	            }
	        }
	        in.close();
	        outToServer.close();
	        out.close();
	    }
}
