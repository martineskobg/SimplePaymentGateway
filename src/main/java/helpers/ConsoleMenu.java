package helpers;

import com.google.gson.Gson;
import exceptions.CustomAuthenticationException;
import objects.Response;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.rmi.UnexpectedException;

public class ConsoleMenu {
    // Main menu
    private static final String[] menuOptions = new String[]{
            "For Sale Transaction press: S",
            "For Void Transaction press: V",
            "For Quit press: Q"
    };

    // Prints main menu on the console
    public static void showMenu() {
        int i = 1;//iterator
        //foreach menu options
        for (String item : menuOptions) {
            System.out.println(i + " -> " + item);
            i++;
        }
    }

    //resource https://mkyong.com/java/how-do-convert-java-object-to-from-json-format-gson-api/

    /**
     * Converts JSON to Java Object
     *
     * @param httpResponse HttpResponse
     * @throws IOException
     */
    public static int printResponse(HttpResponse httpResponse) throws IOException {
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            String json = EntityUtils.toString(httpResponse.getEntity());
            Response response = new Gson().fromJson(json, Response.class);
            System.out.println("\n" + response.toString() + "\n");
        } else if (statusCode == 401) {
            throw new CustomAuthenticationException("Access denied! Wrong user/password. Status code:" + statusCode);
        } else {
            throw new UnexpectedException("An unexpected exception occurred");
        }
        return statusCode;
    }
}
