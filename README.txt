PERSONAL EXPENSE TRACKER
BCA Sem 3 Integrated Project: OOP Java + Advanced Web Technology

FOLDER STRUCTURE
  backend/   Java code (the "brain" - saves and loads the data)
     Expense.java          one expense (one row)
     ExpenseManager.java   keeps the list and saves it to expenses.txt
     Server.java           small web server on port 8080

  frontend/  Web pages (what the user sees)
     index.php     main page (shows totals, form and table)
     add.php       adds an expense, then goes back to index.php
     delete.php    deletes an expense, then goes back to index.php
     config.php    address of the Java server + helper function
     style.css     colours and layout

WHAT YOU NEED
  - Java JDK 8 or newer (check with: javac -version)
  - PHP 7 or newer (check with: php -v)

HOW TO RUN
  1. Terminal 1 (Java backend):
       cd backend
       javac *.java
       java Server
     You should see: Expense Tracker backend running on http://localhost:8080
     Keep this window open.

  2. Terminal 2 (PHP server):
       cd frontend
       php -S localhost:8000

  3. Open http://localhost:8000 in your browser.

HOW THE PARTS TALK TO EACH OTHER
  Browser -> index.php / add.php / delete.php (PHP)
          -> Server.java (Java, port 8080) -> expenses.txt
