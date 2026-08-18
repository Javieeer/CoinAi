import '../../data/models/category_response.dart';
import '../repositories/category_repository.dart';

class GetCategoriesUseCase {
  final CategoryRepository repository;

  GetCategoriesUseCase(
    this.repository,
  );

  Future<List<CategoryResponse>> call() {
    return repository.findAll();
  }
}