# Barnes and Noble Book Purchasing Simulation

This project simulates a book purchasing process using the BarnesAndNoble class, it was developed as part of the 
SE-333 Software Testing assignment 6.
This assignment focuses on both specification-based (black-box) and structural-based (white-box) testing. 
The project also integrates CI/CD via GitHub Actions for automated builds, testing, static analysis, and 
coverage reporting.

## Project Overview

The simulation demonstrates a simplified book purchasing workflow:
- **Book Retrieval:** Uses a `BookDatabase` to fetch book details.
- **Purchase Processing:** Uses a `BuyBookProcess` to simulate buying books.
- **Purchase Summary:** Calculates the total price and tracks any unavailable copies when the requested quantity 
exceeds available stock.

## Project Structure


## Testing

For Part 1, tests have been written for the BarnesAndNoble class using JUnit. The tests include:

- **Specification-Based Tests:**
    - Verify that `getPriceForCart(null)` returns `null`.
    - Check that a single-item order with sufficient stock produces the correct total price.
    - Ensure that an order with insufficient stock correctly adjusts the total price and records unavailable copies.

- **Structural-Based Tests:**
    - Process multiple order items to verify that the method correctly aggregates total prices and handles each case according to the internal logic.
    - Branch Coverage 
## How to Run the Tests

### Prerequisites
- Java JDK 11 or above
- Maven

### Build and Test
From the root directory of the project, run:

```bash
mvn clean test


