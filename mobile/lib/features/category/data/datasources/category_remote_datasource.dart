import '../models/category_response.dart';

abstract class CategoryRemoteDatasource {
  Future<List<CategoryResponse>> findAll();
}