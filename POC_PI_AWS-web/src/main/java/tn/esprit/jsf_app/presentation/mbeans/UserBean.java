package tn.esprit.jsf_app.presentation.mbeans;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import entities.User;
import service.User_service;
import utils.SessionUtils;
@ManagedBean(name = "userBean", eager = true)
@SessionScoped
public class UserBean implements Serializable{
	private String UserName;
	private String FirstName;
	private String Image;
	private String LastName;
	private String UserId;
	private String Password;
	public static boolean isAuthenticated=false;
	private static final long serialVersionUID = 1094801825228386363L;
	private User_service us=new User_service();
	public UserBean() {
		// TODO Auto-generated constructor stub
		
	}

	public UserBean(String userName, String firstName, String image, String lastName, String userId) {
		super();
		UserName = userName;
		FirstName = firstName;
		Image = image;
		LastName = lastName;
		UserId = userId;
	}

	public String Login() {
		User us=new User();
		us.setUsername(UserName);
		us.setPassword(Password);
		User coming=this.us.getUserAttributes(us);
		boolean valid=coming.isAuthenticated();
		if (valid) {
			HttpSession session = SessionUtils.getSession();
			session.setAttribute("userName", coming.getUsername());
			session.setAttribute("userId", coming.getUserId());
			session.setAttribute("firstName", coming.getFirstName());
			session.setAttribute("lastName", coming.getLastName());
			session.setAttribute("image", coming.getImage());
			session.setAttribute("email", coming.getEmail());
			session.setAttribute("phoneNumber", coming.getPhone());
			session.setAttribute("Administrator", coming.isAdmin());
			return "ViewEvents.jsf";
		} else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							coming.getMessage(),
							"Please enter correct username and Password"));
			return "LoginPage.jsf";
		}
		
	}
	
	
	public void logout() {
		HttpSession session = SessionUtils.getSession();
		session.invalidate();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("LoginPage.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void redirectIfLoggedIn() {
		if(SessionUtils.getUserId()!=null) {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("/POC_PI_AWS-web/Event/ViewEvents.jsf");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getImage() {
		return Image;
	}

	public void setImage(String image) {
		Image = image;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}
	
	

}
