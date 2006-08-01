<? include "./inc/header.php" ?>

<h1>Transactions</h1>

<p>
The transactions screen is where you will likely spend most of your time while using Buddi.  In it, you define money transfers, either between accounts (for instance, witdrawing $20 from your savings account) or to budget categories (for instance, spending $100 on groceries, or getting your paycheque for the month).
</p>

<p>
To enter the transactions screen, click on the account to want to transfer to (or from), and click 'Open' (or just double click on the account).  A window will open which looks like the following:
</p>

<img src='../img/transactions/components.png' alt="Transactions window, detailing the different parts of interest">

<p>
This screen contains quite a bit of data, so it is useful to describe each part of it.
</p>
<p>
First, in the center of the window, there is the history of past transactions.  This is sorted in order of date, and details all the transactions which have gone either to or from this account.
</p>

<p>
At the bottom of the window is the transaction edit panel.  This allows you to enter new transactions, or to edit existing ones.  To create a new one, you fill in the needed information and hit "Record".  To edit an existing one, click on the desired transaction in the transaction history section, modify what fields you want, and hit "Update".
</p>

<p>
Before you can successfully record a transaction, you must enter all of the needed fields.  These include the Date, Description, Amount (must be non-zero), and the To and From pulldowns.
</p>

<img src='../img/transactions/edit.png' alt="Transactions window, when editing a transaction">

<p>
The source and destination pulldowns contain all the accounts and categories currently in the system.  This setup was chosen to be more intuitive than a traditional accounting ledger: you can read it off as a sentence to think what accounts should go where.  "I want to transfer from &lt;Source&gt; to &lt;Destination&gt;."
</p>

<p>
The two optional fields, Number and Memo, can be used however you see fit.  The Number field was meant to include whatever unique transaction number could be assigned; this could be cheque number, transaction reference ID, or whatever else you want.  The memo is meant for including notes to yourself on the transaction.
</p>

<p>
As you probably noticed in the prior two screenshots, the buttons on the lower right of the transactions window change labels between "Clear / Record" and "New / Update", depending on if you are creating a new transaction or editing an existing one.  The buttons essentially function the same way regardless of the label; the text change is just for clarification on what exactly is happening.
</p>


<? include "./inc/footer.php" ?>
