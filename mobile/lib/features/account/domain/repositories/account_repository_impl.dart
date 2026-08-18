import '../../domain/entities/account.dart';
import '../../domain/entities/account_balance.dart';
import '../../domain/repositories/account_repository.dart';
import '../../data/datasources/account_remote_datasource.dart';

class AccountRepositoryImpl
    implements AccountRepository {
  final AccountRemoteDatasource datasource;

  AccountRepositoryImpl({
    required this.datasource,
  });

  @override
  Future<List<Account>> getAccounts() {
    return datasource.getAccounts();
  }

  @override
  Future<AccountBalance> getAccountBalance(
    String accountId,
  ) {
    return datasource.getAccountBalance(
      accountId,
    );
  }
}