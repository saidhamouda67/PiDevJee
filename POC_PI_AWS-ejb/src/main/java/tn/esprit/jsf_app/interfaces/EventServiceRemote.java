package tn.esprit.jsf_app.interfaces;
import java.util.List;

import javax.ejb.Remote;
import javax.xml.registry.infomodel.User;

import tn.esprit.jsf_app.DTO.Event;

@Remote
public interface EventServiceRemote {
	List<Event> GetAll();
	public void Delete(int eventId);
	public void Create(Event p);
	public void Update(int id,Event p);
	public void Participer(String UserId,int EventId);
	public Event GetEvent(int eventId);
	public void AddRating(int eventId,String userId,float Rating);
}
