package me.daniabudo.formula1ms.application;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DriverImageService {
    // Replace with your actual Unsplash API access key.
    private static final String UNSPLASH_ACCESS_KEY = "m1vjz9mHyZQgtAhvHV3rwom2YrLUtlS6ffZesRwLnHU";

    /**
     * Searches Unsplash for an image matching the driver's name.
     *
     * @param driverName the driver's name
     * @return the URL of the first image found or a placeholder if none is found
     */
    public static String getDriverImageUrl(String driverName) {
        try {
            // URL-encode the driver's name by replacing spaces with '+'
            String formattedName = driverName.replace(" ", "+");

            // Build the Unsplash search URL.
            // You can modify the query parameters (like per_page) if needed.
            String urlStr = "https://api.unsplash.com/search/photos?query="
                    + formattedName
                    + "&client_id=" + UNSPLASH_ACCESS_KEY
                    + "&per_page=1";
            URL url = new URL(urlStr);

            // Open a connection and set up the request.
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000); // 5 seconds timeout
            connection.setReadTimeout(5000);
            connection.connect();

            // Check if the request was successful.
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                System.err.println("Unsplash API request failed with response code: " + responseCode);
                return "https://via.placeholder.com/300?text=" + formattedName;
            }

            // Read the API response.
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder jsonResult = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonResult.append(line);
            }
            reader.close();
            connection.disconnect();

            // Parse the JSON response.
            JSONObject jsonResponse = new JSONObject(jsonResult.toString());
            JSONArray results = jsonResponse.getJSONArray("results");

            // If there are results, return the "small" image URL from the first result.
            if (results.length() > 0) {
                JSONObject firstResult = results.getJSONObject(0);
                JSONObject urlsObject = firstResult.getJSONObject("urls");
                String imageUrl = urlsObject.getString("small");
                return imageUrl;
            } else {
                // No results found; return a placeholder image.
                return "https://via.placeholder.com/300?text=" + formattedName;
            }
        } catch (Exception e) {
            e.printStackTrace();
            // In case of any exception, return a placeholder image.
            return "https://via.placeholder.com/300?text=" + driverName.replace(" ", "+");
        }
    }
}
