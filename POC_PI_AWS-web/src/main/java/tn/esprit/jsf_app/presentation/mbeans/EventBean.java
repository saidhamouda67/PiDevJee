package tn.esprit.jsf_app.presentation.mbeans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import com.azure.storage.blob.*;
import com.azure.storage.blob.models.*;

import entities.User;
import service.User_service;

import java.io.*;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.primefaces.component.rating.Rating;
import org.primefaces.model.UploadedFile;

import tn.esprit.jsf_app.DTO.*;
import tn.esprit.jsf_app.services.EventService;
import utils.SessionUtils;

@ManagedBean(name = "eventBean", eager = true)
@SessionScoped
@RequestScoped
public class EventBean {
	private UploadedFile file;
	
	private String EventId;
	private String Name; 
	private String Picture;
	private String StartDate;
	private String EndDate;
	private String Description;
	private String Category;
	private Event Event;
	private String RatingAdded;
	private float CurrentRating;
	
	private int RatingQuantity;
	private List<User> listParticipants;
	private static final long serialVersionUID = 1L;
    private static final String connectionstring = "DefaultEndpointsProtocol=https;AccountName=medsaidstorage;AccountKey=7e2vM0brM6E30r4FzZNoE+oal6Hh9z5cJAQjysw2sEhdlfBJl2kfSdiPTCdWeeNNgW7icl5Nujl6qaVfGBGyTA==;EndpointSuffix=core.windows.net";
    
	EventService E;
	User_service Us;
	
	public EventBean() {
		E = new EventService();
		Us=new User_service();
	}
	
	public Event getEvent() {
		return Event;
	}


	public int getRatingQuantity() {
		return RatingQuantity;
	}

	public void setRatingQuantity(int ratingQuantity) {
		RatingQuantity = ratingQuantity;
	}

	public String getCategory() {
		return Category;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public void setCategory(String category) {
		Category = category;
	}

	public String getRatingAdded() {
		return RatingAdded;
	}
	
	public List<User> getListParticipants() {
		return listParticipants;
	}

	public void setListParticipants(List<User> listParticipants) {
		this.listParticipants = listParticipants;
	}

	public void redirectIfNotAdmin() {
		if(!SessionUtils.isAdmin()) {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("/POC_PI_AWS-web/Event/ViewEvents.jsf");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public void setRatingAdded(String ratingAdded) {
		RatingAdded = ratingAdded;
	}

	public float getCurrentRating() {
		return CurrentRating;
	}

	public void setCurrentRating(float currentRating) {
		CurrentRating = currentRating;
	}

	public void setEvent(Event event) {
		Event = event;
	}


	public List<Event> getEvents() {
		List<Event> lesEvents=E.GetAll();
		for (Event event : lesEvents) {
			event.setCurrentUserId(SessionUtils.getUserId()!=null ? SessionUtils.getUserId() : "");
		}
		return lesEvents;
		
	}
	
	
	

	public String getDescription() {
		return Description;
	}


	public void setDescription(String description) {
		Description = description;
	}


	public String getMessage() {
		return "welcome to the club "+this.Name;
	}
	public String getWelcomeMessage() {
	      return "Hello " + Name;
	   }
	
	public List<Event> getAll() {
		List<Event> lesEvents=E.GetAll();
		if(SessionUtils.getUserId()!=null) {
			String lid=SessionUtils.getUserId();
		for (Event event : lesEvents) {
			
			if(event.getParticipants().contains(lid)){
				event.setCanParticipate(false);
			}else event.setCanParticipate(true);
			
			if(event.getRatingUsers().contains(lid) || !event.getParticipants().contains(lid) ) {
				event.setCanRate(false);
			}else {
				event.setCanRate(true);
			}
		}
		}else {
			for (Event event : lesEvents) {
				
				event.setCanParticipate(false);
				event.setCanRate(false);
			}
		}
		
		return lesEvents;
	}
	
	public String participer(String eventId) {
		E.Participer(SessionUtils.getUserId(), Integer.parseInt(eventId));
		return "ViewEvents.jsf";
	
	}
	
	public String addRating() {
       System.out.println("rating added is "+RatingAdded+" the eventId is "+EventId);
       float newRatingAdded=Float.valueOf(RatingAdded).floatValue();
       int levent=Integer.parseInt(EventId);
       String userId=SessionUtils.getUserId();
       E.AddRating(levent, userId, newRatingAdded);
		return "ViewEvents.jsf";
	}
	public String addEvent()  {
		
		if(file!=null) {
		setPicture(file.getFileName());
		System.out.println(getPicture());
		String imgUrl=doUpload();
		Event e=new Event(0,Name,imgUrl,Description,StartDate,
				EndDate,Category,"");
				E.Create(e);
		}else System.out.println("tahcheee");
		
		
		return "ViewEvents.jsf";
	}
	
	public String modifier() {
	
		String liid=(SessionUtils.getSession().getAttribute("eventIdFocused")+"").trim();
		Event e=new Event();
		e.setName(Name);
		e.setCategory(Category);
		e.setDescription(Description);
	
		E.Update(Integer.parseInt(liid), e);
		
		return getEvent(liid);
		
		
		
	}
	public void goToCreateEvent() {
		if(SessionUtils.getUserId()==null)
		try {
			
			FacesContext.getCurrentInstance().getExternalContext().redirect("/POC_PI_AWS-web/Event/ViewEvents.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		else {
			if(!SessionUtils.isAdmin())
				try {
					
					FacesContext.getCurrentInstance().getExternalContext().redirect("/POC_PI_AWS-web/Event/ViewEvents.jsf");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	public String loadDataToInputs() {
		String liid=SessionUtils.getSession().getAttribute("eventIdFocused")+"";
		Event e=E.GetEvent(Integer.parseInt(liid));
		setCategory(e.getCategory());
		setDescription(e.getDescription());
		setName(e.getName());
		setEventId(e.getEventId()+"");
		return "EditEvent.jsf";
	}
	public String getEvent(String eventId) {
		
		Event e=E.GetEvent(Integer.parseInt(eventId));
		SessionUtils.getSession().setAttribute("eventIdFocused", eventId);
		listParticipants = Us.GetParticipantsByEvent(Integer.parseInt(eventId));
		setCategory(e.getCategory());
		setCurrentRating(e.getRating());
		setStartDate(e.getHeurD());
		setEndDate(e.getHeurF());
		setPicture(e.getImageEvent());
		setName(e.getName());
		setRatingQuantity(e.getRatingQuantity());
		setEventId(eventId);
		setDescription(e.getDescription());
		return "EventIndex.jsf";
	}
	
public String deleteEvent(String eventId) {
	E.Delete(Integer.parseInt(eventId));
	return "ViewEvents.jsf";
}

public String doUpload() {
	BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(connectionstring).buildClient();
	BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient("photos");
	String imageName=new Date().getTime()+"";
	String allPath="C:\\Users\\medsaid\\Desktop\\images\\"+Picture;
	System.out.println(allPath+" ... "+Picture);
	BlobClient blobClient = containerClient.getBlobClient(imageName+Picture);
	blobClient.uploadFromFile(allPath);
return blobClient.getBlobUrl();
}

	public String getEventId() {
		return EventId;
	}


	public void setEventId(String eventId) {
		EventId = eventId;
	}


	public String getName() {
		return Name;
	}


	public void setName(String name) {
		Name = name;
	}


	public String getPicture() {
		return Picture;
	}


	public void setPicture(String picture) {
		Picture = picture;
	}




	public String getStartDate() {
		return StartDate;
	}


	public void setStartDate(String startDate) {
		StartDate = startDate;
	}


	public String getEndDate() {
		return EndDate;
	}


	public void setEndDate(String endDate) {
		EndDate = endDate;
	}


	
	
	
}