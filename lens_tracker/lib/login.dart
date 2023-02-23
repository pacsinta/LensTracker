import 'package:flutter/material.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:go_router/go_router.dart';

class Login extends StatefulWidget {
  const Login({Key? key}) : super(key: key);

  @override
  State<Login> createState() => _LoginState();
}

extension EmailValidator on String {
  bool isValidEmail() {
    return RegExp(
            r'^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$')
        .hasMatch(this);
  }
}

void showSnackBar(BuildContext context, String message) {
  final snackBar = SnackBar(content: Text(message));
  ScaffoldMessenger.of(context).showSnackBar(snackBar);
}

class _LoginState extends State<Login> {
  bool _isLogin = true;
  final _formKey = GlobalKey<FormState>();
  final emailController = TextEditingController();
  final pwdController = TextEditingController();
  bool _pwdVisible = false;
  bool _isLoading = false;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Form(
          key: _formKey,
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              if (_isLogin) ...[
                const Text('Login', style: TextStyle(fontSize: 30)),
              ] else ...[
                const Text('Register', style: TextStyle(fontSize: 30)),
              ],
              TextFormField(
                controller: emailController,
                validator: (value) {
                  if (value != null &&
                      value.isNotEmpty &&
                      value.isValidEmail()) {
                    return null;
                  } else {
                    return 'Please enter a valid email';
                  }
                },
                decoration: const InputDecoration(hintText: 'E-mail'),
                keyboardType: TextInputType.emailAddress,
              ),
              Row(
                children: [
                  Expanded(
                    child: TextFormField(
                      controller: pwdController,
                      validator: (value) {
                        if (value != null && value.isNotEmpty) {
                          if (value.length < 8) {
                            return 'Password must be at least 8 characters';
                          }
                          return null;
                        } else {
                          return 'Please enter a password';
                        }
                      },
                      decoration: InputDecoration(
                        hintText: 'Password',
                        suffixIcon: IconButton(
                          icon: Icon(
                            _pwdVisible
                                ? Icons.visibility
                                : Icons.visibility_off,
                          ),
                          onPressed: () {
                            setState(() {
                              _pwdVisible = !_pwdVisible;
                            });
                          },
                        ),
                      ),
                      obscureText: !_pwdVisible,
                    ),
                  ),
                ],
              ),
              const SizedBox(height: 20),
              FloatingActionButton(
                  onPressed: () async {
                    if (_formKey.currentState!.validate()) {
                      setState(() {
                        _isLoading = true;
                      });

                      /*try {
                        final credential = await FirebaseAuth.instance
                            .createUserWithEmailAndPassword(
                          email: emailController.text,
                          password: pwdController.text,
                        );

                        if(credential.user != null && context.mounted) {
                          context.go('/home');
                        }
                      } on FirebaseAuthException catch (e) {
                        if (e.code == 'weak-password') {
                          print('The password provided is too weak.');
                        } else if (e.code == 'email-already-in-use') {
                          print('The account already exists for that email.');
                        }
                      } catch (e) {
                        print(e);
                      }*/

                      setState(() {
                        _isLoading = false;
                      });
                    }
                  },
                  child: const Icon(Icons.arrow_forward)),
              if (_isLoading) ...[
                const CircularProgressIndicator(),
              ],
              TextButton(
                onPressed: () {
                  setState(() {
                    _isLogin = !_isLogin;
                  });
                },
                child: Text('Switch to ${_isLogin ? 'Register' : 'Login'}'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
