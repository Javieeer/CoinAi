import '../storage/token_storage.dart';
import 'auth_session.dart';

class SessionService {
  const SessionService();

  Future<void> saveSession(
    AuthSession session,
  ) async {
    await TokenStorage.saveTokens(
      accessToken: session.accessToken,
      refreshToken: session.refreshToken,
      tokenType: session.tokenType,
      expiresIn: session.expiresIn,
    );
  }

  Future<AuthSession?> getSession() async {
    final accessToken =
        await TokenStorage.getAccessToken();

    final refreshToken =
        await TokenStorage.getRefreshToken();

    final tokenType =
        await TokenStorage.getTokenType();

    final expiresIn =
        await TokenStorage.getExpiresIn();

    if (accessToken == null ||
        refreshToken == null ||
        tokenType == null ||
        expiresIn == null) {
      return null;
    }

    return AuthSession(
      accessToken: accessToken,
      refreshToken: refreshToken,
      tokenType: tokenType,
      expiresIn: expiresIn,
    );
  }

  Future<bool> hasSession() {
    return TokenStorage.hasSession();
  }

  Future<void> clearSession() {
    return TokenStorage.clear();
  }
}