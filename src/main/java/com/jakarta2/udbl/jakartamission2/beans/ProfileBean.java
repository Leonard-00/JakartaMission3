/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jakarta2.udbl.jakartamission2.beans;

import com.jakarta2.udbl.jakartamission2.business.SessionManager;
import com.jakarta2.udbl.jakartamission2.business.UtilisateurEntrepriseBean;
import com.jakarta2.udbl.jakartamission2.entities.Utilisateur;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.IOException;
import java.io.Serializable;

/**
 *
 * @author leona
 */
@Named(value = "profileBean")
@SessionScoped
public class ProfileBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Utilisateur utilisateur;
    private String username;
    private String email;
    private String description;
    private String ancienMotDePasse;
    private String nouveauMotDePasse;
    private String confirmerMotDePasse;
    
    @Inject
    private SessionManager sessionManager;
    
    @Inject
    private UtilisateurEntrepriseBean utilisateurEntrepriseBean;
    
    @PostConstruct
    public void init() {
        // Récupérer l'email de la session
        String userEmail = sessionManager.getValueFromSession("user");
        if (userEmail != null) {
            utilisateur = utilisateurEntrepriseBean.trouverUtilisateurParEmail(userEmail);
            if (utilisateur != null) {
                this.username = utilisateur.getUsername();
                this.email = utilisateur.getEmail();
                this.description = utilisateur.getDescription();
            }
        }
    }
    
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getAncienMotDePasse() {
        return ancienMotDePasse;
    }
    
    public void setAncienMotDePasse(String ancienMotDePasse) {
        this.ancienMotDePasse = ancienMotDePasse;
    }
    
    public String getNouveauMotDePasse() {
        return nouveauMotDePasse;
    }
    
    public void setNouveauMotDePasse(String nouveauMotDePasse) {
        this.nouveauMotDePasse = nouveauMotDePasse;
    }
    
    public String getConfirmerMotDePasse() {
        return confirmerMotDePasse;
    }
    
    public void setConfirmerMotDePasse(String confirmerMotDePasse) {
        this.confirmerMotDePasse = confirmerMotDePasse;
    }
    
    public String modifierProfil() {
        try {
            if (utilisateur != null) {
                utilisateur.setDescription(description);
                utilisateurEntrepriseBean.modifierUtilisateur(utilisateur);
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Profil modifié avec succès", null));
                return null;
            }
        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur : " + e.getMessage(), null));
        }
        return null;
    }
    
    public String modifierMotDePasse() {
        try {
            if (utilisateur != null) {
                // Vérifier l'ancien mot de passe
                if (!utilisateurEntrepriseBean.verifierMotDePasse(ancienMotDePasse, utilisateur.getPassword())) {
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ancien mot de passe incorrect", null));
                    return null;
                }
                
                // Vérifier que les nouveaux mots de passe correspondent
                if (!nouveauMotDePasse.equals(confirmerMotDePasse)) {
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Les nouveaux mots de passe ne correspondent pas", null));
                    return null;
                }
                
                // Vérifier que le nouveau mot de passe n'est pas vide
                if (nouveauMotDePasse == null || nouveauMotDePasse.trim().isEmpty()) {
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le nouveau mot de passe ne peut pas être vide", null));
                    return null;
                }
                
                // Modifier le mot de passe
                utilisateurEntrepriseBean.modifierMotDePasse(utilisateur.getId(), nouveauMotDePasse);
                
                // Réinitialiser les champs
                ancienMotDePasse = "";
                nouveauMotDePasse = "";
                confirmerMotDePasse = "";
                
                // Recharger l'utilisateur
                utilisateur = utilisateurEntrepriseBean.trouverUtilisateurParEmail(email);
                
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mot de passe modifié avec succès", null));
                return null;
            }
        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur : " + e.getMessage(), null));
        }
        return null;
    }
    
    public void deconnecter() {
        sessionManager.invalidateSession();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("../index.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
