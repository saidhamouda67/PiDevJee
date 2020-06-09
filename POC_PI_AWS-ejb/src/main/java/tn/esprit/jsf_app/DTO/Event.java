package tn.esprit.jsf_app.DTO;

public class Event {
	
	public int EventId;
	public String Name; 
	public String ImageEvent;
	public String Description;
	public String HeurD;
	public String HeurF;
	public String Category;
	public String Participants;
	public int NumOfParticipants;
	public int RatingQuantity;
	public float Rating;
	public String CurrentUserId;
	public boolean CanParticipate;
	public boolean CanRate;
	public String RatingUsers;
	
	private static final long serialVersionUID = 1L;
	
	
	public Event() {
		
	}
	public Event(int eventId, String name, String imageEvent, String description, String heurD, String heurF,
			String category, String participants) {
		super();
		EventId = eventId;
		Name = name;
		ImageEvent = imageEvent;
		Description = description;
		HeurD = heurD;
		HeurF = heurF;
		Category = category;
		Participants = participants;
	}

	public boolean isCanRate() {
		return CanRate;
	}
	public void setCanRate(boolean canRate) {
		CanRate = canRate;
	}
	public String getRatingUsers() {
		return RatingUsers;
	}
	public void setRatingUsers(String ratingUsers) {
		RatingUsers = ratingUsers;
	}
	public boolean isCanParticipate() {
		return CanParticipate;
	}
	public void setCanParticipate(boolean canParticipate) {
		CanParticipate = canParticipate;
	}
	public int getRatingQuantity() {
		return RatingQuantity;
	}

	public int getNumOfParticipants() {
		return NumOfParticipants;
	}
	public String getCurrentUserId() {
		return CurrentUserId;
	}
	public void setCurrentUserId(String currentUserId) {
		CurrentUserId = currentUserId;
	}
	public void setNumOfParticipants(int numOfParticipants) {
		NumOfParticipants = numOfParticipants;
	}
	public void setRatingQuantity(int ratingQuantity) {
		RatingQuantity = ratingQuantity;
	}

	public float getRating() {
		return Rating;
	}

	public void setRating(float rating) {
		Rating = rating;
	}

	public String getImageEvent() {
		return ImageEvent;
	}

	public void setImageEvent(String imageEvent) {
		ImageEvent = imageEvent;
	}

	public int getEventId() {
		return EventId;
	}
	public void setEventId(int eventId) {
		EventId = eventId;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getHeurD() {
		return HeurD;
	}
	public void setHeurD(String heurD) {
		HeurD = heurD;
	}
	public String getHeurF() {
		return HeurF;
	}
	public void setHeurF(String heurF) {
		HeurF = heurF;
	}
	public String getCategory() {
		return Category;
	}
	public void setCategory(String category) {
		Category = category;
	}
	public String getParticipants() {
		return Participants;
	}
	public void setParticipants(String participants) {
		Participants = participants;
	}

	@Override
	public String toString() {
		return "Event [EventId=" + EventId + ", Name=" + Name + ", ImageEvent=" + ImageEvent + ", Description="
				+ Description + ", HeurD=" + HeurD + ", HeurF=" + HeurF + ", Category=" + Category
				+ ", Participants=" + Participants + "]";
	}
	
	
}
