#include <Servo.h>
Servo servo;
int const potPin = A0;
int const locked = 160;
int const opened = 0;

void setup() {
  Serial.begin(9600); // set the baud rate
  Serial.println("Ready"); // print "Ready" once
  pinMode(LED_BUILTIN, OUTPUT);
  digitalWrite(LED_BUILTIN, LOW);
  servo.attach(9);
  servo.write(locked);
}

void openBox();
void reset();

void loop() {
  char inByte = ' ';
  if(Serial.available()){ // only send data back if data has been sent
    char inByte = Serial.read(); // read the incoming data
    if(inByte == '1'){
      Serial.println("1");
      openBox();
    }else if( inByte == '0') {
      Serial.println("0");
      reset();
    }
  }
  delay(100); // delay for 1/10 of a second
}

void reset() {
  digitalWrite(LED_BUILTIN, LOW);
  servo.write(locked);
}

void openBox() {
  digitalWrite(LED_BUILTIN, HIGH);
  servo.write(opened);
}
