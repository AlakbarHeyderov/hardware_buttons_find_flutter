import 'dart:async';

import 'package:flutter/material.dart';
import 'package:hardware_buttons_find_flutter/hardware_buttons_find_flutter_method_channel.dart'
    as HardwareButtons;

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String? _latestHardwareButtonEvent;

  StreamSubscription<String>? _volumeButtonSubscription;

  @override
  void initState() {
    super.initState();
    _volumeButtonSubscription = HardwareButtons.buttonEvents?.listen((event) {
      setState(() {
        _latestHardwareButtonEvent = event.toString();
      });
    });
  }

  @override
  void dispose() {
    super.dispose();
    _volumeButtonSubscription?.cancel();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: <Widget>[
              Text('Value: $_latestHardwareButtonEvent\n'),
            ],
          ),
        ),
      ),
    );
  }
}

