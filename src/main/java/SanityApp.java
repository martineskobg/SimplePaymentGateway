import exceptions.CustomAuthenticationException;
import exceptions.UnprocessableEntityException;
import helpers.ConsoleMenu;
import objects.Client;
import objects.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.UnexpectedException;
import java.util.Locale;

public class SanityApp {

    public static void main(String[] args) throws IOException {
        String password;
        String username;


        //  Enter data using BufferReader
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter username: ");
        username = reader.readLine();
        System.out.println("Enter password: ");
        password = reader.readLine();

        Client client = new Client(username, password);
        client.setCustomRequest(new Request("4200000000000000", 123, "06/2019",
                500, "Coffeemaker", "Panda Panda", "panda@example.com", "Panda Street, China"));
        System.out.println("\nHello " + client.getUsername());
        // Print menu options on the console

        String id;

        boolean isRunning = true;
        try {
            while (isRunning) {
                ConsoleMenu.showMenu();
                switch (reader.readLine().toLowerCase(Locale.ROOT)) {
                    case "s" -> {
                        try {
                            // Send sale request and print the response
                            ConsoleMenu.printResponse(client.sendSaleRequest());
                        } catch (CustomAuthenticationException | UnexpectedException |
                                 UnprocessableEntityException ex) {
                            System.err.println(ex);
                        }
                    }
                    case "v" -> {
                        System.out.println("Enter Reference ID: ");
                        id = reader.readLine();
                        // Send void request and print the response
                        try {
                            ConsoleMenu.printResponse(client.sendVoidTransaction(id));
                        } catch (UnexpectedException ex) {
                            System.err.println(ex);
                        } catch (UnprocessableEntityException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    case "q" -> isRunning = false;
                    default -> System.out.println("Unknown choice please try again!\n");
                }
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
