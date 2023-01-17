import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:hardware_buttons_find_flutter/hardware_buttons_find_flutter_method_channel.dart';

void main() {
  const EventChannel channel = EventChannel('hardware_buttons_find_flutter');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.receiveBroadcastStream((EventChannel methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.receiveBroadcastStream(null);
  });
}
