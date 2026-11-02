import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ShoppingSwingApp extends JFrame {
    private final ArrayList<User> users = new ArrayList<>();
    private final ArrayList<Product> products = new ArrayList<>();
    private int nextCustomerId = 2;
    private int nextProductId = 4;
    private int nextOrderId = 1;

    private User currentUser;

    // Main CardLayout
    private final CardLayout mainCardLayout = new CardLayout();
    private final JPanel mainContainer = new JPanel(mainCardLayout);

    // Login components
    private JTextField loginUsernameField;
    private JPasswordField loginPasswordField;

    // Register components
    private JTextField regUsernameField;
    private JPasswordField regPasswordField;

    // Customer components
    private JLabel customerWelcomeLabel;
    private DefaultTableModel customerProductModel;
    private JTable customerProductTable;
    private JTextField cartQuantityField;
    private DefaultTableModel cartTableModel;
    private JLabel cartTotalLabel;
    private JComboBox<String> paymentMethodCombo;
    private DefaultTableModel orderHistoryTableModel;
    private JTable orderHistoryTable;
    private DefaultTableModel orderItemsTableModel;
    private JTabbedPane customerTabs;

    // Admin components
    private JLabel adminWelcomeLabel;
    private DefaultTableModel adminProductModel;
    private JTextField prodNameField;
    private JTextField prodPriceField;
    private JTextField prodStockField;

    public ShoppingSwingApp() {
        initSampleData();
        initUI();
    }

    private void initSampleData() {
        users.add(new Admin("A001", "admin", "admin123"));
        users.add(new Customer("C001", "quan", "123456"));

        products.add(new Product("P001", "Keyboard", 350000, 10));
        products.add(new Product("P002", "Mouse", 150000, 15));
        products.add(new Product("P003", "Headphones", 450000, 8));
    }

    private void initUI() {
        setTitle("Shopping Online System - Milestone 4 (Java Swing)");
        setSize(900, 620);
        setMinimumSize(new Dimension(800, 550));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainContainer.add(createAuthPanel(), "AUTH");
        mainContainer.add(createCustomerPanel(), "CUSTOMER");
        mainContainer.add(createAdminPanel(), "ADMIN");

        setContentPane(mainContainer);
        mainCardLayout.show(mainContainer, "AUTH");
    }

    // ==================== AUTH PANEL (LOGIN & REGISTER) ====================
    private JPanel createAuthPanel() {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(new Color(245, 247, 250));

        JPanel card = new JPanel(new BorderLayout(15, 15));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 215, 225), 1),
                new EmptyBorder(25, 30, 25, 30)
        ));
        card.setPreferredSize(new Dimension(430, 440));

        JLabel title = new JLabel("SHOPPING ONLINE SYSTEM", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(new Color(33, 43, 54));
        card.add(title, BorderLayout.NORTH);

        JTabbedPane authTabs = new JTabbedPane();
        authTabs.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // --- Tab 1: Login ---
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setBorder(new EmptyBorder(15, 10, 15, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
        loginPanel.add(new JLabel("Username:"), gbc);

        loginUsernameField = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.7;
        loginPanel.add(loginUsernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.3;
        loginPanel.add(new JLabel("Password:"), gbc);

        loginPasswordField = new JPasswordField(15);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.7;
        loginPanel.add(loginPasswordField, gbc);

        JButton loginBtn = createStyledButton("Đăng nhập (Login)", new Color(37, 99, 235), Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        gbc.insets = new Insets(16, 8, 8, 8);
        loginPanel.add(loginBtn, gbc);

        // Quick demo fill buttons
        JPanel demoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        demoPanel.setBackground(Color.WHITE);
        demoPanel.setBorder(new TitledBorder("Tài khoản mẫu (Click để điền nhanh)"));
        JButton fillAdminBtn = new JButton("Admin (admin / admin123)");
        JButton fillCustBtn = new JButton("Customer (quan / 123456)");
        fillAdminBtn.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        fillCustBtn.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        fillAdminBtn.addActionListener(e -> {
            loginUsernameField.setText("admin");
            loginPasswordField.setText("admin123");
        });
        fillCustBtn.addActionListener(e -> {
            loginUsernameField.setText("quan");
            loginPasswordField.setText("123456");
        });
        demoPanel.add(fillCustBtn);
        demoPanel.add(fillAdminBtn);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 4, 4, 4);
        loginPanel.add(demoPanel, gbc);

        loginBtn.addActionListener(e -> handleLogin());
        loginPasswordField.addActionListener(e -> handleLogin());

        // --- Tab 2: Register Customer ---
        JPanel regPanel = new JPanel(new GridBagLayout());
        regPanel.setBackground(Color.WHITE);
        regPanel.setBorder(new EmptyBorder(15, 10, 15, 10));
        GridBagConstraints rgbc = new GridBagConstraints();
        rgbc.insets = new Insets(8, 8, 8, 8);
        rgbc.fill = GridBagConstraints.HORIZONTAL;

        rgbc.gridx = 0; rgbc.gridy = 0; rgbc.weightx = 0.3;
        regPanel.add(new JLabel("New Username:"), rgbc);

        regUsernameField = new JTextField(15);
        rgbc.gridx = 1; rgbc.gridy = 0; rgbc.weightx = 0.7;
        regPanel.add(regUsernameField, rgbc);

        rgbc.gridx = 0; rgbc.gridy = 1; rgbc.weightx = 0.3;
        regPanel.add(new JLabel("New Password:"), rgbc);

        regPasswordField = new JPasswordField(15);
        rgbc.gridx = 1; rgbc.gridy = 1; rgbc.weightx = 0.7;
        regPanel.add(regPasswordField, rgbc);

        JButton regBtn = createStyledButton("Đăng ký Khách hàng (Register)", new Color(22, 163, 74), Color.WHITE);
        rgbc.gridx = 0; rgbc.gridy = 2; rgbc.gridwidth = 2;
        rgbc.insets = new Insets(16, 8, 8, 8);
        regPanel.add(regBtn, rgbc);

        regBtn.addActionListener(e -> handleRegister(authTabs));

        authTabs.addTab("Đăng nhập", loginPanel);
        authTabs.addTab("Đăng ký Khách hàng", regPanel);

        card.add(authTabs, BorderLayout.CENTER);
        wrapper.add(card);
        return wrapper;
    }

    // ==================== CUSTOMER PANEL ====================
    private JPanel createCustomerPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(12, 12, 12, 12));

        // Top Bar
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(30, 58, 138));
        topBar.setBorder(new EmptyBorder(10, 15, 10, 15));

        customerWelcomeLabel = new JLabel("Welcome, Customer");
        customerWelcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        customerWelcomeLabel.setForeground(Color.WHITE);
        topBar.add(customerWelcomeLabel, BorderLayout.WEST);

        JPanel topActions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        topActions.setOpaque(false);
        JButton changePassBtn = new JButton("Đổi mật khẩu");
        JButton logoutBtn = new JButton("Đăng xuất");
        changePassBtn.addActionListener(e -> showChangePasswordDialog());
        logoutBtn.addActionListener(e -> logout());
        topActions.add(changePassBtn);
        topActions.add(logoutBtn);
        topBar.add(topActions, BorderLayout.EAST);

        panel.add(topBar, BorderLayout.NORTH);

        // Tabs for Customer
        customerTabs = new JTabbedPane();
        customerTabs.setFont(new Font("Segoe UI", Font.BOLD, 13));

        customerTabs.addTab("1. Danh sách Sản phẩm (Products)", createCustomerProductsTab());
        customerTabs.addTab("2. Giỏ hàng & Thanh toán (Cart & Checkout)", createCustomerCartTab());
        customerTabs.addTab("3. Lịch sử Đơn hàng (Order History)", createCustomerOrdersTab());

        panel.add(customerTabs, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createCustomerProductsTab() {
        JPanel tab = new JPanel(new BorderLayout(10, 10));
        tab.setBorder(new EmptyBorder(10, 10, 10, 10));

        customerProductModel = new DefaultTableModel(
                new Object[]{"Mã SP (ID)", "Tên sản phẩm (Name)", "Giá (VND)", "Tồn kho (Stock)"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        customerProductTable = new JTable(customerProductModel);
        customerProductTable.setRowHeight(28);
        customerProductTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tab.add(new JScrollPane(customerProductTable), BorderLayout.CENTER);

        JPanel bottomBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 8));
        bottomBar.setBorder(new TitledBorder("Thêm sản phẩm đang chọn vào giỏ hàng"));
        bottomBar.add(new JLabel("Số lượng (Quantity):"));
        cartQuantityField = new JTextField("1", 6);
        bottomBar.add(cartQuantityField);

        JButton addToCartBtn = createStyledButton("Thêm vào Giỏ hàng (Add to Cart)", new Color(37, 99, 235), Color.WHITE);
        addToCartBtn.addActionListener(e -> handleAddToCart());
        bottomBar.add(addToCartBtn);

        tab.add(bottomBar, BorderLayout.SOUTH);
        return tab;
    }

    private JPanel createCustomerCartTab() {
        JPanel tab = new JPanel(new BorderLayout(10, 10));
        tab.setBorder(new EmptyBorder(10, 10, 10, 10));

        cartTableModel = new DefaultTableModel(
                new Object[]{"Mã SP", "Tên sản phẩm", "Đơn giá (VND)", "Số lượng", "Thành tiền (VND)"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable cartTable = new JTable(cartTableModel);
        cartTable.setRowHeight(28);
        tab.add(new JScrollPane(cartTable), BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new BorderLayout(10, 10));
        southPanel.setBorder(new TitledBorder("Thanh toán (Checkout)"));

        cartTotalLabel = new JLabel("Tổng cộng (Total): 0 VND");
        cartTotalLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        cartTotalLabel.setForeground(new Color(185, 28, 28));
        cartTotalLabel.setBorder(new EmptyBorder(5, 10, 5, 10));
        southPanel.add(cartTotalLabel, BorderLayout.WEST);

        JPanel checkoutControls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        checkoutControls.add(new JLabel("Phương thức thanh toán:"));
        paymentMethodCombo = new JComboBox<>(new String[]{
                "Cash on delivery",
                "Bank transfer (demo)"
        });
        checkoutControls.add(paymentMethodCombo);

        JButton clearCartBtn = new JButton("Xóa giỏ hàng");
        clearCartBtn.addActionListener(e -> {
            Customer customer = (Customer) currentUser;
            customer.getCart().clear();
            refreshCustomerViews();
        });
        checkoutControls.add(clearCartBtn);

        JButton checkoutBtn = createStyledButton("Đặt hàng (Checkout)", new Color(22, 163, 74), Color.WHITE);
        checkoutBtn.addActionListener(e -> handleCheckout());
        checkoutControls.add(checkoutBtn);

        southPanel.add(checkoutControls, BorderLayout.EAST);
        tab.add(southPanel, BorderLayout.SOUTH);
        return tab;
    }

    private JPanel createCustomerOrdersTab() {
        JPanel tab = new JPanel(new BorderLayout(10, 10));
        tab.setBorder(new EmptyBorder(10, 10, 10, 10));

        orderHistoryTableModel = new DefaultTableModel(
                new Object[]{"Mã Đơn (Order ID)", "Khách hàng", "Tổng tiền (VND)", "Phương thức TT", "Trạng thái TT"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        orderHistoryTable = new JTable(orderHistoryTableModel);
        orderHistoryTable.setRowHeight(26);
        orderHistoryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        orderItemsTableModel = new DefaultTableModel(
                new Object[]{"Tên sản phẩm", "Đơn giá (VND)", "Số lượng", "Thành tiền (VND)"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable orderItemsTable = new JTable(orderItemsTableModel);
        orderItemsTable.setRowHeight(26);

        orderHistoryTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateSelectedOrderDetails();
            }
        });

        JScrollPane topScroll = new JScrollPane(orderHistoryTable);
        topScroll.setBorder(new TitledBorder("Danh sách Đơn hàng (Chọn 1 đơn để xem chi tiết)"));

        JScrollPane bottomScroll = new JScrollPane(orderItemsTable);
        bottomScroll.setBorder(new TitledBorder("Chi tiết các mặt hàng trong đơn"));

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topScroll, bottomScroll);
        splitPane.setDividerLocation(220);
        splitPane.setResizeWeight(0.5);

        tab.add(splitPane, BorderLayout.CENTER);
        return tab;
    }

    // ==================== ADMIN PANEL ====================
    private JPanel createAdminPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(12, 12, 12, 12));

        // Top Bar
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(15, 23, 42));
        topBar.setBorder(new EmptyBorder(10, 15, 10, 15));

        adminWelcomeLabel = new JLabel("Welcome, Admin");
        adminWelcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        adminWelcomeLabel.setForeground(Color.WHITE);
        topBar.add(adminWelcomeLabel, BorderLayout.WEST);

        JPanel topActions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        topActions.setOpaque(false);
        JButton changePassBtn = new JButton("Đổi mật khẩu");
        JButton logoutBtn = new JButton("Đăng xuất");
        changePassBtn.addActionListener(e -> showChangePasswordDialog());
        logoutBtn.addActionListener(e -> logout());
        topActions.add(changePassBtn);
        topActions.add(logoutBtn);
        topBar.add(topActions, BorderLayout.EAST);

        panel.add(topBar, BorderLayout.NORTH);

        // Center Table
        adminProductModel = new DefaultTableModel(
                new Object[]{"Mã SP (ID)", "Tên sản phẩm (Name)", "Giá (VND)", "Tồn kho (Stock)"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable adminProductTable = new JTable(adminProductModel);
        adminProductTable.setRowHeight(28);
        JScrollPane scrollPane = new JScrollPane(adminProductTable);
        scrollPane.setBorder(new TitledBorder("Danh mục Sản phẩm trong kho"));
        panel.add(scrollPane, BorderLayout.CENTER);

        // Add Product Form
        JPanel addForm = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        addForm.setBorder(new TitledBorder("Thêm sản phẩm mới (Add Product)"));

        addForm.add(new JLabel("Tên SP:"));
        prodNameField = new JTextField(16);
        addForm.add(prodNameField);

        addForm.add(new JLabel("Giá (VND):"));
        prodPriceField = new JTextField(10);
        addForm.add(prodPriceField);

        addForm.add(new JLabel("Tồn kho:"));
        prodStockField = new JTextField(6);
        addForm.add(prodStockField);

        JButton addProdBtn = createStyledButton("Thêm Sản phẩm", new Color(37, 99, 235), Color.WHITE);
        addProdBtn.addActionListener(e -> handleAdminAddProduct());
        addForm.add(addProdBtn);

        panel.add(addForm, BorderLayout.SOUTH);
        return panel;
    }

    // ==================== EVENT HANDLERS & LOGIC ====================
    private void handleLogin() {
        String username = loginUsernameField.getText().trim();
        String password = new String(loginPasswordField.getPassword());

        for (User user : users) {
            if (user.login(username, password)) {
                currentUser = user;
                loginUsernameField.setText("");
                loginPasswordField.setText("");

                if (user instanceof Admin) {
                    adminWelcomeLabel.setText("Xin chào, " + user.getUsername()
                            + " (ID: " + user.getId() + " | Vai trò: " + user.getRole() + ")");
                    refreshAdminViews();
                    mainCardLayout.show(mainContainer, "ADMIN");
                } else if (user instanceof Customer) {
                    customerWelcomeLabel.setText("Xin chào, " + user.getUsername()
                            + " (ID: " + user.getId() + " | Vai trò: " + user.getRole() + ")");
                    refreshCustomerViews();
                    customerTabs.setSelectedIndex(0);
                    mainCardLayout.show(mainContainer, "CUSTOMER");
                }
                return;
            }
        }
        JOptionPane.showMessageDialog(this,
                "Sai tên đăng nhập hoặc mật khẩu!",
                "Lỗi đăng nhập", JOptionPane.ERROR_MESSAGE);
    }

    private void handleRegister(JTabbedPane authTabs) {
        String username = regUsernameField.getText().trim();
        String password = new String(regPasswordField.getPassword());

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên đăng nhập không được để trống.", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                JOptionPane.showMessageDialog(this, "Tên đăng nhập đã tồn tại.", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        if (password.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mật khẩu không được để trống.", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String id = String.format("C%03d", nextCustomerId++);
        users.add(new Customer(id, username, password));
        regUsernameField.setText("");
        regPasswordField.setText("");

        JOptionPane.showMessageDialog(this,
                "Đăng ký thành công! Mã khách hàng của bạn là: " + id + "\nVui lòng đăng nhập.",
                "Thành công", JOptionPane.INFORMATION_MESSAGE);
        authTabs.setSelectedIndex(0);
        loginUsernameField.setText(username);
    }

    private void logout() {
        currentUser = null;
        mainCardLayout.show(mainContainer, "AUTH");
    }

    private void showChangePasswordDialog() {
        if (currentUser == null) return;
        JPasswordField oldPassField = new JPasswordField(15);
        JPasswordField newPassField = new JPasswordField(15);

        JPanel panel = new JPanel(new GridLayout(2, 2, 8, 8));
        panel.add(new JLabel("Mật khẩu hiện tại:"));
        panel.add(oldPassField);
        panel.add(new JLabel("Mật khẩu mới:"));
        panel.add(newPassField);

        int result = JOptionPane.showConfirmDialog(this, panel,
                "Đổi mật khẩu (Change Password)", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String oldPass = new String(oldPassField.getPassword());
            String newPass = new String(newPassField.getPassword());
            if (currentUser.changePassword(oldPass, newPass)) {
                JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Đổi mật khẩu thất bại. Kiểm tra lại mật khẩu hiện tại và mật khẩu mới không được rỗng.",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private Product findProduct(String id) {
        for (Product product : products) {
            if (product.getId().equalsIgnoreCase(id)) return product;
        }
        return null;
    }

    private void handleAddToCart() {
        int selectedRow = customerProductTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm trong bảng trước!", "Chú ý", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String productId = (String) customerProductModel.getValueAt(selectedRow, 0);
        Product product = findProduct(productId);
        if (product == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(cartQuantityField.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng là số nguyên hợp lệ!", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Customer customer = (Customer) currentUser;
        try {
            customer.getCart().addProduct(product, quantity);
            refreshCustomerViews();
            JOptionPane.showMessageDialog(this,
                    "Đã thêm " + quantity + " x " + product.getName() + " vào giỏ hàng!",
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                    "Không thể thêm vào giỏ: " + ex.getMessage(),
                    "Lỗi số lượng / tồn kho", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleCheckout() {
        Customer customer = (Customer) currentUser;
        Cart cart = customer.getCart();
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Giỏ hàng của bạn đang trống!", "Chú ý", JOptionPane.WARNING_MESSAGE);
            return;
        }

        for (CartItem item : cart.getItems()) {
            if (item.getQuantity() > item.getProduct().getStock()) {
                JOptionPane.showMessageDialog(this,
                        "Không đủ tồn kho cho sản phẩm: " + item.getProduct().getName(),
                        "Lỗi tồn kho", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        String method = (String) paymentMethodCombo.getSelectedItem();
        String orderId = String.format("O%03d", nextOrderId++);
        Order order = new Order(orderId, customer.getId(), cart, method);

        for (CartItem item : cart.getItems()) {
            item.getProduct().reduceStock(item.getQuantity());
        }

        if ("Bank transfer (demo)".equals(method)) {
            order.getPayment().processPayment();
        }

        customer.addOrder(order);
        cart.clear();
        refreshCustomerViews();

        JOptionPane.showMessageDialog(this,
                "Đặt hàng thành công!\nMã đơn hàng: " + order.getId()
                        + "\nTổng tiền: " + String.format("%,.0f VND", order.getPayment().getAmount())
                        + "\nTrạng thái: " + (order.getPayment().isPaid() ? "PAID" : "UNPAID"),
                "Hoàn tất đặt hàng", JOptionPane.INFORMATION_MESSAGE);
        customerTabs.setSelectedIndex(2);
    }

    private void handleAdminAddProduct() {
        String name = prodNameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên sản phẩm không được để trống.", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double price;
        int stock;
        try {
            price = Double.parseDouble(prodPriceField.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Giá sản phẩm phải là một số hợp lệ!", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            stock = Integer.parseInt(prodStockField.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số lượng tồn kho phải là số nguyên hợp lệ!", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (price <= 0 || stock < 0) {
            JOptionPane.showMessageDialog(this, "Giá phải > 0 và Tồn kho không được âm.", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String id = String.format("P%03d", nextProductId++);
        products.add(new Product(id, name, price, stock));
        prodNameField.setText("");
        prodPriceField.setText("");
        prodStockField.setText("");
        refreshAdminViews();

        JOptionPane.showMessageDialog(this, "Đã thêm sản phẩm mới: " + id + " - " + name, "Thành công", JOptionPane.INFORMATION_MESSAGE);
    }

    private void refreshCustomerViews() {
        // Products table
        customerProductModel.setRowCount(0);
        for (Product p : products) {
            customerProductModel.addRow(new Object[]{
                    p.getId(), p.getName(), String.format("%,.0f", p.getPrice()), p.getStock()
            });
        }

        if (!(currentUser instanceof Customer)) return;
        Customer customer = (Customer) currentUser;

        // Cart table
        cartTableModel.setRowCount(0);
        for (CartItem item : customer.getCart().getItems()) {
            cartTableModel.addRow(new Object[]{
                    item.getProduct().getId(),
                    item.getProduct().getName(),
                    String.format("%,.0f", item.getProduct().getPrice()),
                    item.getQuantity(),
                    String.format("%,.0f", item.getSubtotal())
            });
        }
        cartTotalLabel.setText(String.format("Tổng cộng (Total): %,.0f VND", customer.getCart().getTotal()));

        // Order history table
        orderHistoryTableModel.setRowCount(0);
        orderItemsTableModel.setRowCount(0);
        for (Order order : customer.getOrderHistory()) {
            orderHistoryTableModel.addRow(new Object[]{
                    order.getId(),
                    order.getCustomerId(),
                    String.format("%,.0f", order.getPayment().getAmount()),
                    order.getPayment().getMethod(),
                    order.getPayment().isPaid() ? "PAID" : "UNPAID"
            });
        }
        if (orderHistoryTableModel.getRowCount() > 0) {
            orderHistoryTable.setRowSelectionInterval(orderHistoryTableModel.getRowCount() - 1, orderHistoryTableModel.getRowCount() - 1);
        }
    }

    private void updateSelectedOrderDetails() {
        orderItemsTableModel.setRowCount(0);
        if (!(currentUser instanceof Customer)) return;
        int row = orderHistoryTable.getSelectedRow();
        if (row < 0) return;

        Customer customer = (Customer) currentUser;
        if (row >= customer.getOrderHistory().size()) return;

        Order selectedOrder = customer.getOrderHistory().get(row);
        for (OrderItem item : selectedOrder.getItems()) {
            orderItemsTableModel.addRow(new Object[]{
                    item.getProductName(),
                    String.format("%,.0f", item.getPrice()),
                    item.getQuantity(),
                    String.format("%,.0f", item.getSubtotal())
            });
        }
    }

    private void refreshAdminViews() {
        adminProductModel.setRowCount(0);
        for (Product p : products) {
            adminProductModel.addRow(new Object[]{
                    p.getId(), p.getName(), String.format("%,.0f", p.getPrice()), p.getStock()
            });
        }
    }

    private JButton createStyledButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        return btn;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
        SwingUtilities.invokeLater(() -> {
            ShoppingSwingApp app = new ShoppingSwingApp();
            app.setVisible(true);
            app.setAlwaysOnTop(true);
            app.toFront();
            app.requestFocus();
            app.setAlwaysOnTop(false);
        });
    }
}

