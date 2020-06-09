package tn.esprit.jsf_app.presentation.mbeans;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import utils.SessionUtils;


@ManagedBean
@SessionScoped


	
public class Login implements Serializable {

		private static final long serialVersionUID = 1094801825228386363L;
		
		private String password;
		private String username;
		

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		//validate login
		public String validateUsernamePassword() {
		/*	if (valid) {
				HttpSession session = SessionUtils.getSession();
				session.setAttribute("username", user);
				return "admin";
			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Incorrect Username and Passowrd",
								"Please enter correct username and Password"));
				return "login";
			}*/
			return "";
		}

		//logout event, invalidate session
		public String logout() {
			HttpSession session = SessionUtils.getSession();
			session.invalidate();
			return "login";
		}
	}


