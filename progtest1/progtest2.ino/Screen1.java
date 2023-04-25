//from templete
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

  //method
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

    //bluetooth
    BluetoothClient1 = new BluetoothClient(this);

    //clock
    Clock1 = new Clock(this);
    Clock2 = new Clock(this);
  }
  public boolean dispatchEvent(Component component, String componentName, String eventName, Object[] params){
    return false;
  }
}