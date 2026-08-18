import 'package:flutter/material.dart';

import '../widgets/balance_card.dart';
import '../widgets/dashboard_bottom_navigation.dart';
import '../widgets/dashboard_header.dart';

class DashboardPage extends StatefulWidget {
  const DashboardPage({
    super.key,
  });

  @override
  State<DashboardPage> createState() =>
      _DashboardPageState();
}

class _DashboardPageState
    extends State<DashboardPage> {
  int _currentIndex = 0;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: IndexedStack(
          index: _currentIndex,
          children: [
            _buildHome(),
            _buildPlaceholder('Movimientos'),
            _buildPlaceholder('Agregar movimiento'),
            _buildPlaceholder('Metas'),
            _buildPlaceholder('Perfil'),
          ],
        ),
      ),
      bottomNavigationBar:
          DashboardBottomNavigation(
        currentIndex: _currentIndex,
        onTap: (index) {
          setState(() {
            _currentIndex = index;
          });
        },
      ),
    );
  }

  Widget _buildHome() {
    return SingleChildScrollView(
      padding: const EdgeInsets.all(24),
      child: Column(
        crossAxisAlignment:
            CrossAxisAlignment.start,
        children: const [
          DashboardHeader(),

          SizedBox(height: 32),

          BalanceCard(),

          SizedBox(height: 32),

          Text(
            'Movimientos recientes',
            style: TextStyle(
              fontSize: 20,
              fontWeight: FontWeight.bold,
            ),
          ),

          SizedBox(height: 16),

          Center(
            child: Text(
              'No hay movimientos todavía',
              style: TextStyle(
                color: Colors.grey,
              ),
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildPlaceholder(
    String title,
  ) {
    return Center(
      child: Text(
        title,
        style: const TextStyle(
          fontSize: 24,
          fontWeight: FontWeight.bold,
        ),
      ),
    );
  }
}