import 'package:flutter/material.dart';

class LoginHeader extends StatelessWidget {

  const LoginHeader({
    super.key,
  });

  @override
  Widget build(BuildContext context) {

    return const Column(

      children: [

        FlutterLogo(
          size: 90,
        ),

        SizedBox(height: 20),

        Text(
          'CoinAI',
          style: TextStyle(
            fontSize: 32,
            fontWeight: FontWeight.bold,
          ),
        ),

        SizedBox(height: 8),

        Text(
          'Bienvenido nuevamente',
          style: TextStyle(
            color: Colors.grey,
          ),
        ),

      ],

    );

  }

}