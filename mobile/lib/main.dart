import 'package:flutter/material.dart';

import 'core/router/app_router.dart';
import 'core/theme/app_theme.dart';

void main() {
  runApp(const CoinAI());
}

class CoinAI extends StatelessWidget {
  const CoinAI({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp.router(
      debugShowCheckedModeBanner: false,
      title: 'CoinAI',
      theme: AppTheme.light,
      routerConfig: AppRouter.router,
    );
  }
}