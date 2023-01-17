
<h2 align="center">
Hardware Buttons Find Flutter
</h2>
 
 - [x] Android
 
 
## Overview

It is necessary to learn the feedback of buttons on different types of reading devices and take steps based on it. This plugin will help you. This code, written with Stream, always listens to the native code, returning the name of the button to you when the button is pressed. Based on the answer you get, write down the functions you envision.


--------
<img src="https://raw.githubusercontent.com/AlakbarHeyderov/hardware_buttons_find_flutter/main/example/assets/device.jpeg"  width="500" height="500" alt="logo" />

--------
## How to use it

```dart
class _MyAppState extends State<MyApp> {
  String? _latestHardwareButtonEvent;

  StreamSubscription<String>? _buttonSubscription;

  @override
  void initState() {
    super.initState();
    _buttonSubscription = HardwareButtons.buttonEvents?.listen((event) {
      setState(() {
        _latestHardwareButtonEvent = event.toString();
      });
    });
  }

  @override
  void dispose() {
    super.dispose();
    _buttonSubscription?.cancel();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Hardware buttons find'),
        ),
        body: Center(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: <Widget>[
              Text('Button name: $_latestHardwareButtonEvent\n'),
            ],
          ),
        ),
      ),
    );
  }
}
```

 


 
 
