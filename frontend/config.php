<?php
// config.php
// This file holds the address of the Java backend,
// and a function that asks the backend for something.

$BACKEND_URL = "http://localhost:8080";

// Sends a request (for example "/list") to Java and returns the text it sends back.
// Returns false if the Java backend is not running.
function callBackend($path) {
    global $BACKEND_URL;
    $reply = @file_get_contents($BACKEND_URL . $path);
    return $reply;
}
?>
