package objects;

import exceptions.UnprocessableEntityException;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.text.ParseException;

public class Client {
    private final String username;
    private final String password;
    private Request request;

    public Client(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setCustomRequest(Request request) {
        this.request = request;
    }

    public Request getRequest() {
        return request;
    }

    /**
     * Send sale HTTP POST request
     *
     * @return HttpResponse
     * @throws MalformedURLException
     * @throws ParseException
     */
    public HttpResponse sendSaleRequest() throws IOException, UnprocessableEntityException {
        // Set the request params. and send it
        try {
            return HttpClientBuilder.create().build().execute(request.setSaleRequestParameters(this.username, this.password));
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedEncodingException();
        }

    }

    /**
     * Send void HTTP POST request
     *
     * @param reference_id String id of the sale transaction
     * @return HttpResponse
     * @throws MalformedURLException
     */
    public HttpResponse sendVoidTransaction(String reference_id) throws MalformedURLException, UnprocessableEntityException {
        request = new Request(reference_id);
        try {
            // Set the request params. and send it
            return HttpClientBuilder.create().build().execute(request.setVoidRequestParameters(this.username, this.password));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
