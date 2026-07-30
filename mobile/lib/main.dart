import 'package:flutter/material.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';

import 'core/router/app_router.dart';
import 'core/theme/app_theme.dart';
import 'core/di/injection.dart';

Future<void> main() async {

  WidgetsFlutterBinding.ensureInitialized();
  await dotenv.load(fileName: ".env");
  await configureDependencies();
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