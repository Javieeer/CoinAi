import '../entities/account_balance.dart';
import '../repositories/account_repository.dart';

class GetAccountBalanceUsecase {
  final AccountRepository repository;

  GetAccountBalanceUsecase({
    required this.repository,
  });

  Future<AccountBalance> call(
    String accountId,
  ) {
    return repository.getAccountBalance(
      accountId,
    );
  }
}