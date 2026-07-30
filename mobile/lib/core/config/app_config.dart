import 'package:flutter_dotenv/flutter_dotenv.dart';

class AppConfig {
  AppConfig._();

  static String get apiUrl =>
      dotenv.env['API_URL'] ?? '';
}