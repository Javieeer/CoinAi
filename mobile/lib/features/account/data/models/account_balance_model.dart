import '../../domain/entities/account_balance.dart';

class AccountBalanceModel extends AccountBalance {
  const AccountBalanceModel({
    required super.accountId,
    required super.accountName,
    required super.currentBalance,
    required super.totalIncome,
    required super.totalExpense,
  });

  factory AccountBalanceModel.fromJson(
    Map<String, dynamic> json,
  ) {
    return AccountBalanceModel(
      accountId: json['accountId'] as String,
      accountName: json['accountName'] as String,
      currentBalance:
          (json['currentBalance'] as num).toDouble(),
      totalIncome:
          (json['totalIncome'] as num).toDouble(),
      totalExpense:
          (json['totalExpense'] as num).toDouble(),
    );
  }
}