package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.ProductRepositories;
import com.finalprojectcoffee.repositories.UserRepositories;
import com.finalprojectcoffee.utils.EmailUtil;
import com.finalprojectcoffee.utils.JBCriptUtil;
import com.finalprojectcoffee.utils.PhoneNumberUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

 public class SearchKeyword implements Command{
        private final HttpServletRequest request;
        private final HttpServletResponse response;
        private final EntityManagerFactory factory;

        public SearchKeyword(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
            this.request = request;
            this.response = response;
            this.factory = factory;
        }

        @Override
        public String execute() {
            String terminus = "customer-home.jsp";
            HttpSession session = request.getSession(true);
            String keyword = request.getParameter("keyword");


            if(keyword != null && !keyword.isEmpty() ){
                EntityManager entityManager = factory.createEntityManager();

                try {
                    ProductRepositories productRepos = new ProductRepositories(factory);
                    productRepos.findProductsByKeyword(keyword);


                } finally {
                    entityManager.close();
                }
            }
            return terminus;
        }
    }

