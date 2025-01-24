package restapiclient;

import org.json.JSONObject;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DogPictures {

    private static final String API_URL = "https://dog.ceo/api/breeds/image/random";  // API URL for random dog images

    public static void main(String[] args) {
        try {
            // Make the HTTP request to the Dog API
            HttpURLConnection connection = (HttpURLConnection) new URL(API_URL).openConnection();
            connection.setRequestMethod("GET");
            
            // Read the response from the API
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            
            // Parse the JSON response
            JSONObject jsonResponse = new JSONObject(response.toString());
            
            // Extract and display the dog image
            displayDogPicture(jsonResponse);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void displayDogPicture(JSONObject jsonResponse) {
        // Check if the response contains the image URL
        if (jsonResponse != null && jsonResponse.has("message")) {
            String dogImageUrl = jsonResponse.getString("message");
            System.out.println("Random Dog Picture:");
            System.out.println("---------------------");
            System.out.println("Here is a random dog picture URL: " + dogImageUrl);

            // Display the image using Swing
            try {
                // Create an ImageIcon from the URL
                ImageIcon dogImage = new ImageIcon(new URL(dogImageUrl));
                JLabel label = new JLabel(dogImage);

                // Set up the JFrame
                JFrame frame = new JFrame("Random Dog Picture");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(600, 600); // Adjust the size as needed
                frame.add(label);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Unable to retrieve dog picture. Please check the API.");
        }
    }
}
