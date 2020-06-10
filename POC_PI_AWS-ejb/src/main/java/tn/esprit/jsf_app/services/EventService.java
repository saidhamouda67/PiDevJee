 package tn.esprit.jsf_app.services;

import java.io.StringReader;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.persistence.EntityManager;
import javax.security.cert.X509Certificate;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tn.esprit.jsf_app.DTO.Event;
import tn.esprit.jsf_app.interfaces.EventServiceRemote;

@Stateless
@LocalBean
public class EventService implements EventServiceRemote{

	public String GlobalEndPoint = "https://consomitounsisaid.azurewebsites.net";
	//public String GlobalEndPoint = "https://localhost:44364/";

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
	public EventService() {
		// TODO Auto-generated constructor stub
	}

	/*	@Override

	 * public List<Event> GetAll() {
		EntityManager em ;
			List<Event>  lasp = new ArrayList<Event>();
	    	Client client = ClientBuilder.newClient();
	    	
	    	WebTarget web = client.target("http://"+GlobalEndPoint+"/api/EventWebApi/"); 
	    	
	    	Response response = web.request().get();
	    	
	    	String result = response.readEntity(String.class); 
	    	
	    	//System.out.println(result);
	    	JsonReader jsonReader = Json.createReader(new StringReader(result));
	    	JsonArray object =  jsonReader.readArray();
	    	
	    	 
	    	for (int i=0;i<object.size();i++)
	    	{
	    	 
	    		Event m = new Event();
	    	 //String dateString;
	       	 m.setEventId(object.getJsonObject(i).getInt("EventId")); 
	    	 m.setName(object.getJsonObject(i).getString("Name")); 
	    	 m.setPicture(object.getJsonObject(i).getString("Picture")); 
	    	
	    	 m.setStartDate(object.getJsonObject(i).getString("StartDate"));
	    	 m.setEndDate(object.getJsonObject(i).getString("EndDate"));
	    	
	    	 lasp.add(m);
	    	}
	    	

	return lasp;    	
	}

	@Override
	public void Delete(Event eve) {
		Client cl = ClientBuilder.newClient();
		WebTarget target = cl.target("http://"+GlobalEndPoint+"/api/EventWebApi?id="+eve.getEventId()); 
		WebTarget hello = target.path("");     	
    	Response res=(Response) hello.request().delete();
	}*/

	@Override
	public void Create(Event p) {
	
            try {
            SSLContext sc = SSLContext.getInstance("ssl");
            sc.init(null, noopTrustManager, null);
            
            
		Client client = ClientBuilder.newBuilder().sslContext(sc).build();
		WebTarget target = client.target(GlobalEndPoint+"/api/EventPost");
		WebTarget hello =target.path("");
		
		Response response =hello.request().post(Entity.entity(p, MediaType.APPLICATION_JSON) );
		
		
		String result=response.readEntity(String.class);
		System.out.println(result);
		
		

		response.close();
            }catch(Exception e) {
            	System.out.println(e);
            }
	}

	@Override
	public List<Event> GetAll() {
		List<Event>  lasp = new ArrayList<Event>();

		// TODO Auto-generated method stub
		 try {
	            SSLContext sc = SSLContext.getInstance("ssl");
	            sc.init(null, noopTrustManager, null);
	            
	            
			Client client = ClientBuilder.newBuilder().sslContext(sc).build();
WebTarget web = client.target(GlobalEndPoint+"/api/event"); 
	    	
	    	Response response = web.request().get();
	    	
	    	String result = response.readEntity(String.class); 
	    	
	    	//System.out.println(result);
	    	JsonReader jsonReader = Json.createReader(new StringReader(result));
	    	JsonArray object =  jsonReader.readArray();
	    	System.out.println("size is "+object.size());
	    	 System.out.println(object);
	    	for (int i=0;i<object.size();i++)
	    	{
	    	 
	    		Event m = new Event();
	    	 //String dateString;
	       	 m.setEventId(object.getJsonObject(i).getInt("EventId")); 
	    	 m.setName(object.getJsonObject(i).getString("Name")); 
	    	 m.setImageEvent(object.getJsonObject(i).getString("ImageEvent")); 
	    	 m.setParticipants(object.getJsonObject(i).getString("Participants"));
	    	 m.setDescription(object.getJsonObject(i).getString("Description"));
	    	 m.setHeurD(object.getJsonObject(i).getString("HeurD"));
	    	 m.setHeurF(object.getJsonObject(i).getString("HeurF"));
	    	 m.setCategory(object.getJsonObject(i).getString("Category"));
	    	 m.setRatingQuantity(object.getJsonObject(i).getInt("RatingQuantity"));
	    	 m.setRatingUsers(object.getJsonObject(i).getString("ratingUsers"));
	    	 int number=(object.getJsonObject(i).getString("Participants").length()==0)?0:object.getJsonObject(i).getString("Participants").split(",").length;
	    	 
	    	 m.setNumOfParticipants(number);
	    	 m.setRating(BigDecimal.valueOf(object.getJsonObject(i).getJsonNumber("Rating").doubleValue()).floatValue());
	    	 lasp.add(m);
	    	}
	    	
		 }catch (Exception e) {
			// TODO: handle exception
			 System.out.println(e);
		}
	
		return lasp;
	}

	@Override
	public void Delete(int eventId) {
		// TODO Auto-generated method stub
		try {
            SSLContext sc = SSLContext.getInstance("ssl");
            sc.init(null, noopTrustManager, null);
            
            
		Client client = ClientBuilder.newBuilder().sslContext(sc).build();
		String finalUrl=GlobalEndPoint+"/api/event/"+eventId;
		WebTarget target = client.target(finalUrl);
		Response response = target
		               .request().delete();
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}



	@Override
	public void Participer(String UserId, int EventId) {
	
		try {
            SSLContext sc = SSLContext.getInstance("ssl");
            sc.init(null, noopTrustManager, null);
            
            
		Client client = ClientBuilder.newBuilder().sslContext(sc).build();
		String finalUrl=GlobalEndPoint+"/api/event/"+EventId+"/participer/"+UserId;
		System.out.println(finalUrl);
		WebTarget target = client.target(finalUrl);
	Response response = target
	               .request()
	              .post(null);
		//System.out.println(response);
		}catch (Exception e2) {
			// TODO: handle exception
			System.out.println(e2.getMessage());
		}
		
	}

	@Override
	public Event GetEvent(int eventId) {
		// TODO Auto-generated method stub
		 try {
	            SSLContext sc = SSLContext.getInstance("ssl");
	            sc.init(null, noopTrustManager, null);
	            
	            
			Client client = ClientBuilder.newBuilder().sslContext(sc).build();
			WebTarget web = client.target(GlobalEndPoint+"/api/event/"+eventId);
			Response response = web.request().get();
	    	String result = response.readEntity(String.class); 
	    	JsonReader jsonReader = Json.createReader(new StringReader(result));
	    	JsonObject EventObject=jsonReader.readObject();
	    		
	    	Event e=new Event(
	    			EventObject.getInt("Id"),
	    			EventObject.getString("Name"),
	    			EventObject.getString("ImageUrl"),
	    			EventObject.getString("Description"),
	    			EventObject.getString("heurD"),
	    			EventObject.getString("heurF"),
	    			EventObject.getString("Category"),
	    			EventObject.getString("Participants")
	    			);
	    	e.setRating(BigDecimal.valueOf(EventObject.getJsonNumber("Rating").doubleValue()).floatValue());
	    	e.setRatingQuantity(EventObject.getInt("RatingQuantity"));
	    	e.setRatingUsers(EventObject.getString("ratingUsers"));
	    	return e;
	    	
		 }catch (Exception e) {
			// TODO: handle exception
			 e.getStackTrace();
		 }
		return null;
	}

	@Override
	public void Update(int id, Event p) {
		Event e = new Event();
		e.setName(p.getName());
		e.setCategory(p.getCategory());
		e.setDescription(p.getDescription());
		
		 try {
	            SSLContext sc = SSLContext.getInstance("ssl");
	            sc.init(null, noopTrustManager, null);
	            
	            
			Client client = ClientBuilder.newBuilder().sslContext(sc).build();
  	

		WebTarget target = client.target(GlobalEndPoint+"/api/event/"+id);
		Response response = target
		                 .request()
		                 .put(Entity.entity(e, MediaType.APPLICATION_JSON));
	
		
	
		 }catch(Exception ex) {
			 System.out.println(ex);
		 }
}

	@Override
	public void AddRating(int eventId, String userId, float Rating) {
		Event e=new Event();
		e.setRating(Rating);

		
		 try {
	            SSLContext sc = SSLContext.getInstance("ssl");
	            sc.init(null, noopTrustManager, null);
	            
	            
			Client client = ClientBuilder.newBuilder().sslContext(sc).build();
			String finalUrl=GlobalEndPoint+"/api/event/"+eventId+"/add-rating/"+userId;
			System.out.println(finalUrl);
		WebTarget target = client.target(finalUrl);
		Response response = target
		                 .request()
		                 .put(Entity.entity(e, MediaType.APPLICATION_JSON));
		System.out.println("the message is ");
		System.out.println(response);
		
	
		 }catch(Exception ex) {
			 System.out.println(ex);
 		 }
		
	}
}