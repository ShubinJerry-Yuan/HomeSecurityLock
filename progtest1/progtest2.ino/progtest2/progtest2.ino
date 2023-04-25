//by shubin Yuna
//HomeSLock, read data from phone Lock App open or close the door. 
// read sensor send it to phone

#define LOCK 3 // pin 13 as output pin for lock
#define senRIP 2 // pin 2 as input pin for sensor

int senRIP_state = 0; // track sensor state
int senRIP_stored = 0;  // stored if bluetooth not connected but sensor detected

int connection_checker_prev = 0;
int connection_checker_now = 0;
int connection_state = 0;

unsigned long previousCheck; //previous deteted time
unsigned long checkperiod = 1000; // delay time

unsigned long previousTime; //previous deteted time
unsigned long timeNow; // current time
unsigned long DelayTime = 200; // delay time
char dataTransmit = 0b00000000;// data send

void connection_check(){
  if(timeNow - previousCheck > checkperiod){
    connection_state_update();
    previousCheck = timeNow;
  }
}


void connection_state_update(){
  if (connection_checker_prev == connection_checker_now){
    connection_state = 0;
    digitalWrite(LOCK, LOW);
    dataTransmit = dataTransmit & 0b11111110;  
  }else{
    connection_checker_prev = connection_checker_now;
    if (connection_checker_prev > 100000 ){
      connection_checker_prev = 0;
      connection_checker_now = 0;
    }
  }
}

//-------------------------------------------------------------------------------------
void setup() {
  pinMode(LOCK, OUTPUT); // lock output pin
  pinMode(senRIP, INPUT); // senRIP input pin
  Serial.begin(9600); //serial begin 9600
}

//-------------------------------------------------------------------------------------
//@return
//1, can keep read input from sensor
//0 stop read input from sensor 
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
//-------------------------------------------------------------------------------------
//read sensor input 
void readSensorInput(){

  //check can i read
  int delayChecker = sensorDelay();

  //if I can read
  if (delayChecker == 1){

    //read sensor input
    int current_senRIP_state = digitalRead(senRIP);
    if(current_senRIP_state == HIGH){
      //if detceted
      if(senRIP_state == 0){
        //change state
        senRIP_state = 1;
        //stored data for transmision, use binary or
        dataTransmit = dataTransmit | 0b00000010;
        if(connection_state == 0){
          senRIP_stored = 1;
        }
      }
      //previous time = current time
      previousTime = millis();
      //if noty 
    }else{
      if(senRIP_state == 1){
        senRIP_state = 0;        
        //stored the data use binary and 
        dataTransmit = dataTransmit & 0b11111101;      
      }

    }
  }
}

//-------------------------------------------------------------------------------------
//read input from user
void readUserInput(){
    if(Serial.available()>0)
   {     
      char data= Serial.read(); 
      switch(data)
      {
        //read 1
        case '1': 
        {
        digitalWrite(LOCK, HIGH);
        dataTransmit = dataTransmit | 0b00000001;  
        break;
        }
        //read 2
        case '2': 
        {
        digitalWrite(LOCK, LOW);
        dataTransmit = dataTransmit & 0b11111110;  
        break;
        }
        //read 3
        case '3':
        { 
        if(senRIP_stored == 1){
          dataTransmit = dataTransmit | 0b00000100;
        }else{
          dataTransmit = dataTransmit & 0b11111011;
        }
        senRIP_stored = 0;
        connection_state = 1;  
        connection_checker_now++;        
        break;
        }
        //read 4
        case '4':
        {
          connection_state = 1;  
          connection_checker_now++;
          Serial.print(dataTransmit, HEX);
        break;
        }
        default : break;
      }
    }
  //Serial.println(connection_state);
}
//-----------------------------------------------------------------------------
//program start:
void loop() {
  //current read
  timeNow = millis();
  //read user 
  readUserInput();
  //read sensor
  readSensorInput();
  connection_check();
  }