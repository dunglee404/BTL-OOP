import java.util.ArrayList;
import java.util.Scanner;

public class ShoppingOnlineSystem {
    private Scanner sc = new Scanner(System.in);
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Product> products = new ArrayList<>();
    private int nextCustomerId = 2;
    private int nextProductId = 4;
    private int nextOrderId = 1;

    public ShoppingOnlineSystem() {
        users.add(new Admin("A001", "admin", "admin123"));
        users.add(new Customer("C001", "quan", "123456"));

        products.add(new Product("P001", "Keyboard", 350000, 10));
        products.add(new Product("P002", "Mouse", 150000, 15));
        products.add(new Product("P003", "Headphones", 450000, 8));
    }

    public static void main(String[] args) {
        new ShoppingOnlineSystem().run();
    }

    private void run() {
        while (true) {
            System.out.println("\n===== SHOPPING ONLINE SYSTEM =====");
            System.out.println("1. Login");
            System.out.println("2. Register customer account");
            System.out.println("0. Exit");
            int choice = readInt("Choose: ");

            if (choice == 1) login();
            else if (choice == 2) registerCustomer();
            else if (choice == 0) {
                System.out.println("Goodbye!");
                break;
            } else System.out.println("Invalid choice.");
        }
        sc.close();
    }

    private void login() {
        System.out.print("Username: ");
        String username = sc.nextLine().trim();
        System.out.print("Password: ");
        String password = sc.nextLine();

        for (User user : users) {
            if (user.login(username, password)) {
                System.out.println("Login successful. Welcome, " + user.getUsername()
                        + " (ID: " + user.getId() + ", role: " + user.getRole() + ")");
                if (user instanceof Admin) adminMenu((Admin) user);
                else customerMenu((Customer) user);
                return;
            }
        }
        System.out.println("Incorrect username or password.");
    }

    private void registerCustomer() {
        System.out.print("Choose username: ");
        String username = sc.nextLine().trim();
        if (username.isEmpty()) {
            System.out.println("Username cannot be empty.");
            return;
        }
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                System.out.println("Username already exists.");
                return;
            }
        }
        System.out.print("Choose password: ");
        String password = sc.nextLine();
        if (password.trim().isEmpty()) {
            System.out.println("Password cannot be empty.");
            return;
        }
        String id = String.format("C%03d", nextCustomerId++);
        users.add(new Customer(id, username, password));
        System.out.println("Registration successful. Your customer ID is " + id + ".");
    }

    private void customerMenu(Customer customer) {
        while (true) {
            System.out.println("\n===== CUSTOMER MENU =====");
            System.out.println("1. View products");
            System.out.println("2. Add product to cart");
            System.out.println("3. View cart");
            System.out.println("4. Checkout");
            System.out.println("5. View order history");
            System.out.println("6. Change password");
            System.out.println("0. Logout");
            int choice = readInt("Choose: ");
            if (choice == 1) showProducts();
            else if (choice == 2) addToCart(customer);
            else if (choice == 3) showCart(customer);
            else if (choice == 4) checkout(customer);
            else if (choice == 5) showOrderHistory(customer);
            else if (choice == 6) changePassword(customer);
            else if (choice == 0) return;
            else System.out.println("Invalid choice.");
        }
    }

    private void adminMenu(Admin admin) {
        while (true) {
            System.out.println("\n===== ADMIN MENU =====");
            System.out.println("1. View products");
            System.out.println("2. Add product");
            System.out.println("0. Logout");
            int choice = readInt("Choose: ");
            if (choice == 1) showProducts();
            else if (choice == 2) addProduct();
            else if (choice == 0) return;
            else System.out.println("Invalid choice.");
        }
    }

    private void showProducts() {
        System.out.println("\n--- PRODUCT LIST ---");
        for (Product product : products) System.out.println(product);
    }

    private Product findProduct(String id) {
        for (Product product : products) {
            if (product.getId().equalsIgnoreCase(id)) return product;
        }
        return null;
    }

    private void addToCart(Customer customer) {
        showProducts();
        System.out.print("Enter product ID: ");
        String id = sc.nextLine().trim();
        Product product = findProduct(id);
        if (product == null) {
            System.out.println("Product not found.");
            return;
        }
        int quantity = readInt("Quantity: ");
        try {
            customer.getCart().addProduct(product, quantity);
            System.out.println("Product added to cart.");
        } catch (IllegalArgumentException e) {
            System.out.println("Cannot add product: " + e.getMessage());
        }
    }

    private void showCart(Customer customer) {
        Cart cart = customer.getCart();
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }
        System.out.println("\n--- YOUR CART ---");
        for (CartItem item : cart.getItems()) {
            System.out.printf("%-22s x %-3d %10.0f VND%n",
                    item.getProduct().getName(), item.getQuantity(), item.getSubtotal());
        }
        System.out.printf("Total: %.0f VND%n", cart.getTotal());
    }

    private void checkout(Customer customer) {
        Cart cart = customer.getCart();
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }
        // Check stock again before creating the order.
        for (CartItem item : cart.getItems()) {
            if (item.getQuantity() > item.getProduct().getStock()) {
                System.out.println("Not enough stock for " + item.getProduct().getName() + ".");
                return;
            }
        }
        showCart(customer);
        System.out.println("Payment method: 1. Cash on delivery  2. Bank transfer (demo)");
        int choice = readInt("Choose payment method: ");
        if (choice != 1 && choice != 2) {
            System.out.println("Invalid payment method. Checkout cancelled.");
            return;
        }
        String method = choice == 1 ? "Cash on delivery" : "Bank transfer (demo)";
        Order order = new Order(String.format("O%03d", nextOrderId++), customer.getId(), cart, method);
        for (CartItem item : cart.getItems()) item.getProduct().reduceStock(item.getQuantity());
        if (choice == 2) order.getPayment().processPayment();
        customer.addOrder(order);
        cart.clear();
        System.out.println("Order placed successfully!");
        order.printDetails();
    }

    private void showOrderHistory(Customer customer) {
        if (customer.getOrderHistory().isEmpty()) {
            System.out.println("You have no orders yet.");
            return;
        }
        System.out.println("\n--- ORDER HISTORY ---");
        for (Order order : customer.getOrderHistory()) {
            order.printDetails();
            System.out.println("------------------------------");
        }
    }

    private void addProduct() {
        System.out.print("Product name: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Product name cannot be empty.");
            return;
        }
        double price = readDouble("Price (VND): ");
        int stock = readInt("Stock quantity: ");
        if (price <= 0 || stock < 0) {
            System.out.println("Price must be positive and stock cannot be negative.");
            return;
        }
        String id = String.format("P%03d", nextProductId++);
        products.add(new Product(id, name, price, stock));
        System.out.println("Product added: " + id);
    }

    private void changePassword(User user) {
        System.out.print("Current password: ");
        String oldPassword = sc.nextLine();
        System.out.print("New password: ");
        String newPassword = sc.nextLine();
        if (user.changePassword(oldPassword, newPassword))
            System.out.println("Password changed successfully.");
        else System.out.println("Password change failed. Check your current password and new password.");
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
