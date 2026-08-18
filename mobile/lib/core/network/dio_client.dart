import 'package:dio/dio.dart';

import '../auth/session_service.dart';
import '../config/app_config.dart';
import 'auth_interceptor.dart';

class DioClient {
  DioClient._();

  static Dio create(
    SessionService sessionService,
  ) {
    final dio = Dio(
      BaseOptions(
        baseUrl: AppConfig.apiUrl,
        connectTimeout: const Duration(
          seconds: 10,
        ),
        receiveTimeout: const Duration(
          seconds: 10,
        ),
        sendTimeout: const Duration(
          seconds: 10,
        ),
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json',
        },
      ),
    );

    dio.interceptors.add(
      AuthInterceptor(
        sessionService,
      ),
    );

    return dio;
  }
}