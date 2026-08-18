import 'package:dio/dio.dart';
import 'package:get_it/get_it.dart';

import '../auth/session_service.dart';
import '../network/dio_client.dart';

import '../../features/auth/data/datasources/auth_remote_datasource.dart';
import '../../features/auth/data/datasources/auth_remote_datasource_impl.dart';
import '../../features/auth/data/repositories/auth_repository_impl.dart';
import '../../features/auth/domain/repositories/auth_repository.dart';
import '../../features/auth/domain/usecases/login_usecase.dart';
import '../../features/category/data/datasources/category_remote_datasource.dart';
import '../../features/category/data/datasources/category_remote_datasource_impl.dart';
import '../../features/category/data/repositories/category_repository_impl.dart';
import '../../features/category/domain/repositories/category_repository.dart';
import '../../features/category/domain/usecases/get_categories_usecase.dart';

final GetIt getIt = GetIt.instance;

Future<void> configureDependencies() async {

  getIt.registerLazySingleton<SessionService>(
    () => const SessionService(),
  );

  getIt.registerLazySingleton<Dio>(
    () => DioClient.create(
      getIt<SessionService>(),
    ),
  );

  // Datasource
  getIt.registerLazySingleton<AuthRemoteDatasource>(
    () => AuthRemoteDatasourceImpl(
      getIt<Dio>(),
    ),
  );
  
  // Repository
  getIt.registerLazySingleton<AuthRepository>(
    () => AuthRepositoryImpl(
      getIt<AuthRemoteDatasource>(),
    ),
  );

  // UseCase
  getIt.registerLazySingleton<LoginUseCase>(
    () => LoginUseCase(
      getIt<AuthRepository>(),
    ),
  );

  getIt.registerLazySingleton<CategoryRemoteDatasource>(
    () => CategoryRemoteDatasourceImpl(
      getIt<Dio>(),
    ),
  );

  getIt.registerLazySingleton<CategoryRepository>(
    () => CategoryRepositoryImpl(
      getIt<CategoryRemoteDatasource>(),
    ),
  );

  getIt.registerLazySingleton<GetCategoriesUseCase>(
    () => GetCategoriesUseCase(
      getIt<CategoryRepository>(),
    ),
  );
}