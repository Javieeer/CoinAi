class AccountBalance {
  final String accountId;
  final String accountName;
  final double currentBalance;
  final double totalIncome;
  final double totalExpense;

  const AccountBalance({
    required this.accountId,
    required this.accountName,
    required this.currentBalance,
    required this.totalIncome,
    required this.totalExpense,
  });
}