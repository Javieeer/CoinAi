import 'package:flutter/material.dart';

class LoginForm extends StatefulWidget {
  final TextEditingController emailController;
  final TextEditingController passwordController;

  final FocusNode emailFocusNode;
  final FocusNode passwordFocusNode;

  final VoidCallback onSubmit;

  const LoginForm({
    super.key,
    required this.emailController,
    required this.passwordController,
    required this.emailFocusNode,
    required this.passwordFocusNode,
    required this.onSubmit,
  });

  @override
  State<LoginForm> createState() => _LoginFormState();
}

class _LoginFormState extends State<LoginForm> {
  bool _obscurePassword = true;

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        TextFormField(
          controller: widget.emailController,
          focusNode: widget.emailFocusNode,
          keyboardType: TextInputType.emailAddress,
          textInputAction: TextInputAction.next,
          autocorrect: false,
          decoration: const InputDecoration(
            labelText: 'Correo electrónico',
            prefixIcon: Icon(
              Icons.email_outlined,
            ),
          ),
          validator: (value) {
            final email = value?.trim() ?? '';

            if (email.isEmpty) {
              return 'Ingresa tu correo electrónico';
            }

            final emailRegex = RegExp(
              r'^[^@\s]+@[^@\s]+\.[^@\s]+$',
            );

            if (!emailRegex.hasMatch(email)) {
              return 'Ingresa un correo electrónico válido';
            }

            return null;
          },
          onFieldSubmitted: (_) {
            FocusScope.of(context).requestFocus(
              widget.passwordFocusNode,
            );
          },
        ),

        const SizedBox(height: 20),

        TextFormField(
          controller: widget.passwordController,
          focusNode: widget.passwordFocusNode,
          obscureText: _obscurePassword,
          textInputAction: TextInputAction.done,
          autocorrect: false,
          decoration: InputDecoration(
            labelText: 'Contraseña',
            prefixIcon: const Icon(
              Icons.lock_outline,
            ),
            suffixIcon: IconButton(
              onPressed: () {
                setState(() {
                  _obscurePassword = !_obscurePassword;
                });
              },
              icon: Icon(
                _obscurePassword
                    ? Icons.visibility_outlined
                    : Icons.visibility_off_outlined,
              ),
            ),
          ),
          validator: (value) {
            final password = value ?? '';

            if (password.isEmpty) {
              return 'Ingresa tu contraseña';
            }

            return null;
          },
          onFieldSubmitted: (_) {
            widget.onSubmit();
          },
        ),
      ],
    );
  }
}