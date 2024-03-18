package org.example;

import com.google.protobuf.ServiceException;

public class UserService {
    private final UtilisateurApi utilisateurApi;
    public UserService(UtilisateurApi utilisateurApi) {
        this.utilisateurApi = utilisateurApi;
    }
    public void creerUtilisateur(Utilisateur utilisateur) throws
            ServiceException {
        utilisateurApi.creerUtilisateur(utilisateur);
    }
}

interface UtilisateurApi {
    void creerUtilisateur(Utilisateur utilisateur) throws ServiceException;
}