package com.finalprojectcoffee.commands;

import jakarta.servlet.ServletException;

import java.io.IOException;

public interface Command {
    String execute() throws ServletException, IOException;
}
