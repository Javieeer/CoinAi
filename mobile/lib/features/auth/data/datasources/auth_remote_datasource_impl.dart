import 'package:dio/dio.dart';

import '../models/login_request.dart';
import '../models/login_response.dart';
import 'auth_remote_datasource.dart';

class AuthRemoteDatasourceImpl
    implements AuthRemoteDatasource {

  final Dio dio;

  AuthRemoteDatasourceImpl(
    this.dio,
  );

  @override
  Future<LoginResponse> login(
    LoginRequest request,
  ) async {

    final response = await dio.post(
      '/auth/login',
      data: request.toJson(),
    );

    return LoginResponse.fromJson(
      response.data,
    );
  }
}