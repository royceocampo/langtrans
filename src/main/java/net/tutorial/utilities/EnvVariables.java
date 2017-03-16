package net.tutorial.utilities;

import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.google.gson.JsonParser;


public class EnvVariables {
	
	boolean hasVcap = false;
	JSONObject vcap;
	
	public EnvVariables() {
		String VCAP_SERVICES = System.getenv("VCAP_SERVICES");
		
		if (VCAP_SERVICES != null) {
			this.hasVcap = true;
			JsonParser parser = new JsonParser();
			Object obj;
			obj = parser.parse(VCAP_SERVICES);
			vcap =  (JSONObject) obj;
		}
	}
	
	public Map<String, String> getCredentials(String serviceName) {
		
		Map<String, String> creds = new HashMap<String, String>();
		
		if (this.hasVcap) {
			JSONArray serviceConfig = (JSONArray) vcap.get(serviceName);
	        JSONObject serviceInstance = (JSONObject) serviceConfig.get(0);
	        JSONObject serviceCreds = (JSONObject) serviceInstance.get("credentials");
			
			creds.put("username", serviceCreds.get("username").toString());
			creds.put("password",  serviceCreds.get("password").toString());
		} else {
			creds.put("username", ""); // Put username here if you are testing in local
			creds.put("password",  ""); // Put password here if you are testing in local
		}
		
		return creds;
	}

}
