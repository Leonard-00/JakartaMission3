/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jakarta2.udbl.jakartamission2.business;

import com.jakarta2.udbl.jakartamission2.entities.Utilisateur;
import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author joelm
 */
@Stateless
@LocalBean
public class UtilisateurEntrepriseBean {
    @PersistenceContext
    private EntityManager em;
    private String password;
    @Transactional
    public void ajouterUtilisateurEntreprise(String username, String email, String password, String description) throws Exception {
        // Vérifier si l'utilisateur existe déjà par nom d'utilisateur ou email
        Utilisateur existingUserByUsername = trouverUtilisateurParUsername(username);
        Utilisateur existingUserByEmail = trouverUtilisateurParEmail(email);

        if (existingUserByUsername != null || existingUserByEmail != null) {
            throw new Exception("Ce nom d'utilisateur et cette adresse existent déjà.");
        }
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        Utilisateur utilisateur = new Utilisateur(username, email, hashedPassword, description);
        em.persist(utilisateur);
    }
    public List<Utilisateur> listerTousLesUtilisateurs() {
        return em.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class).getResultList();
    }
    @Transactional
    public void supprimerUtilisateur(Long id) {
        Utilisateur utilisateur = em.find(Utilisateur.class, id);
        if (utilisateur != null) {
            em.remove(utilisateur);
        }
    }
    public Utilisateur trouverUtilisateurParId(Long id) {
        return em.find(Utilisateur.class, id);
    }
    public Utilisateur trouverUtilisateurParEmail(String email) {
        try {
            return em.createQuery("SELECT u FROM Utilisateur u WHERE u.email = :email", Utilisateur.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    public Utilisateur trouverUtilisateurParUsername(String username) {
        try {
            return em.createQuery("SELECT u FROM Utilisateur u WHERE u.username = :username", Utilisateur.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    public Utilisateur authentifier(String email, String password){
        Utilisateur utilisateur = this.trouverUtilisateurParEmail(email);
        if (utilisateur != null && this.verifierMotDePasse(password, utilisateur.getPassword())){
            return utilisateur;
            }
        return null;
        
    }
    
    public boolean verifierMotDePasse(String password, String hashedPassword) { 
        return BCrypt.checkpw(password, hashedPassword); 
    }
    
    @Transactional
    public void modifierUtilisateur(Utilisateur utilisateur) throws Exception {
        if (utilisateur != null && utilisateur.getId() != null) {
            em.merge(utilisateur);
        } else {
            throw new Exception("Utilisateur invalide");
        }
    }
    
    @Transactional
    public void modifierMotDePasse(Long userId, String nouveauMotDePasse) throws Exception {
        Utilisateur utilisateur = em.find(Utilisateur.class, userId);
        if (utilisateur != null) {
            String hashedPassword = BCrypt.hashpw(nouveauMotDePasse, BCrypt.gensalt());
            utilisateur.setPassword(hashedPassword);
            em.merge(utilisateur);
        } else {
            throw new Exception("Utilisateur non trouvé");
        }
    }

//    private boolean verifierMotDePasse(String password, String password0) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
}