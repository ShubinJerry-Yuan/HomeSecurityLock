package org.appinventor;
import com.google.appinventor.components.runtime.HandlesEventDispatching;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.Button;
import com.google.appinventor.components.runtime.ListPicker;
import com.google.appinventor.components.runtime.HorizontalArrangement;
import com.google.appinventor.components.runtime.Label;
import com.google.appinventor.components.runtime.BluetoothClient;
import com.google.appinventor.components.runtime.Clock;
import com.google.appinventor.components.runtime.util.YailList;
class Screen1 extends Form implements HandlesEventDispatching {
  //MY code is here
  //Button1 for on
  private Button Button1;

  //button2 for off
  private Button Button2;

  //listpicker to pick list
  private ListPicker ListPicker1;

  //horizontal Arrangment
  private HorizontalArrangement HorizontalArrangement1;

  //label1 use to display did bluetooth connected
  private Label Label1;

  //label2 use to display LED state
  //it change the value when it recive from serial moniter
  private HorizontalArrangement HorizontalArrangement2;
  private Label Label2;

  // need a blutoothClient2;
  private BluetoothClient BluetoothClient1;

  // and two clock, clock 1 use for check connected
  private Clock Clock1;

  //clock two use fo check for 
  private Clock Clock2;
  
  //initialize all needed button, values
  // this is starting initialize 
   protected void $define() {

    //app Name
    this.AppName("Lock");

    //first screen
    this.Title("Screen1");

    //button1
    Button1 = new Button(this);
    Button1.Text("ON");

    //button2
    Button2 = new Button(this);
    Button2.Text("Off");

    //list
    ListPicker1 = new ListPicker(this);
    ListPicker1.Text("BlutoothList");

    //label1
    HorizontalArrangement1 = new HorizontalArrangement(this);
    Label1 = new Label(HorizontalArrangement1);
    Label1.Width(100);
    Label1.Text("NOT connected");

    //label2
    HorizontalArrangement2 = new HorizontalArrangement(this);
    Label2 = new Label(HorizontalArrangement2);
    Label2.Text("NOT detected");

    //bluetooth start
    BluetoothClient1 = new BluetoothClient(this);

    //clock start
    Clock1 = new Clock(this);
    Clock2 = new Clock(this);
  }
    // before picking event, after Picking event
    EventDispatcher.registerEventForDelegation(this, "BeforePickingEvent", "BeforePicking" );
    EventDispatcher.registerEventForDelegation(this, "AfterPickingEvent", "AfterPicking" );
    EventDispatcher.registerEventForDelegation(this, "TimerEvent", "Timer" );
    EventDispatcher.registerEventForDelegation(this, "ClickEvent", "Click" );

  // use boolean to detect state
  public boolean dispatchEvent(Component component, String componentName, String eventName, Object[] params){
    if( component.equals(ListPicker1) && eventName.equals("BeforePicking") ){
      ListPicker1BeforePicking();
      return true;
    }
    if( component.equals(ListPicker1) && eventName.equals("AfterPicking") ){
      ListPicker1AfterPicking();
      return true;
    }
    if( component.equals(Clock1) && eventName.equals("Timer") ){
      Clock1Timer();
      return true;
    }
    if( component.equals(Button1) && eventName.equals("Click") ){
      Button1Click();
      return true;
    }
    if( component.equals(Button2) && eventName.equals("Click") ){
      Button2Click();
      return true;
    }
    return false;
  }

  //picklist generatonation
  public void ListPicker1BeforePicking(){
    ListPicker1.Elements(YailList.makeList(BluetoothClient1.AddressesAndNames()));
  }

  //after pick, connections
  public void ListPicker1AfterPicking(){
    if(BluetoothClient1.Connect(String.valueOf(ListPicker1.BackgroundColor()))){
      ListPicker1.Elements(YailList.makeList(BluetoothClient1.AddressesAndNames()));
    }
  }

  // timer, check connections
  public void Clock1Timer(){
    if(BluetoothClient1.IsConnected()){
      Label1.Text("Connected");
    }
    else {
      Label1.Text("Not Connected");
    }
  }

  //button click send back
  public void Button1Click(){
    BluetoothClient1.SendText(String.valueOf(1));
  }

  //but clikc send back.
  public void Button2Click(){
    BluetoothClient1.SendText(String.valueOf(2));
  }
}