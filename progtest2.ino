#define LOCK LED_BUILTIN
#define senRIP 2

int senRIP_state = 0;
int senRIP_stored = 0;
int connectionState = 0;

unsigned long previousTime;
unsigned long timeNow;
unsigned long DelayTime = 5000;
char dataTransmit = 0b00000000;

void setup() {
  pinMode(LOCK, OUTPUT); 
  pinMode(senRIP, INPUT);
  Serial.begin(9600);
}

int sensorDelay(){
  if(senRIP_state == 1){
    if(timeNow - previousTime > DelayTime){
      return 1;
    }else{
      return 0;
    }
  }else{
    return 1;
  }
}
void readSensorInput(){
  int delayChecker = sensorDelay();
  if (delayChecker == 1){
    int current_senRIP_state = digitalRead(senRIP);
    if(current_senRIP_state == HIGH){
      if(senRIP_state == 0){
        senRIP_state = 1;
        dataTransmit = dataTransmit | 0b00000010;
        senRIP_stored = 1;
      }
      previousTime = millis();
    }else{
      if(senRIP_state == 1){
        senRIP_state = 0;        
        dataTransmit = dataTransmit & 0b11111101;      
      }

    }
  }
}

void readUserInput(){
    if(Serial.available()>0)
   {     
      char data= Serial.read(); 
      switch(data)
      {
        case '1': 
        {
        digitalWrite(LOCK, HIGH);
        dataTransmit = dataTransmit | 0b00000001;  
        break;
        }
        case '2': 
        {
        digitalWrite(LOCK, LOW);
        dataTransmit = dataTransmit & 0b11111110;  
        break;
        }
        case '3':
        {
          senRIP_state == 0;
        }
        case '4':
        {
          Serial.println(dataTransmit, HEX);
        }
        default : break;
      }
    }
}

void loop() {
  timeNow = millis();
  readUserInput();
  readSensorInput();
  }