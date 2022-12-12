package helpers;

import com.google.gson.JsonObject;
import objects.Request;

public class RequestToJsonString {
    /**
     * Converts SALE request object into JsonObject
     *
     * @param request Request
     * @return String
     */
    public static String saleRequestToJson(Request request) {
        // Resource https://www.tabnine.com/code/java/methods/com.google.gson.JsonObject/add
        JsonObject saleTransactionsValues = new JsonObject();
        saleTransactionsValues.addProperty("card_number", request.getCard_number());
        saleTransactionsValues.addProperty("cvv", Integer.toString(request.getCvv()));
        saleTransactionsValues.addProperty("expiration_date", request.getExpiration_date());
        saleTransactionsValues.addProperty("amount", Integer.toString(request.getAmount()));
        saleTransactionsValues.addProperty("usage", request.getUsage());
        saleTransactionsValues.addProperty("transaction_type", request.getTransaction_type());
        saleTransactionsValues.addProperty("card_holder", request.getCard_holder());
        saleTransactionsValues.addProperty("email", request.getEmail());
        saleTransactionsValues.addProperty("address", request.getAddress());

        JsonObject js = new JsonObject();
        js.add("payment_transaction", saleTransactionsValues);
        return js.toString();
    }

    /**
     * Converts VOID request object into JsonObject
     *
     * @param request Request
     * @return String
     */
    public static String voidRequestToJson(Request request) {
        JsonObject voidTransactionValues = new JsonObject();
        voidTransactionValues.addProperty("reference_id", request.getReferenceId());
        voidTransactionValues.addProperty("transaction_type", request.getTransaction_type());
        JsonObject js = new JsonObject();
        js.add("payment_transaction", voidTransactionValues);
        return js.toString();
    }
}
