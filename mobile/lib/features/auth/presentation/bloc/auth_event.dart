import '../../domain/entities/login_credentials.dart';

abstract class AuthEvent {
  const AuthEvent();
}

class LoginRequested extends AuthEvent {

  final LoginCredentials credentials;

  const LoginRequested(
    this.credentials,
  );

}