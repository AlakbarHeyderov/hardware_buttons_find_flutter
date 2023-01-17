import 'dart:html';

String getKeyEventName(KeyEvent keyEvent) {

  return keyEvent.toString().split('.').last;
}