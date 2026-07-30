import '../../domain/entities/login_credentials.dart';
import '../../domain/repositories/auth_repository.dart';
import '../datasources/auth_remote_datasource.dart';
import '../models/login_request.dart';
import '../models/login_response.dart';

class AuthRepositoryImpl
    implements AuthRepository {

  final AuthRemoteDatasource datasource;

  const AuthRepositoryImpl(
    this.datasource,
  );

  @override
  Future<LoginResponse> login(
    LoginCredentials credentials,
  ) {

    final request = LoginRequest(
      email: credentials.email,
      password: credentials.password,
    );

    return datasource.login(request);

  }

}