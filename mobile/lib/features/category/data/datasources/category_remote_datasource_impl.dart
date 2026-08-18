import 'package:dio/dio.dart';

import '../models/category_response.dart';
import 'category_remote_datasource.dart';

class CategoryRemoteDatasourceImpl
    implements CategoryRemoteDatasource {
  final Dio dio;

  CategoryRemoteDatasourceImpl(
    this.dio,
  );

  @override
  Future<List<CategoryResponse>> findAll() async {
    final response = await dio.get(
      '/categories',
    );

    final data = response.data as List<dynamic>;

    return data
        .map(
          (json) => CategoryResponse.fromJson(
            json as Map<String, dynamic>,
          ),
        )
        .toList();
  }
}