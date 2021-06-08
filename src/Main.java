import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.Console;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;


public class Main {

    public static void main(String args[]) {

        Console console = System.console();

        String inline = "";

        try {
            URL url = new URL("https://api.covid19api.com/summary"); // GET Summary by Country
            HttpURLConnection con = (HttpURLConnection) url.openConnection(); //Type cast url to HttpURLConnection object for benefits/behavior specific to this object
            con.setRequestMethod("GET");
            con.connect();
            int responseCode = con.getResponseCode();

            if (responseCode != 200) {
                throw new RuntimeException("Error: Response Code: " + responseCode);
            } else {
                System.out.println("Connected");

                Scanner sc = new Scanner(url.openStream());

                while (sc.hasNext()) {
                    inline += sc.nextLine();
                }

                System.out.println("\nData from https://covid19api.com/");
                System.out.println("Response Format: JSON");
                System.out.println(inline);
                sc.close();
            }

            JSONParser parser = new JSONParser();
            JSONObject jObjects = (JSONObject) parser.parse(inline); //Convert the string into JSON objects
            JSONArray jArray_1 = (JSONArray) jObjects.get("Countries"); //Converts JSON object into JSONArray

            for (int i = 0; i < jArray_1.size();i++) {
                JSONObject jsonObj_1 = (JSONObject) jArray_1.get(i);
                if ((long) jsonObj_1.get("NewConfirmed") > 0) {
                    System.out.println("\nCountry: " + jsonObj_1.get("Country"));
                    System.out.println("New Confirmed: " + jsonObj_1.get("NewConfirmed"));
                }
            }

            con.disconnect();
            System.out.println("Disconnected");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
