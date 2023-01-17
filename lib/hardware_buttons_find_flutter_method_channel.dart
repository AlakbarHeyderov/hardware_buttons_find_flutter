import 'dart:async';

import 'package:flutter/services.dart';
import 'package:hardware_buttons_find_flutter/key_events.dart';

const EventChannel _buttonEventChannel =
    EventChannel('hardware_buttons_find_flutter');

Stream<String>? _buttonEvents;

Stream<String>? get buttonEvents {
  _buttonEvents ??= _buttonEventChannel
      .receiveBroadcastStream()
      .map((dynamic event) => getKeyEventName(event) ?? 'null');
  return _buttonEvents;
}
