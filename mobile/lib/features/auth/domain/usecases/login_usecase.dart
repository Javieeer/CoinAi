import '../entities/login_credentials.dart';
import '../../data/models/login_response.dart';
import '../repositories/auth_repository.dart';

class LoginUseCase {

  final AuthRepository repository;

  const LoginUseCase(
    this.repository,
  );

  Future<LoginResponse> call(
    LoginCredentials credentials,
  ) {

    return repository.login(
      credentials,
    );

  }

}