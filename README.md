# Concurrent Banking Transaction System

A small Java console simulation of several users depositing to and withdrawing from one shared bank account at the same time.

## Requirements

- Java Development Kit (JDK) 8 or newer
- `java` and `javac` available on `PATH`

## Run on Windows

Double-click `Run Project.bat`. It compiles the source into the `out` folder and starts the program.

The program asks for an opening balance in INR, number of users (2 to 10), transactions per user, and amount per transaction in INR. Odd-numbered users deposit; even-numbered users withdraw. A withdrawal is declined if funds are insufficient. Transaction message order can vary because the threads run concurrently.

## Main Java concepts

- `BankAccount` is the shared resource.
- `deposit`, `withdraw`, and `getBalance` are synchronized methods that protect the balance.
- `TransactionThread` is an abstract base class. `DepositThread` and `WithdrawalThread` extend it and override `makeTransaction`.
- `start()` runs each worker thread; `join()` makes the main program wait for all workers to finish.
- `Scanner` reads console input.

## Scope

This is an in-memory learning simulation, not banking software. It uses `double` for simplicity and does not store transaction history.
