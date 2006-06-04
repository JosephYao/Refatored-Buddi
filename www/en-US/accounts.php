<? include "./inc/header.php" ?>

<h1>Accounts</h1>

<p>
Accounts are the sources where you can transfer money to or from.  They can include things such as a chequing account, a savings account, credit cards, or even your wallet!
</p>

<p>
There are two main kinds of accounts: Credit and Debit (an accountant might call these liability and asset).  Credit accounts, such as a credit card, are accounts which you owe money to; Debit accounts on the other hand, are accounts in which you have money.
</p>

<p>
As long as the balances for the different accounts are positive, credit accounts are red, and debit accounts are black.  (Note that if you owe money on a debit account, it will turn red; likewise, if you pay off all the money on a credit account, it will turn black.)
</p>

<p>
In the following screen shot, you can see two accounts: a chequing account with $100 in it, and a credit card with $100 owing on it.
</p>

<img src='./img/accounts/before.png' alt="Accounts window before adding another account">

<p>
To create a new account, click on the 'New' button, when in the accounts window.  You can specify the account name, the starting balance, and the account type (such as Chequing, Credit Card, or Cash):
</p>

<img src='./img/accounts/add.png' alt="Adding a cash account">

<p>
Note that after the new account has been added, the net worth value at the bottom of the screen has been updated.  Net worth is the sum of the balance of all your accounts.
</p>
<img src='./img/accounts/after.png' alt="Accounts window after adding another account">

<p>
If you created an account in error, you can delete it.  If there are any transactions in it, you can only mark it as deleted; the information will still stay in your data file.  (Depending on your <a href='./preferences.php'>preferences</a>, you may see the deleted account as an account with a line through it, or it may disappear from the screen altogether).
</p>



<? include "./inc/footer.php" ?>
