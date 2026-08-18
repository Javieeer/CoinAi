import 'package:flutter_bloc/flutter_bloc.dart';

import '../../../../core/auth/auth_session.dart';
import '../../../../core/auth/session_service.dart';
import '../../../../core/di/injection.dart';
import '../../domain/entities/login_credentials.dart';
import '../../domain/usecases/login_usecase.dart';
import 'auth_event.dart';
import 'auth_state.dart';

class AuthBloc extends Bloc<AuthEvent, AuthState> {
  final LoginUseCase loginUseCase =
      getIt<LoginUseCase>();

  final SessionService sessionService =
      getIt<SessionService>();

  AuthBloc()
      : super(
          const AuthInitial(),
        ) {
    on<AppStarted>(_onAppStarted);
    on<LoginRequested>(_onLoginRequested);
    on<LogoutRequested>(_onLogoutRequested);
  }

  Future<void> _onAppStarted(
    AppStarted event,
    Emitter<AuthState> emit,
  ) async {
    emit(
      const AuthCheckingSession(),
    );

    try {
      final hasSession =
          await sessionService.hasSession();

      if (hasSession) {
        emit(
          const AuthAuthenticated(),
        );
      } else {
        emit(
          const AuthUnauthenticated(),
        );
      }
    } catch (e) {
      emit(
        const AuthUnauthenticated(),
      );
    }
  }

  Future<void> _onLoginRequested(
    LoginRequested event,
    Emitter<AuthState> emit,
  ) async {
    emit(
      const AuthLoading(),
    );

    try {
      final credentials = LoginCredentials(
        email: event.email,
        password: event.password,
      );

      final response = await loginUseCase(credentials);

      final session = AuthSession(
        accessToken: response.accessToken,
        refreshToken: response.refreshToken,
        tokenType: response.tokenType,
        expiresIn: response.expiresIn,
      );

      await sessionService.saveSession(
        session,
      );

      emit(
        const AuthAuthenticated(),
      );
    } catch (e) {
      emit(
        AuthFailure(
          e.toString(),
        ),
      );
    }
  }

  Future<void> _onLogoutRequested(
    LogoutRequested event,
    Emitter<AuthState> emit,
  ) async {
    await sessionService.clearSession();

    emit(
      const AuthUnauthenticated(),
    );
  }
}