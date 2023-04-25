//By Shubin Yuan
//program for arduino to turn LED on and off
#define LED LED_BUILTIN
void setup() {
  Serial.begin(9600);
  pinMode(LED, OUTPUT); 
 }



//read data, from serial, 1 = off, 2 = on
void loop() {
  if(Serial.available()>0)
   {     
     //read data
      char data = Serial.read(); 

      //switch case for data
      switch(data)
      {
        //when input is 1
        case '1': 
        {
        // turn LED off
        digitalWrite(LED, HIGH);
        break;
        }

        // when in put is 2
        case '2': 
        {
        // turn LEO on
        digitalWrite(LED, LOW);
        break;
        }
        //nothing happen out of loop directly  
        default : break;
      }
   }
}