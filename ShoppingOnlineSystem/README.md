# ShoppingOnlineSystem - Milestone 4 (Java Console)

A small console-based online shopping application for practicing core Java implementation.

## Milestone 4 requirements covered

- **Java classes and OOP:** `User`, `Customer`, `Admin`, `Product`, `Cart`, `CartItem`, `Order`, `OrderItem`, and `Payment`.
- **Encapsulation:** fields are private and accessed through methods.
- **Inheritance and polymorphism:** `Customer` and `Admin` extend the abstract `User` class and implement `getRole()`.
- **Collections:** `ArrayList` stores users, products, cart items, and order history.
- **Exception handling:** `NumberFormatException` is caught when the user enters invalid numeric input; `IllegalArgumentException` is caught when a cart operation has invalid quantity or insufficient stock.
- **Runnable console application:** login, customer registration, product listing, cart, checkout, order history, password change, and admin product creation.

## Requirements

- JDK 8 or later
- Terminal / Command Prompt

## Compile and run

Open a terminal in the `ShoppingOnlineSystem` folder and run:

```bash
javac -encoding UTF-8 src/*.java
java -cp src ShoppingOnlineSystem
```

If your terminal is already inside the `src` folder, run:

```bash
javac -encoding UTF-8 *.java
java ShoppingOnlineSystem
```

## Demo accounts

| Role | Username | Password |
|---|---|---|
| Admin | `admin` | `admin123` |
| Customer | `quan` | `123456` |

You can register another customer from the main menu.

## Suggested manual tests

1. Enter letters at a menu that expects a number: the program should show a message and ask again.
2. Log in with an incorrect password: login should fail.
3. Add a quantity of `0` or a negative quantity to the cart: the program should reject it.
4. Try adding more items than the available stock: the program should reject it.
5. Log in as admin and add a product, then view the product list.
6. Log in as customer, place an order, and check the order history.

## Limitations

- Data is stored in memory and resets when the program exits; no database or file persistence is implemented.
- Payment is simulated; no real money is transferred.
- Passwords are stored as plain text for this beginner educational example and are not suitable for production use.
