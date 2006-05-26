<? include "./inc/header.php" ?>

<h1>Transactions</h1>

<p>
The transactions screen is where you will likely spend most of your time while using Buddi.  In it, you define money transfers, either between accounts (for instance, witdrawing $20 from your savings account) or to budget categories (for instance, spending $100 on groceries, or getting your paycheque for the month).
</p>

<p>
To enter the transactions screen, click on the account to want to transfer to (or from), and click 'Open' (or just double click on the account).  A window will open which looks like the following:
</p>

<img src='/img/transactions/components.png' alt="Transactions window, detailing the different parts of interest">

<p>
This screen contains quite a bit of data, so it is useful to describe each part of it.
</p>
<p>
First, there is the history of past transactions.  This is sorted in order of date, and details all the transactions which have gone either to or from this account.
</p>
<img src='/img/transactions/history.png' alt="Transactions window, detailing the transaction history">

<p>
Next there is the the date and amount fields.  Both of these are neccesary to record the transaction, and should be filled in.  The amount must be non-zero, and the date must be valid, in the format specified in the <a href='/preferences.php'>Preferences</a> window.
</p>

<img src='/img/transactions/date_and_amount.png' alt="Transactions window, detailing the date and amount fields">

<p>
The source and destination pulldowns contain all the accounts and categories currently in the system.  This setup was chosen to be more intuitive than a traditional accounting ledger: you can read it off as a sentence to think what accounts should go where.  "I want to transfer from &lt;Source&gt; to &lt;Destination&gt;."  Both of these have to be set to some value before you can record the transaction.
</p>

<img src='/img/transactions/to_and_from.png' alt="Transactions window, detailing the source and destination pulldowns">

<p>
The box in the bottom left corner of the screen is the transaction number.  This could be cheque number, reference ID, or whatever else you want.  It is not required to enter anything in this field if you don't want to.
</p>

<img src='/img/transactions/number.png' alt="Transactions window, detailing the transaction number field">

<p>
Finally, we have the description and memo fields.  The description field (the middle field on the bottom row) is required; this is the field where you should put the store name (if recording a purchase from a store), person name (if cashing a personal cheque, for instance), etc.  The memo field is not required, but you can enter additional details about the transaction in it.
</p>

<img src='/img/transactions/description_and_memo.png' alt="Transactions window, detailing the description and memo fields">

<p>
Once you are done, you can hit 'Record' (if creating a new transaction), or 'Update' (if you are editing an existing one) to save your changes.  If you want to cancel without saving, you can hit 'Clear' (if this is a new transaction), or 'New' (if you are editing an existing one).
</p>


<? include "./inc/footer.php" ?>