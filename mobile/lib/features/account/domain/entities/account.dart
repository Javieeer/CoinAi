class Account {
  final String id;
  final String name;
  final String type;
  final String currency;
  final bool archived;
  final DateTime createdAt;
  final DateTime updatedAt;

  const Account({
    required this.id,
    required this.name,
    required this.type,
    required this.currency,
    required this.archived,
    required this.createdAt,
    required this.updatedAt,
  });
}