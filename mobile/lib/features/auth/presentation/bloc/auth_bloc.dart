import 'package:flutter_bloc/flutter_bloc.dart';

import '../../domain/entities/login_credentials.dart';
import '../../domain/usecases/login_usecase.dart';
import '../../../../core/di/injection.dart';
import '../../../../core/auth/auth_session.dart';
import '../../../../core/auth/session_service.dart';
import '../../../category/domain/usecases/get_categories_usecase.dart';
import 'auth_event.dart';
import 'auth_state.dart';

class AuthBloc extends Bloc<AuthEvent, AuthState> {
  final LoginUseCase loginUseCase = getIt<LoginUseCase>();
  final SessionService sessionService = getIt<SessionService>();

  AuthBloc(): super(const AuthInitial(),) {
    on<LoginRequested>(_login);
  }

  Future<void> _login(LoginRequested event, Emitter<AuthState> emit,) async {
    emit(
      const AuthLoading(),
    );

    try {
      final credentials = LoginCredentials(
        email: event.email,
        password: event.password,
      );

      final response = await loginUseCase(
        credentials,
      );

      final session = AuthSession(
        accessToken: response.accessToken,
        refreshToken: response.refreshToken,
        tokenType: response.tokenType,
        expiresIn: response.expiresIn,
      );

      await sessionService.saveSession(
        session,
      );

      /* TEMPORAL --------------*/
      final categories =
          await getIt<GetCategoriesUseCase>()();

      print(
        'Categorías recibidas: ${categories.length}',
      );

      /* ------------------- */

      emit(
        const AuthSuccess(),
      );
    } catch (e) {
      emit(
        AuthFailure(
          e.toString(),
        ),
      );
    }
  }
}