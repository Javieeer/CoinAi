class CategoryResponse {
  final String id;
  final String name;
  final String? icon;
  final String? color;
  final String movementType;
  final String origin;

  const CategoryResponse({
    required this.id,
    required this.name,
    this.icon,
    this.color,
    required this.movementType,
    required this.origin,
  });

  factory CategoryResponse.fromJson(
    Map<String, dynamic> json,
  ) {
    return CategoryResponse(
      id: json['id'] as String,
      name: json['name'] as String,
      icon: json['icon'] as String?,
      color: json['color'] as String?,
      movementType: json['movementType'] as String,
      origin: json['origin'] as String,
    );
  }
}