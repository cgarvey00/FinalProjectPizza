package com.finalprojectcoffee.commands;

import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CommandFactory {
    public static Command getCommand(String action, HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        Command command = null;

        if (action != null) {
            switch (action) {
                case "login":
                    command = new Login(request, response, factory);
                    break;
                case "register":
                    command = new Register(request, response, factory);
                    break;
                case "SearchKeyword":
                    command = new SearchKeyword(request, response, factory);
                    break;
                case "view-register":
                    command = new ViewRegister(request, response, factory);
                    break;
                case "view-login":
                    command = new ViewLogin(request, response, factory);
                    break;
                case "view-menu":
                    command = new ViewMenu(request, response, factory);
                    break;
                case "update-user-profile":
                    command = new UpdateUserProfile(request, response, factory);
                    break;
                default:
                    break;
            }
        }
        return command;
    }
}
