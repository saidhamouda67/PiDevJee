package service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import entities.User;
import tn.esprit.jsf_app.presentation.mbeans.UserBean;

public class User_service {
	public String GlobalEndPoint = "https://consomitounsisaid.azurewebsites.net/";
//	public String GlobalEndPoint = "https://localhost:44364/";

	TrustManager[] noopTrustManager = new TrustManager[]{
            new X509TrustManager() {

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                }
            }
        };
	public User_service() {
		// TODO Auto-generated constructor stub
	}
	
	public User getUserAttributes(User ub) {
		
		 try {
	            SSLContext sc = SSLContext.getInstance("ssl");
	            sc.init(null, noopTrustManager, null);
	            
	            String finalUrl=GlobalEndPoint+"api/user-login/"+ub.getUsername();
	            System.out.println(finalUrl+" "+ub.getPassword());
			Client client = ClientBuilder.newBuilder().sslContext(sc).build();
			WebTarget web = client.target(finalUrl); 
	    		web=web.path("");
	    	Response response =web.request().post(Entity.entity(ub, MediaType.APPLICATION_JSON) );
	    	
	    	String result = response.readEntity(String.class); 
	    	JsonReader jsonReader = Json.createReader(new StringReader(result));
	    	JsonObject userObject=jsonReader.readObject();
	    	if (userObject.containsKey("Message")) {
	    		User us=new User();
	    		us.setMessage(userObject.getString("Message"));
	    		us.setAuthenticated(false);
	    		return us;
	    	}else {
	    		System.out.println(userObject);
	    		User us=new User();
	    		us.setUsername(userObject.getString("username"));
	    		us.setFirstName(userObject.getString("firstName"));
	    		us.setLastName(userObject.getString("lastName"));
	    		us.setUserId(userObject.getString("userId"));
	    		us.setImage(userObject.getString("image"));
	    		us.setEmail(userObject.getString("email"));
	    		us.setPhone(userObject.getString("phoneNumber"));
	    		us.setAdmin(userObject.getBoolean("isAdmin"));
	    		us.setAuthenticated(true);
	    		us.setMessage("success");
	    		return us;
	    		
	    	}
	    
		 }catch(Exception e) {
			 System.out.println(e);
		 }
		 return null;
	}
	
	public List<User> GetParticipantsByEvent(int eventId) {
		 try {
	            SSLContext sc = SSLContext.getInstance("ssl");
	            sc.init(null, noopTrustManager, null);
	            
	            
			Client client = ClientBuilder.newBuilder().sslContext(sc).build();
			WebTarget web = client.target(GlobalEndPoint+"api/event/"+eventId+"/participants");
			Response response = web.request().get();
	    	String result = response.readEntity(String.class); 
	    	JsonReader jsonReader = Json.createReader(new StringReader(result));
	    	JsonArray object =  jsonReader.readArray();
	    	List<User> users=new ArrayList<User>();
	    	for (int i = 0; i < object.size(); i++) {
		    	User u=new User();
		    	u.setFirstName(object.getJsonObject(i).getString("FirstName"));
		    	u.setLastName(object.getJsonObject(i).getString("LastName"));
		    	u.setImage(object.getJsonObject(i).getString("Image"));
		    	u.setUsername(object.getJsonObject(i).getString("UserName"));
			users.add(u);
	    	}
	    	return users;
		 }catch (Exception e) {
			// TODO: handle exception
			 
			 System.out.println(e);
		}
		 return null;
		
	}

}
