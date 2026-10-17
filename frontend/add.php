<?php
// add.php
// Receives the form data from index.php and sends it to Java.

include "config.php";

// Read the values that the form sent
$date = $_POST["date"];
$category = $_POST["category"];
$amount = $_POST["amount"];
$description = $_POST["description"];

// Only save if the amount is a number bigger than zero
if (is_numeric($amount) && $amount > 0) {
    // urlencode makes spaces and special symbols safe to put in a web address
    $path = "/add?date=" . urlencode($date)
          . "&category=" . urlencode($category)
          . "&amount=" . urlencode($amount)
          . "&description=" . urlencode($description);
    callBackend($path);
}

// Go back to the main page
header("Location: index.php");
exit();
?>
