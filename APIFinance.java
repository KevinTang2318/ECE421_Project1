import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.io.*;

public class APIFinance {
    private static final String BASE_URL = "https://www.alphavantage.co/query?";
    private final static String apiKey = "0LQTWHJ9OCWIRFN8";

    private static long lastRecordedTime = 0;
    private static int accessCount = 0;

    public static BigDecimal getPrice(final String symbol) {
        
        // This function checks whether we current have access to the api;
        checkAccess();

        BigDecimal price = new BigDecimal(0);

        try {
            URL url = new URL(BASE_URL + "function=GLOBAL_QUOTE&symbol=" + symbol + "&apikey=" + apiKey);
            URLConnection connection = url.openConnection();

            InputStreamReader inputStream = new InputStreamReader(connection.getInputStream(), "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStream);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("price")) {
                    price = new BigDecimal(line.split("\"")[3].trim());
                    
                    // Here we accommodate the situation when all 500 accesses has been depleted
                    // We simply exit the program
                    if (price.compareTo(BigDecimal.valueOf(0)) == 0) {
                        System.out.println("Access denied, please check today's access limit.");
                        System.exit(-1);
                    }
                }
            }
            bufferedReader.close();
        } 
        catch (IOException e) {
            System.out.println("failure sending request");
        }

        return price;
    }

    // This function mainly deals with the "5 access per minute" constraint
    // If we have already accessed 5 times in this minute, we sleep until next minute and keep accessing
    private static void checkAccess(){
        // Initialize the lastRecordedTime variable
        if (lastRecordedTime == 0) 
            lastRecordedTime = System.currentTimeMillis();

        long currentTime = System.currentTimeMillis();

        // If we are still within the current 1 min window
        if ((currentTime - lastRecordedTime) < (1000*60)) {
            if (accessCount >= 5) {
                System.out.println("Access Paused, waiting till next minute");
                accessCount = 0;
                try {
                    Thread.sleep(1000*60 - (currentTime - lastRecordedTime) + 1000); // The added 1 second is to compensate potential calculation delay
                } catch (Exception e) {
                    System.out.println("Sleep is interrupted");
                }
            }
    
            if (accessCount < 5)
                accessCount++;
        }
        else {
            lastRecordedTime = System.currentTimeMillis();
            accessCount = 1;
        }
    }
}
