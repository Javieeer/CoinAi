import '../entities/login_credentials.dart';
import '../../data/models/login_response.dart';

abstract class AuthRepository {

  Future<LoginResponse> login(
    LoginCredentials credentials,
  );

}