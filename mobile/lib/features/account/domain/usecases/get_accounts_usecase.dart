import '../entities/account.dart';
import '../repositories/account_repository.dart';

class GetAccountsUsecase {
  final AccountRepository repository;

  GetAccountsUsecase({
    required this.repository,
  });

  Future<List<Account>> call() {
    return repository.getAccounts();
  }
}