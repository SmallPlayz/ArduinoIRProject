//www.elegoo.com
//2016.12.9

#include "IRremote.h"

int receiver = 11; // Signal Pin of IR receiver to Arduino Digital Pin 11

/*-----( Declare objects )-----*/
IRrecv irrecv(receiver);     // create instance of 'irrecv'
decode_results results;      // create instance of 'decode_results'

/*-----( Function )-----*/
void translateIR() // takes action based on IR code received

// describing Remote IR codes 

{

  switch(results.value)

  {
  case 0xFFA25D: Serial.println(""); break; //power
  case 0xFFE21D: Serial.println(""); break; //FUNC/STOP
  case 0xFF629D: Serial.println("  0"); break; //up
  case 0xFF22DD: Serial.println("      0");    break; //left
  case 0xFF02FD: Serial.println("          0");    break; //click
  case 0xFFC23D: Serial.println("        0");   break; //right
  case 0xFFE01F: Serial.println("              0");    break; //enter
  case 0xFFA857: Serial.println("    0");    break; //down
  case 0xFF906F: Serial.println("            0");    break; //space
  case 0xFF9867: Serial.println("");    break; //EQ
  case 0xFFB04F: Serial.println("");    break; //ST/REPT
  case 0xFF6897: Serial.println("");    break; //0
  case 0xFF30CF: Serial.println("");    break; //1
  case 0xFF18E7: Serial.println("");    break; //2
  case 0xFF7A85: Serial.println("");    break; //3
  case 0xFF10EF: Serial.println("");    break; //4
  case 0xFF38C7: Serial.println("");    break; //5
  case 0xFF5AA5: Serial.println("");    break; //6
  case 0xFF42BD: Serial.println("");    break; //7
  case 0xFF4AB5: Serial.println("");    break; //8
  case 0xFF52AD: Serial.println("");    break; //9
  case 0xFFFFFFFF: Serial.println("0");break;  //repeat

  default: 
    Serial.println(" other button : ");
    Serial.println(results.value);

  }// End Case

  delay(500); // Do not get immediate repeat


} //END translateIR
void setup()   /*----( SETUP: RUNS ONCE )----*/
{
  Serial.begin(9600);
  Serial.println("IR Receiver Button Decode"); 
  irrecv.enableIRIn(); // Start the receiver

}/*--(end setup )---*/


void loop()   /*----( LOOP: RUNS CONSTANTLY )----*/
{
  if (irrecv.decode(&results)) // have we received an IR signal?

  {
    translateIR(); 
    irrecv.resume(); // receive the next value
  }  
}/* --(end main loop )-- */
