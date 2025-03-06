# Barnes and Noble Book Purchasing Simulation

[![CI](https://github.com/SamSamiSz/Assignment6_Code/actions/workflows/SE333_CI.yml/badge.svg)](https://github.com/SamSamiSz/Assignment6_Code/actions/workflows/SE333_CI.yml)

This project simulates a book purchasing process using the `BarnesAndNoble` class, developed as part of the
SE-333 Software Testing Assignment 6. The assignment focuses on both specification-based (black-box) and
structural-based (white-box) testing. Additionally, this project is integrated with CI/CD via GitHub Actions
to automate builds, run tests, perform static analysis, and generate coverage reports.

## Project Overview

The simulation demonstrates a simplified book purchasing workflow:
- **Book Retrieval:** Uses a `BookDatabase` to fetch book details.
- **Purchase Processing:** Uses a `BuyBookProcess` to simulate buying books.
- **Purchase Summary:** Calculates the total price and tracks any unavailable copies when the requested quantity
  exceeds available stock.

## Project Structure

*Describe your project structure here (e.g., folders, key classes, etc.)*

## Testing

For Part 1, tests have been written for the `BarnesAndNoble` class using JUnit. The tests include:

- **Specification-Based Tests:**
  - Verify that `getPriceForCart(null)` returns `null`.
  - Check that a single-item order with sufficient stock produces the correct total price.
  - Ensure that an order with insufficient stock correctly adjusts the total price and records unavailable copies.

- **Structural-Based Tests:**
  - Process multiple order items to verify that the method correctly aggregates total prices and handles each case according to the internal logic.
  - Branch Coverage

## Continuous Integration / Continuous Delivery (CI/CD)

This project uses GitHub Actions to automate the following:
- **Static Analysis:** Checkstyle is run during the Maven `validate` phase, generating a report (`target/checkstyle-result.xml`).
- **Testing:** Maven tests are executed to ensure the quality and correctness of the code.
- **Coverage Reporting:** Jacoco is used to generate a code coverage report (found in `target/site/jacoco`).

These steps are configured in the `.github/workflows/SE333_CI.yml` file. Each push to the `main` branch triggers this workflow automatically.

## How to Run the Tests

### Prerequisites
- Java JDK 11 or above
- Maven

### Build and Test
From the root directory of the project, run:

```bash
mvn clean test
