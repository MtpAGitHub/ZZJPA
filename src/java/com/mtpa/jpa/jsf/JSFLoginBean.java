//050314    MtpA    Create class

package com.mtpa.jpa.jsf;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;


@Named("login")
@RequestScoped
public class JSFLoginBean {

    @Inject
    JSFErrorBean errorText;
    
    private String  username;
    private String  password;
    
    public JSFLoginBean() {
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

    public String loginUser() {
/*        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            //this method will actually check in the realm for the provided credentials
            request.login(this.username, this.password);
        } catch (ServletException e) {
            context.addMessage(null, new FacesMessage("Login failed."));
            return "loginerr";
        }
*/
        if (!this.username.equals("error")) {
            return "home";            
        } else {
            return "loginerr";
        }
    }
    
    public String logoutUser() {
/*        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            //this method will disassociate the principal from the session (effectively logging him/her out)
            request.logout();
            return "logout";
        } catch (ServletException e) {
            return "logouterr";
        }
*/
        return "logout";
    }
}
