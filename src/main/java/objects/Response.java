package objects;

public class Response {
    private String unique_id;
    private String status;
    private String usage;
    private String amount;
    private String transaction_time;
    private String message;
    private String statusCode;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setTransaction_time(String transaction_time) {
        this.transaction_time = transaction_time;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public String getStatus() {
        return status;
    }

    public String getUsage() {
        return usage;
    }

    public String getAmount() {
        return amount;
    }

    public String getTransaction_time() {
        return transaction_time;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message +
                "\nuniqueId='" + unique_id + '\'' +
                "\nstatus='" + status + '\'' +
                "\nusage='" + usage + '\'' +
                "\namount='" + amount + '\'' +
                "\ntransactionTime='" + transaction_time + '\'';
    }
}
