package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Product;
import com.finalprojectcoffee.repositories.ProductRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;

public class ViewLandingCalories implements Command {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;
    private static final String API_KEY = "HLCULM+DbcZ20PldkQBCFQ==F26SkWjkoW9d6LRl";

    public ViewLandingCalories(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "landing-menu.jsp";

        HttpSession session = request.getSession(true);
        int productId = Integer.parseInt(request.getParameter("productId"));
        try {
            ProductRepositories productRep = new ProductRepositories(factory);
            Product p = productRep.findProductByID(productId);

            if (p != null) {
                String name = p.getName();
                String caloriesInfo = fetchNutritionData(name);
                session.setAttribute("caloriesInfo", caloriesInfo);
                terminus = "calories-view.jsp";
            } else {
                session.setAttribute("vpe-message", "Product is empty");
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred while viewing product list: " + e.getMessage());
        }
        return terminus;
    }

    private String fetchNutritionData(String query) throws IOException {
        String url = "https://api.calorieninjas.com/v1/nutrition?query=" + URLEncoder.encode(query, "UTF-8");

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            request.setHeader("X-Api-Key", API_KEY);


            String response = EntityUtils.toString(httpClient.execute(request).getEntity());

            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject.has("items") && jsonObject.getJSONArray("items").length() > 0) {
                JSONObject nutritionInfo = jsonObject.getJSONArray("items").getJSONObject(0);

                if (nutritionInfo.has("calories")) {
                    return String.valueOf(nutritionInfo.getDouble("calories"));
                } else {
                    return "Calories data not available";
                }
            }
        }
        return "No Data available";
    }

}