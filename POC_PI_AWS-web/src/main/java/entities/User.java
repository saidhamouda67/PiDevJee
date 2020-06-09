package entities;

public class User {
	private String username;
	private String password;
	private String FirstName;
	private String LastName;
	private String Image;
	private String userId;
	private String Message;
	private String phone;
	private String email;
	private boolean isAuthenticated;
	private boolean Admin;
	public User() {
		// TODO Auto-generated constructor stub
	}
	

	public User(String username, String password, String firstName, String lastName, String image) {
		super();
		this.username = username;
		this.password = password;
		FirstName = firstName;
		LastName = lastName;
		Image = image;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public boolean isAdmin() {
		return Admin;
	}


	public void setAdmin(boolean admin) {
		Admin = admin;
	}


	public String getMessage() {
		return Message;
	}


	public void setMessage(String message) {
		Message = message;
	}


	public boolean isAuthenticated() {
		return isAuthenticated;
	}


	public void setAuthenticated(boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getImage() {
		return Image;
	}

	public void setImage(String image) {
		Image = image;
	}
	

}
