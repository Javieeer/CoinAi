import 'package:flutter/material.dart';

class LoginForm extends StatelessWidget {

  const LoginForm({
    super.key,
  });

  @override
  Widget build(BuildContext context) {

    return const Column(

      children: [

        TextField(

          decoration: InputDecoration(
            labelText: 'Correo electrónico',
          ),

        ),

        SizedBox(height: 20),

        TextField(

          obscureText: true,

          decoration: InputDecoration(
            labelText: 'Contraseña',
          ),

        ),

      ],

    );

  }

}