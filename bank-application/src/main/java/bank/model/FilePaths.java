package bank.model;


public enum FilePaths {
    CUSTOMER("ek/Customer.json"),
    ACCOUNT("ek/Accounts.json"),
    CREDIT("ek/Credit.json");

    private final String path;

    FilePaths(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}

