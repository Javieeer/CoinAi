import '../../data/models/category_response.dart';

abstract class CategoryRepository {
  Future<List<CategoryResponse>> findAll();
}