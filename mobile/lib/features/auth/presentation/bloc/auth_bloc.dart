import 'package:flutter_bloc/flutter_bloc.dart';

import '../../../../core/di/injection.dart';
import '../../domain/usecases/login_usecase.dart';
import 'auth_event.dart';
import 'auth_state.dart';

class AuthBloc extends Bloc<AuthEvent, AuthState> {

  final LoginUseCase loginUseCase =
      getIt<LoginUseCase>();

  AuthBloc()
      : super(
          const AuthInitial(),
        ) {

    on<LoginRequested>(
      _login,
    );

  }

  Future<void> _login(
    LoginRequested event,
    Emitter<AuthState> emit,
  ) async {

    emit(
      const AuthLoading(),
    );

    try {

      final response =
          await loginUseCase(
            event.credentials,
          );

      print(response.accessToken);

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