import processing.core.*;//this is the library that makes display very easy
import java.util.ArrayList;//Objects are stinky, arraylist is better
import javax.swing.*;

/*
To Do List:
make it so pressing tab cycles to the next text box
window resizing compatibility?
limit framerate?
convert to arraylists because arrays are finite in size (we will need it for output)
*/

public class testui extends PApplet{
    float tempstock[] = {0,0,0,0};
    float temptxt[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    double scaleFactor;
    ArrayList<Float> x = new ArrayList<Float>();//arraylists are used for output
    ArrayList<Float> y = new ArrayList<Float>();
    ArrayList<Float> w = new ArrayList<Float>();
    ArrayList<Float> l = new ArrayList<Float>();
    boolean output=false;//if the calculate button has been pressed this will be true and output will stay on screen until calc is clicked again
int rows=4;
int colorval=0;
Boolean grain[]= {false,false,false,false,false,false,false,false,false};//x9 false=horizontal grain[7]=stock grain[8]=t/f if grain direction matters
Boolean box[]= {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false};//x21
String	txt[]= {"5","5","1","5","5","1","5","5","1","5","5","1","5","5","1","5","5","1","5","5","1"};//x21
String stock[]= {"60","60","2","0.125"};
Float tstock[]={(float)0,(float)0,(float)0,(float)0};
Boolean Stockbox[]= {false,false,false,false};
int cooldown=0;//frames b4 another input is processed
int selectedboxcolor=250;
int i=0;
    @Override
    public void settings() {
        size(1200, 675);//sets window size (will effect other display elements) normally 1200/675
    }
    @Override
    public void draw() {
        background(50);
        fill(190);
        rect(0, 0, width/3, height);//left bar background
        textSize(20);	//prolly should determine this based on screen size but whatev
        //part display:
        for (i=0;i<rows;i++) {//i=# of current rows (buttons should change this)
        	fill(10);
            text("Part "+(i+1)+":",5,100+(40*i));//nice spot to display variables for debug
            if(box[0+(3*i)]) {
                fill(selectedboxcolor);
                rect(60,110+(40*i),70,-30);
                }else {
            	fill(200);
            	rect(60,110+(40*i),70,-30);
                }
            fill(10);
            text(txt[0+(3*i)], 60, 100+(40*i));
            if(box[1+(3*i)]) {
                fill(selectedboxcolor);
                rect(140,110+(40*i),70,-30);
                }else {
             	fill(200);
               	rect(140,110+(40*i),70,-30);
                }
           	fill(10);
           	text(txt[1+(3*i)], 140, 100+(40*i));
            if(box[2+(3*i)]) {
            	fill(selectedboxcolor);
                rect(220,110+(40*i),70,-30);
                }else {
                fill(200);
                rect(220,110+(40*i),70,-30);
                }
            fill(10);
           	text(txt[2+(3*i)], 220, 100+(40*i));
        }
        for (i=0;i<rows;i++) {//loop for grain direction boxes
            if(grain[i]) {
                fill(200);//vertical display
                rect(300,110+(40*i),70,-30);
                fill(10);
                rect(318,110+(40*i),10,-30);//off by .3333 (dont tell anyone)
                fill(10);
                rect(352,110+(40*i),-10,-30);//off by .3333 (dont tell anyone)
                }else {
            	fill(200);//horizontal display
            	rect(300,110+(40*i),70,-30);
            	fill(10);
            	rect(300,100+(40*i),70,-10);
                }
            //stock grain direction
            if(grain[7]) {
                fill(200);//vertical display
                rect(300,430,70,-30);
                fill(10);
                rect(318,430,10,-30);
                fill(10);
                rect(352,430,-10,-30);
                }else {
            	fill(200);//horizontal display
            	rect(300,430,70,-30);
            	fill(10);
            	rect(300,420,70,-10);
                }
        }
        //display for Stock
        fill(10);
        text("Stock:",5,420);
        if(Stockbox[0]) {
            fill(selectedboxcolor);
            rect(60,430,70,-30);
            }else {
        	fill(200);
        	rect(60,430,70,-30);
            }
        fill(10);
        text(stock[0], 60, 420);
        if(Stockbox[1]) {
            fill(selectedboxcolor);
            rect(140,430,70,-30);
            }else {
         	fill(200);
           	rect(140,430,70,-30);
            }
       	fill(10);
       	text(stock[1], 140, 420);
        if(Stockbox[2]) {
        	fill(selectedboxcolor);
            rect(220,430,70,-30);
            }else {
            fill(200);
            rect(220,430,70,-30);
            }
        fill(10);
       	text(stock[2], 220, 420);
        if(Stockbox[3]) {
            fill(selectedboxcolor);
            rect(60,470,70,-30);
        }else {
            fill(200);
            rect(60,470,70,-30);
        }
        fill(10);
        text(stock[3], 60, 460);// end of Stock display
       	//+ and - buttons for Parts
       	if(rows==7) {
       	fill(250,0,0);//make the + button red when you are at max rows
       	}else fill(200);
        rect(60,20,50,50);
        fill(10);
        rect(65,40,40,10);
       	fill(10);
       	rect(80,25,10,40);
       	if(rows==1) {
       	fill(250,0,0);//make the - button red when you are at 1 row
       	}else fill(200);
        rect(240,20,50,50);
        fill(10);
        rect(245,40,40,10);
        textSize(15);
       	fill(10);
       	text("Add/Remove Parts",120,50);
           //toggle grain button
        if(grain[8]) {
            fill(0,250,0);
        }else fill(250,0,0);//make the - button red when grain is off
        rect(300,20,50,50);
        textSize(12);
        fill(10);
        if(grain[8]) {
            text("Grain On", 300, 50);
        }else    text("Grain Off", 300, 50);
        //calculate button
        fill(200);
        rect(300,height-75,50,50);
        fill(10);
        text("Calculate", 300, height-50);
        if(output){//display for right side of screen
         //use stock info + arraylist values
colorval=360/rows;//fix this later with numpart instead of rows
            scaleFactor = (Math.min(1.0 * width*2/3 / tstock[0], 1.0 * height / tstock[1])) * 0.8;//stock scaling mult
fill(200);
rect((float)(width/3)+10,(float)10, (float)(tstock[0]*scaleFactor),(float)(tstock[1]*scaleFactor));// stock display
            colorMode(HSB, 360, 100, 100);//changing colormode for part display
for(i=0;i<x.size();i++){
       fill(colorval*i,100,100);
    rect((float)(x.get(i)*scaleFactor)+(width/3)+10,(float)(y.get(i)*scaleFactor)+10,(float)(w.get(i)*scaleFactor),(float)(l.get(i)*scaleFactor));//part display
}
            colorMode(RGB, 255, 255, 255);//fixing colormode after im done with hue stuff
        }
    	update();
    }
	public static void main(String[] args) {
		PApplet.main("testui");
    }
public void update() {
if(cooldown<1&&mousePressed){//check if they clicked on a text box or if they clicked off a text box
	cooldown=20;
	 for (i=0;i<rows;i++) {//mouse collision for each Part box
         if(mouseX>60&&mouseX<130&&mouseY<(110+(40*i))&&mouseY>(80+(40*i))) {
        	 box[0+(i*3)]=true;	
	 } else box[0+(i*3)]=false;
         if(mouseX>140&&mouseX<210&&mouseY<(110+(40*i))&&mouseY>(80+(40*i))) {
        	 box[1+(i*3)]=true;	
	 } else box[1+(i*3)]=false;
         if(mouseX>220&&mouseX<290&&mouseY<(110+(40*i))&&mouseY>(80+(40*i))) {
        	 box[2+(i*3)]=true;	
	 } else box[2+(i*3)]=false;
         //check for grain direction
         if(mouseX>300&&mouseX<370&&mouseY<(110+(40*i))&&mouseY>(80+(40*i))) grain[i]= !grain[i];//flip bool
     }
	 //mouse collision for each Stock box
	 if(mouseX>60&&mouseX<130&&mouseY<430&&mouseY>400) {
    	 Stockbox[0]=true;	
 } else Stockbox[0]=false;
	 if(mouseX>140&&mouseX<210&&mouseY<430&&mouseY>400) {
    	 Stockbox[1]=true;	
 } else Stockbox[1]=false;
	 if(mouseX>220&&mouseX<290&&mouseY<430&&mouseY>400) {
    	 Stockbox[2]=true;	
 } else Stockbox[2]=false;
    if(mouseX>60&&mouseX<130&&mouseY<470&&mouseY>440) {
        Stockbox[3]=true;
    } else Stockbox[3]=false;
     //stock grain direction
    if(mouseX>300&&mouseX<370&&mouseY<430&&mouseY>400)grain[7]=!grain[7];
	//check for + or - maxParts button
	 if(rows<7&&mouseX>60&&mouseX<110&&mouseY<70&&mouseY>20) rows++;
	 if(rows>1&&mouseX>240&&mouseX<290&&mouseY<70&&mouseY>20) rows--;
    //grain top toggle check
    if(mouseX>300&&mouseX<350&&mouseY<70&&mouseY>20) grain[8]= !grain[8];
    //calculate button (calls to the algorithm and will come through here)
    if(mouseX>300&&mouseX<350&&mouseY<(height-25)&&mouseY>(height-75)){
        output=true;
        for(i=0;i<4;i++){//makes temporary values for stock input
            tstock[i]=Float.parseFloat(stock[i]);
        }
        x.clear();
        y.clear();
        w.clear();
        l.clear();
        func meth=new func();
        for (i=0;i<rows;i++){//convert data from string to float for algorithm
            tempstock[i]=Float.parseFloat(stock[i]);
            temptxt[3*i]=Float.parseFloat(txt[3*i]);
            temptxt[3*i+1]=Float.parseFloat(txt[3*i+1]);
            temptxt[3*i+2]=Float.parseFloat(txt[3*i+2]);
        }
        float crying[] = meth.cry(tempstock,temptxt,rows);//imput from algorithm to a float[] (totally could use this instead of arraylist)
        for(i=0;i<(crying.length/4);i++) {//convert to arraylist
            w.add((float) crying[4*i]);
            l.add((float) crying[4*i+1]);
            x.add((float) crying[4*i+2]);
            y.add((float) crying[4*i+3]);
        }
    }
}
//now that we have checked what box might be selected, handle typing into boxess
if (keyPressed&&cooldown<1) {
	for(i=0;i<(rows*3);i++) {//key handling for every box
		if(key == BACKSPACE) {
			if(box[i]&&cooldown<1){
                txt[i]="";
                cooldown=10;
            }
			if(Stockbox[0]&&cooldown<1){
                stock[0]="";
                cooldown=10;
            }
			if(Stockbox[1]&&cooldown<1) {
                stock[1] = "";
                cooldown=10;
            }
			if(Stockbox[2]&&cooldown<1){
                stock[2]="";
                cooldown=10;
            }
            if(Stockbox[3]&&cooldown<1){
                stock[3]="";
                cooldown=10;
            }
		}else {
			if(key=='0'||key=='1'||key=='2'||key=='3'||key=='4'||key=='5'||key=='6'||key=='7'||key=='8'||key=='9'||key=='.') {//add a check to only allow numerical inputs (or decimal)
                if (box[i] && cooldown < 1){
                    txt[i] += key;
                    cooldown=10;
                }
                if (Stockbox[0] && cooldown < 1){
                    stock[0] += key;
                    cooldown=10;
                }
                if (Stockbox[1] && cooldown < 1){
                    stock[1] += key;
                    cooldown=10;
                }
                if (Stockbox[2] && cooldown < 1){
                    stock[2] += key;
                    cooldown=10;
                }
                if (Stockbox[3] && cooldown < 1){
                    stock[3] += key;
                    cooldown=10;
                }
            }
        }
	}	
	}
cooldown-=1;
}
}// end of ui stuff
class func
{
    public static int totalcut(float[] cut, int row)
    {
        int num = 0;
        for (int i = 0; i < row * 3; i++)
        {
            if (i % 3 == 2) {
                num += cut[i];
            }
        }
        return num;
    }

    public static float[] cry(float[] stock, float[] cut, int row)
    {
        int tempj = 0;
        int num = totalcut(cut, row);
        Box[] obj = new Box[num];
        int temps = num * 4;
        float[] out = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

        for (int i = 0; i < row * 3; i++)
        {
            if (i % 3 == 0)
            {
                int j;
                for (j = 0; j < cut[i + 2]; j++)
                {
                    obj[j + tempj] = new Box(cut[i], cut[i + 1]);
                }
                tempj += j;
            }
        }
        obj[0].posx = 0;
        obj[0].posy = 0;
        float tempx = 0;

        for (int i = 1; i < num; i++)
        {
            if ((obj[i].height + obj[i - 1].height + obj[i - 1].posy) <= stock[1])
            {
                obj[i].posy = obj[i - 1].height + obj[i - 1].posy;
                obj[i].posx = tempx;
            } else if ((obj[i].width + obj[i - 1].width + obj[i - 1].posx) <= stock[0])
            {
                obj[i].posx = obj[i - 1].width + obj[i - 1].posx;
                obj[i].posy = 0;
                tempx = obj[i].posx;
            } else
            {
                System.out.println("cant fit");
                break;
            }
        }



        for(int i=0;i<num;i++)
        {
            out[4*i]=obj[i].width;
            out[4*i+1]=obj[i].height;
            out[4*i+2]=obj[i].posx;
            out[4*i+3]=obj[i].posy;
        }


        return out;
    }
}

class Box {
    float width, height, posx, posy;

    public Box(float width, float height) {
        this.width = width;
        this.height = height;
    }
}