package com.finalprojectcoffee.commands;

import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CommandFactory {
    public static Command getCommand(String action, HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory){
        Command command = null;

        if(action != null){
            switch (action){
                case "login":
                    command = new Login(request,response,factory);
                    break;
                case "Register":
                    command = new Register(request,response,factory);
                    break;
                case "SearchKeyword":
                    command = new SearchKeyword(request,response,factory);
                    break;
                default:
                    break;
            }
        }
        return command;
    }
}
