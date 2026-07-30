import 'package:flutter/material.dart';

class LoginFooter extends StatelessWidget {

  const LoginFooter({
    super.key,
  });

  @override
  Widget build(BuildContext context) {

    return Column(

      children: [

        TextButton(

          onPressed: () {},

          child: const Text(
            '¿Olvidaste tu contraseña?',
          ),

        ),

        TextButton(

          onPressed: () {},

          child: const Text(
            'Crear cuenta',
          ),

        ),

      ],

    );

  }

}