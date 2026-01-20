/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jakarta2.udbl.jakartamission2.beans;

import com.jakarta2.udbl.jakartamission2.business.UtilisateurEntrepriseBean;
import com.jakarta2.udbl.jakartamission2.business.SessionManager;
import com.jakarta2.udbl.jakartamission2.entities.Utilisateur;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 *
 * @author leona
 */
@Named(value = "welcomeBean")
@ViewScoped
public class WelcomBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String email;
    private String password;
    private String message;
    
    public WelcomBean (){
    
    }
    
    public WelcomBean(String email, String password) {
        this.email = email;
        this.password = password;
    }
    
        public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    @Inject
    private UtilisateurEntrepriseBean utilisateurEntrepriseBean;
    
    @Inject
    private SessionManager sessionManager;
    
    public String sAuthentifier(){
              Utilisateur utilisateur = utilisateurEntrepriseBean.authentifier(email,password);
              FacesContext context = FacesContext.getCurrentInstance();
              if(utilisateur != null){
                  // Créer la session avec l'email de l'utilisateur
                  sessionManager.createSession("user", utilisateur.getEmail());
                  return "home?faces-redirect=true";
              }
              else{
                  this.message = "Email ou mot de passe incorrecte.! ";
                  context.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,message,null));
                  return null;
              }
    }
}
