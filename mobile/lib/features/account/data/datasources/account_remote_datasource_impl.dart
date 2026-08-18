import 'package:dio/dio.dart';

import '../models/account_balance_model.dart';
import '../models/account_model.dart';
import 'account_remote_datasource.dart';

class AccountRemoteDatasourceImpl
    implements AccountRemoteDatasource {
  final Dio dio;

  AccountRemoteDatasourceImpl({
    required this.dio,
  });

  @override
  Future<List<AccountModel>> getAccounts() async {
    final response = await dio.get(
      '/accounts',
    );

    final data = response.data as List;

    return data
        .map(
          (json) => AccountModel.fromJson(
            json as Map<String, dynamic>,
          ),
        )
        .toList();
  }

  @override
  Future<AccountBalanceModel> getAccountBalance(
    String accountId,
  ) async {
    final response = await dio.get(
      '/accounts/$accountId/balance',
    );

    return AccountBalanceModel.fromJson(
      response.data as Map<String, dynamic>,
    );
  }
}