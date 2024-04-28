package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Address;
import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.UserRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class AddAddress implements Command {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;
    private static final String API_KEY = "288b690a4f3240e8a843b87a36449bde";

    public AddAddress(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "customer-home.jsp";

        HttpSession session = request.getSession(true);
        User activeUser = (User) session.getAttribute("loggedInUser");
        int activeUserId = activeUser.getId();
        String street = request.getParameter("street");
        String town = request.getParameter("town");
        String county = request.getParameter("county");
        String eirCode = request.getParameter("eirCode");
        Address address = new Address();

        if (street != null && !street.isEmpty()) {
            address.setStreet(street);
        }

        if (town != null && !town.isEmpty()) {
            address.setTown(town);
        }

        if (county != null && !county.isEmpty()) {
            address.setCounty(county);
        }

        if (eirCode != null && !eirCode.isEmpty()) {
            address.setEirCode(eirCode);
        }

        try {
            double[] coords = getCoordinates(street + ", " + town + ", " + county + "," + eirCode + ", Ireland");
            if (coords == null) {
                terminus = "view-address.jsp";
                session.setAttribute("invalidAddress", "Address could not be verified");
            }
            if (withinRange(coords)) {
                UserRepositories userRep = new UserRepositories(factory);
                address.setUser(activeUser);

                Boolean isAdded = userRep.addAddress(activeUserId, address);
                if (isAdded) {
                    session.setAttribute("addAddress", "Address has been added");
                    session.removeAttribute("addressed");
                } else {
                    terminus = "view-address.jsp";
                    session.setAttribute("failedAddAddress", "Failed to update");
                }
            } else {
                terminus = "view-address.jsp";
                session.setAttribute("outOfRange", "It is out of range");
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred: " + e.getMessage());
        }

        return terminus;
    }

    private double[] getCoordinates(String address) throws IOException {
        String url = "https://api.geoapify.com/v1/geocode/search?text=" + URLEncoder.encode(address, "UTF-8") + "&apiKey=" + API_KEY;
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        //        URL obj = new URL(url);
        // Connection code
//        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
        conn.setRequestMethod("GET");
        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            JSONObject myResponse = new JSONObject(response.toString());
            JSONArray locations = myResponse.getJSONArray("features");

            for (int i = 0; i < locations.length(); i++) {
                JSONObject feature = locations.getJSONObject(i);
                JSONObject properties = feature.getJSONObject("properties");
                String county = properties.optString("county");

                if (county.equalsIgnoreCase("Louth")) {
                    double lat =properties.getDouble("lat");
                    double lon =properties.getDouble("lon");
                    return new double[]{lat, lon};
                }
            }
        }catch (IOException e){
            System.err.println("Error during API request");
            return null;
        }finally {
            conn.disconnect();
        }
        return null;
    }

    private boolean withinRange ( double[] coords){
        //Location of the pizza shop
        final double PIZZASHOP_LAT = 53.9826;
        final double PIZZASHOP_LON = -6.39536;
        //Measured in kilometers
        final double MAXIMUM_DISTANCE = 10;

        double distance = distanceInBetweenEarthMeasurements(coords[0], coords[1], PIZZASHOP_LAT, PIZZASHOP_LON);
        return distance <= MAXIMUM_DISTANCE;
    }

    private double distanceInBetweenEarthMeasurements ( double lat1, double lon1, double lat2, double lon2){
        double earthRadius = 6371;

        double dLat = degreeToRadianConversion(lat2 - lat1);
        double dLon = degreeToRadianConversion(lon2 - lon1);

        lat1 = degreeToRadianConversion(lat1);
        lat2 = degreeToRadianConversion(lat2);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadius * c;
    }

    private double degreeToRadianConversion ( double degrees){
        return degrees * (Math.PI / 180);
    }
}
