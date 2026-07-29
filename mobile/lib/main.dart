import 'package:flutter/material.dart';

import 'core/theme/app_theme.dart';

void main() {
  runApp(const CoinAI());
}

class CoinAI extends StatelessWidget {
  const CoinAI({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'CoinAI',
      theme: AppTheme.light,
      home: const HomePage(),
    );
  }
}

class HomePage extends StatelessWidget {
  const HomePage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('CoinAI'),
      ),
      body: const Center(
        child: Text(
          'Bienvenido a CoinAI',
          style: TextStyle(
            fontSize: 24,
            fontWeight: FontWeight.bold,
          ),
        ),
      ),
    );
  }
}