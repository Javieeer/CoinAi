class LoginResponse {
  final String accessToken;
  final String refreshToken;
  final String tokenType;
  final int expiresIn;

  const LoginResponse({
    required this.accessToken,
    required this.refreshToken,
    required this.tokenType,
    required this.expiresIn,
  });

  factory LoginResponse.fromJson(
    Map<String, dynamic> json,
  ) {
    return LoginResponse(
      accessToken: json['accessToken'],
      refreshToken: json['refreshToken'],
      tokenType: json['tokenType'],
      expiresIn: json['expiresIn'],
    );
  }
}