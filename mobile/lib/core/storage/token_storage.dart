import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class TokenStorage {
  TokenStorage._();

  static const FlutterSecureStorage _storage =
      FlutterSecureStorage();

  static const String _accessTokenKey =
      'access_token';

  static const String _refreshTokenKey =
      'refresh_token';

  static const String _tokenTypeKey =
      'token_type';

  static const String _expiresInKey =
      'expires_in';

  static Future<void> saveTokens({
    required String accessToken,
    required String refreshToken,
    required String tokenType,
    required int expiresIn,
  }) async {
    await _storage.write(
      key: _accessTokenKey,
      value: accessToken,
    );

    await _storage.write(
      key: _refreshTokenKey,
      value: refreshToken,
    );

    await _storage.write(
      key: _tokenTypeKey,
      value: tokenType,
    );

    await _storage.write(
      key: _expiresInKey,
      value: expiresIn.toString(),
    );
  }

  static Future<String?> getAccessToken() async {
    return _storage.read(
      key: _accessTokenKey,
    );
  }

  static Future<String?> getRefreshToken() async {
    return _storage.read(
      key: _refreshTokenKey,
    );
  }

  static Future<String?> getTokenType() async {
    return _storage.read(
      key: _tokenTypeKey,
    );
  }

  static Future<int?> getExpiresIn() async {
    final value = await _storage.read(
      key: _expiresInKey,
    );

    if (value == null) {
      return null;
    }

    return int.tryParse(value);
  }

  static Future<bool> hasSession() async {
    final accessToken = await getAccessToken();

    return accessToken != null &&
        accessToken.isNotEmpty;
  }

  static Future<void> clear() async {
    await _storage.delete(
      key: _accessTokenKey,
    );

    await _storage.delete(
      key: _refreshTokenKey,
    );

    await _storage.delete(
      key: _tokenTypeKey,
    );

    await _storage.delete(
      key: _expiresInKey,
    );
  }
}