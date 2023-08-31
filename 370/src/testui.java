import processing.core.*;//this is the library that makes display very easy
/*
To Do List:
calculate cuts button
part display (right side)
Stock Display (right side)
window resizing compatibility
blade thickness box

could swap to part objects instead of box/txt array but the only advantage is adding more parts easily
the problem is if i add more parts i need to redo the display to add a couple more or add a scroll wheel to allow more than like 10 parts.
*/

public class testui extends PApplet{
int rows=7;
Boolean box[]= {false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false};//x21 
String	txt[]= {"test","test","test","test","test","test","test","test","test","test","test","test","test","test","test","test","test","test","test","test","test"};//x21
String stock[]= {"w","h","q"};
Boolean Stockbox[]= {false,false,false};
int cooldown=0;//frames b4 another input is processed
int selectedboxcolor=250;
int i=0;
    @Override
    public void settings() {
        size(1200, 675);//sets window size (will effect other display elements)
    }

    @Override
    public void draw() {
        background(0);
        fill(190);
        rect(0, 0, width/4, height);//left bar background
        textSize(20);	//prolly should determine this based on screen size but whatev
        //part display:
        for (i=0;i<rows;i++) {//i=# of current rows (buttons should change this)
        	fill(10);
            text("Part "+(i+1)+":",5,100+(40*i));
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
       	text(stock[2], 220, 420);// end of Stock display
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
     }
	 //mouse collision for each Stock box
	 if(mouseX>60&&mouseX<130&&mouseY<(430)&&mouseY>(400)) {
    	 Stockbox[0]=true;	
 } else Stockbox[0]=false;
	 if(mouseX>140&&mouseX<210&&mouseY<(430)&&mouseY>(400)) {
    	 Stockbox[1]=true;	
 } else Stockbox[1]=false;
	 if(mouseX>220&&mouseX<290&&mouseY<(430)&&mouseY>(400)) {
    	 Stockbox[2]=true;	
 } else Stockbox[2]=false;
	//check for + or - maxParts button
	 if(rows<7&&mouseX>60&&mouseX<110&&mouseY<(70)&&mouseY>(20)) rows++;
	 if(rows>1&&mouseX>240&&mouseX<290&&mouseY<(70)&&mouseY>(20)) rows--;
}
//now that we have checked what box might be selected, handle typing into boxes
if (keyPressed&&cooldown<1) {
	cooldown=10;
	for(i=0;i<(rows*3);i++) {//key handling for every box
		if(key == BACKSPACE) {
			if(box[i])txt[i]="";
			if(Stockbox[0])stock[0]="";
			if(Stockbox[1])stock[1]="";
			if(Stockbox[2])stock[2]="";
		}else {
			if(box[i])txt[i]+=key;
			if(Stockbox[0])stock[0]+=key;
			if(Stockbox[1])stock[1]+=key;
			if(Stockbox[2])stock[2]+=key;
		}
	}	
	}
cooldown-=1;
}
}