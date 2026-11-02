public class Payment {
    private String method;
    private double amount;
    private boolean paid;

    public Payment(String method, double amount) {
        this.method = method;
        this.amount = amount;
        this.paid = false;
    }

    public void processPayment() {
        // Demo only: mark payment as successful; no real money is transferred.
        paid = true;
    }

    public String getMethod() { return method; }
    public double getAmount() { return amount; }
    public boolean isPaid() { return paid; }
}
