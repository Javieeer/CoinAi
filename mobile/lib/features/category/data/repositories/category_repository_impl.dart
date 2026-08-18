import '../../domain/repositories/category_repository.dart';
import '../datasources/category_remote_datasource.dart';
import '../models/category_response.dart';

class CategoryRepositoryImpl
    implements CategoryRepository {
  final CategoryRemoteDatasource datasource;

  CategoryRepositoryImpl(
    this.datasource,
  );

  @override
  Future<List<CategoryResponse>> findAll() {
    return datasource.findAll();
  }
}