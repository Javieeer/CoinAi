import '../models/login_request.dart';
import '../models/login_response.dart';

abstract class AuthRemoteDatasource {
  Future<LoginResponse> login(
    LoginRequest request,
  );
}