import com.google.gson.Gson;
import exceptions.UnprocessableEntityException;
import objects.Client;
import objects.Request;
import objects.Response;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SanityTest {
    private static final String username = "pandahapva";
    private static final String password = "kachamak";
    private Client client;
    private HttpResponse httpResponse;
    private static String transactionId;
    private final Request validSaleRequest = new Request("4200000000000000", 123, "06/2019",
            500, "Coffeemaker", "Panda Panda", "panda@example.com", "Panda Street, China");

    public SanityTest() throws MalformedURLException {
    }

    @BeforeEach
    public void setClient() {
        client = new Client(username, password);
    }

    @Test
    @Order(1)
    public void checkForSuccessStatusCode() throws IOException, UnprocessableEntityException {
        client.setCustomRequest(validSaleRequest);
        client.sendSaleRequest();
        httpResponse = client.sendSaleRequest();
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        System.out.println("Checking if status code is 200");
        assertEquals(statusCode, 200);
    }

    @Test
    @Order(2)
    public void checkForApprovedSaleStatus() throws IOException, UnprocessableEntityException {
        client.setCustomRequest(validSaleRequest);
        httpResponse = client.sendSaleRequest();
        Response response = getResponse(httpResponse);
        String saleTransactionStatus = response.getStatus();
        // Get the transaction ID. It's going to be used to send void transaction
        transactionId = response.getUnique_id();

        System.out.println("Checking if sale transaction status is approved");
        assertEquals(saleTransactionStatus, "approved");
    }

    @Test
    @Order(3)
    public void checkForApprovedVoidStatus() throws IOException, UnprocessableEntityException {
        httpResponse = client.sendVoidTransaction(transactionId);
        Response response = getResponse(httpResponse);
        String saleTransactionStatus = response.getStatus();
        System.out.println("Checking if void transaction status is approved");
        assertEquals(saleTransactionStatus, "approved");
    }

    @Test
    @Order(4)
    public void checkStatusCodeInvalidSaleTransaction() throws IOException, ParseException, UnprocessableEntityException {
        // Set new sale request with missing expiration_date
        client.setCustomRequest(new Request("4200000000000000", 123, "", 500, "Coffeemaker"
                , "Panda Panda", "panda@example.com", "Panda Street, China"));
        int statusCode = client.sendSaleRequest().getStatusLine().getStatusCode();
        System.out.println("Checking if the user is able to send request with missing mandatory field");
        assertEquals(statusCode, 422);
    }

    @Test
    @Order(4)
    public void checkStatusCodeInvalidVoidTransaction() throws IOException, ParseException, UnprocessableEntityException {
        httpResponse = client.sendVoidTransaction("wrongId");
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        System.out.println("Checking if void transaction status code is 422 when client send request with invalid transaction ID");
        assertEquals(statusCode, 422);
    }

    @Test
    @Order(5)
    public void testAccessClient() throws IOException, UnprocessableEntityException {
        Client wrongClient = new Client("admin", "nopass");
        wrongClient.setCustomRequest(validSaleRequest);
        int statusCode = wrongClient.sendSaleRequest().getStatusLine().getStatusCode();
        System.out.println("Checking if the user is able to login with wrong credentials");
        assertEquals(statusCode, 401);
    }

    /**
     * Gets as param. HttpResponse and convert it to Response object
     *
     * @param httpResponse HttpResponse
     * @return Response
     * @throws IOException
     */
    private Response getResponse(HttpResponse httpResponse) throws IOException {
        String json = EntityUtils.toString(httpResponse.getEntity());
        return new Gson().fromJson(json, Response.class);
    }
}
