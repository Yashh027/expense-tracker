<?php
// index.php
// The main page. It asks Java for the expenses, works out the totals,
// and then shows everything in the HTML below.

include "config.php";

// The categories shown in the dropdown list
$categories = array("Food", "Travel", "Shopping", "Bills", "Education", "Other");

// Step 1: ask Java for all the expenses (one per line)
$listText = callBackend("/list");
$backendDown = ($listText === false);

// Step 2: turn the text into a PHP array, and work out the totals
$expenses = array();        // each item is one expense
$total = 0;                 // total of all expenses
$categoryTotals = array();  // total for each category

if (!$backendDown) {
    $lines = explode("\n", trim($listText));
    foreach ($lines as $line) {
        if (trim($line) == "") {
            continue;   // skip empty lines
        }
        // $parts[0]=id, [1]=date, [2]=category, [3]=amount, [4]=description
        $parts = explode("|", $line, 5);
        $expenses[] = $parts;

        $amount = (float)$parts[3];
        $total = $total + $amount;

        $cat = $parts[2];
        if (!isset($categoryTotals[$cat])) {
            $categoryTotals[$cat] = 0;
        }
        $categoryTotals[$cat] = $categoryTotals[$cat] + $amount;
    }
}
?>
<!DOCTYPE html>
<html>
<head>
    <title>Personal Expense Tracker</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>

<div class="header">
    <h1>Personal Expense Tracker</h1>
    <p>BCA Sem 3 Integrated Project: OOP Java + Advanced Web Technology</p>
</div>

<div class="container">

<?php if ($backendDown) { ?>

    <div class="error">
        The Java backend is not running. Start it first (see the README file).
    </div>

<?php } else { ?>

    <!-- Box 1: total spent -->
    <div class="box">
        <h2>Total Spent</h2>
        <p class="total">Rs. <?php echo number_format($total, 2); ?></p>
    </div>

    <!-- Box 2: form to add a new expense -->
    <div class="box">
        <h2>Add New Expense</h2>
        <form action="add.php" method="post">
            <label>Date</label>
            <input type="date" name="date" value="<?php echo date('Y-m-d'); ?>" required>

            <label>Category</label>
            <select name="category">
                <?php foreach ($categories as $c) { ?>
                    <option><?php echo $c; ?></option>
                <?php } ?>
            </select>

            <label>Amount (Rs.)</label>
            <input type="number" name="amount" step="0.01" min="1" required>

            <label>Description</label>
            <input type="text" name="description" placeholder="e.g. Lunch at canteen">

            <button type="submit">Add Expense</button>
        </form>
    </div>

    <!-- Box 3: total for each category -->
    <div class="box">
        <h2>Category Summary</h2>
        <table>
            <tr><th>Category</th><th>Total (Rs.)</th></tr>
            <?php foreach ($categoryTotals as $name => $sum) { ?>
                <tr>
                    <td><?php echo htmlspecialchars($name); ?></td>
                    <td><?php echo number_format($sum, 2); ?></td>
                </tr>
            <?php } ?>
        </table>
    </div>

    <!-- Box 4: all expenses, with a delete link on each row -->
    <div class="box">
        <h2>All Expenses</h2>
        <table>
            <tr>
                <th>ID</th><th>Date</th><th>Category</th>
                <th>Amount (Rs.)</th><th>Description</th><th>Action</th>
            </tr>
            <?php foreach ($expenses as $e) { ?>
                <tr>
                    <td><?php echo $e[0]; ?></td>
                    <td><?php echo htmlspecialchars($e[1]); ?></td>
                    <td><?php echo htmlspecialchars($e[2]); ?></td>
                    <td><?php echo number_format((float)$e[3], 2); ?></td>
                    <td><?php echo htmlspecialchars($e[4]); ?></td>
                    <td>
                        <a class="del" href="delete.php?id=<?php echo $e[0]; ?>"
                           onclick="return confirm('Delete this expense?');">Delete</a>
                    </td>
                </tr>
            <?php } ?>
        </table>
    </div>

<?php } ?>

</div>
</body>
</html>
