# TODO List

- [x] Fix 404 error on "À propos" button click
  - Issue: HTTP Status 404 - /Pages/index.xhtml Not Found in ExternalContext as a Resource
  - Root cause: ProfileBean.deconnecter() method was returning "redirect:/index.xhtml" which is invalid in JSF
  - Fix: Changed deconnecter() to use FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml") instead of returning a string
  - Status: Completed

- [x] Add logout and profile features to a_propos.xhtml
  - Created src/main/webapp/a_propos.xhtml with navbar containing logout button and profile display/modification sections
  - Includes user profile view, description editing, and password change functionality
  - Status: Completed
