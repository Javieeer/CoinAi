import '../models/account_balance_model.dart';
import '../models/account_model.dart';

abstract class AccountRemoteDatasource {
  Future<List<AccountModel>> getAccounts();

  Future<AccountBalanceModel> getAccountBalance(
    String accountId,
  );
}