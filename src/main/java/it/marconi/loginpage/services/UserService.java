package it.marconi.loginpage.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Service;

import it.marconi.loginpage.domains.RegistrationForm;

@Service
public class UserService {
    private ArrayList<RegistrationForm> users = new ArrayList<>();

    public ArrayList<RegistrationForm> getUsers() {
        return users;
    }

    public void addUser(RegistrationForm user) {
        users.add(user);
    }

    public Optional<RegistrationForm> getByUsername(String username) {
        for(RegistrationForm u : users)
            if(u.getUsername().equals(username))
                return Optional.of(u);

        return Optional.empty();
    }
}
