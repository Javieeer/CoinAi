import '../entities/account.dart';
import '../entities/account_balance.dart';

abstract class AccountRepository {
  Future<List<Account>> getAccounts();

  Future<AccountBalance> getAccountBalance(
    String accountId,
  );
}