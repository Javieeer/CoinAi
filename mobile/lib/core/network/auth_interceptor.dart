import 'package:dio/dio.dart';

import '../auth/session_service.dart';

class AuthInterceptor extends Interceptor {
  final SessionService sessionService;

  AuthInterceptor(
    this.sessionService,
  );

  @override
  Future<void> onRequest(
    RequestOptions options,
    RequestInterceptorHandler handler,
  ) async {
    final session = await sessionService.getSession();

    if (session != null) {
      options.headers['Authorization'] =
          '${session.tokenType} ${session.accessToken}';
    }

    handler.next(options);
  }
}