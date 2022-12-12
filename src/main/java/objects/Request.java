package objects;

import helpers.RequestToJsonString;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.Properties;

public class Request {
    private final String BASE_URL = loadPropertiesFromFile().getProperty("url");
    private final String END_POINT_PAYMENT_TRANSACTION = loadPropertiesFromFile().getProperty("endPoint");
    URL url = new URL(BASE_URL + END_POINT_PAYMENT_TRANSACTION);
    private String card_number;
    private int cvv;
    private String expiration_date;
    private int amount;
    private String usage;
    private final String transaction_type;
    private String card_holder;
    private String email;
    private String address;
    private String referenceId;

    public Request(String card_number, int cvv, String expiration_date, int amount, String usage, String card_holder,
                   String email, String address) throws MalformedURLException {
        this.card_number = card_number;
        this.cvv = cvv;
        this.expiration_date = expiration_date;
        this.amount = amount;
        this.usage = usage;
        this.transaction_type = this.loadPropertiesFromFile().getProperty("saleTransaction");
        this.card_holder = card_holder;
        this.email = email;
        this.address = address;
    }

    public Request(String referenceId) throws MalformedURLException {
        this.referenceId = referenceId;
        this.transaction_type = this.loadPropertiesFromFile().getProperty("voidTransaction");
    }

    // Resource https://mkyong.com/java/java-properties-file-examples/

    /**
     * Load properties from config.properties
     *
     * @return Properties as MAP
     */
    private Properties loadPropertiesFromFile() {
        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            return prop;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getCard_number() {
        return card_number;
    }

    public int getCvv() {
        return cvv;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public int getAmount() {
        return amount;
    }

    public String getUsage() {
        return usage;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public String getCard_holder() {
        return card_holder;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public HttpPost post;
    public StringEntity postingString;


    /**
     * @param username String
     * @param password String
     * @return String
     */
    // Resource https://www.baeldung.com/java-httpclient-basic-auth
    public static String getBasicAuthenticationHeader(String username, String password) {
        String valueToEncode = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());
    }

    /**
     * Set parameters for sale transaction
     *
     * @param username String
     * @param password String
     * @return HttpPost
     * @throws UnsupportedEncodingException
     */
    public HttpPost setSaleRequestParameters(String username, String password) throws UnsupportedEncodingException {
        this.postingString = new StringEntity(RequestToJsonString.saleRequestToJson(this));
        return getHttpPost(username, password);
    }

    /**
     * Set parameters for void transaction
     *
     * @param username String
     * @param password String
     * @return HttpPost
     * @throws UnsupportedEncodingException
     */
    public HttpPost setVoidRequestParameters(String username, String password) throws UnsupportedEncodingException {
        this.postingString = new StringEntity(RequestToJsonString.voidRequestToJson(this));
        return getHttpPost(username, password);
    }

    /**
     * Prepare HTTP POST request it can be used for any types of transactions
     *
     * @param username String
     * @param password String
     * @return HttpPost
     */
    private HttpPost getHttpPost(String username, String password) {
        this.post = new HttpPost(this.url.toString());
        this.post.setEntity(postingString);
        this.post.setHeader("Content-type", "application/json;charset=UTF-8");
        this.post.setHeader("Authorization", getBasicAuthenticationHeader(username, password));
        return post;
    }
}
