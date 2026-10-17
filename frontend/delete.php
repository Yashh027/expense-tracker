<?php
// delete.php
// Receives the id of an expense (from the Delete link) and asks Java to delete it.

include "config.php";

if (isset($_GET["id"]) && is_numeric($_GET["id"])) {
    callBackend("/delete?id=" . $_GET["id"]);
}

// Go back to the main page
header("Location: index.php");
exit();
?>
