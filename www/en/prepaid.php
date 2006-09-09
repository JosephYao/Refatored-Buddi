<? include "./inc/header.php" ?>

<h1>Prepaid Liability Accounts</h1>

<p>
The theory behind this is that you can create a Prepaid Liability account, which acts a virtual account - each month you can 'pay' yourself a certain amount, which is put into the liability account.  This can accumulate over a number of months; when you spend that money, it is transferred out of the liability account and does not show up in your budget categories (since it has already been budgeted for each month when you paid yourself in the first place).
</p>

<p>
To set this up, you can do the following:
</p>

<ul>
<li>Create a new liability account.  For this example, we will call it Prepaid Auto Maintenance.
<br>
<img src='../img/prepaid/create_new_account.png' alt="Create a new Prepaid Liability account">
</li>

<li>Say that every month, you want to transfer $50 into this account.  To do this, make a transaction where the From dropdown is the liability account (since it is a credit account, the value increases as money is taken from it), and the To dropdown is whatever budget category you want (in this case, Auto Maintenance).  (Note: this is the perfect opportunity to use <a href="./scheduled.php">Scheduled Transactions</a>.)
<br>
<img src='../img/prepaid/save_money.png' alt="Put aside $50 for a rainy day">
</li>

<li>When your car finally breaks down and you need repairs, you can now use this prepaid account.  At this point, the prepaid account is the expense (i.e., the To dropdown), and your chequing / savings / whatever account (where the money actually was the whole time) is the From dropdown.  Since it is simply a transfer between accounts, and does not involve budget categories at all, there is nothing mentioned in the reports (the $50 each month has already shown up in them).
<br>
<img src='../img/prepaid/spend_money.png' alt="When your car breaks down, you can use the prepaid account to repair it">
</li>
</ul>

<? include "./inc/footer.php" ?>
