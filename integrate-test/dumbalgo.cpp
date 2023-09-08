//tests for objects in cpp

#include <iostream>
#include <fstream>
using namespace std;

class box
{
    public:
    float width, height, posx, posy;
};

void cry()
{
    int num,i;
    float Cwidth, Cheight, Bwidth, Bheight;

    cout << "enter number of boxes: ";
    cin >> num;

    cout << "enter height of board: ";
    cin >> Bheight;
    cout << "enter width of board: ";
    cin >> Bwidth;

    box obj[num];

    for(i=0;i<num;i++)
    {
        cout << "input cut width: ";
        cin >> Cwidth;
        cout << "input cut height: ";
        cin >> Cheight;
        cout << endl;

        obj[i].width = Cwidth;
        obj[i].height = Cheight;
    }

    obj[0].posx = 0;
    obj[0].posy = 0;
    float tempx = 0;

    for(i=1;i<num;i++)
    {
        if((obj[i].height + obj[i-1].height + obj[i-1].posy) <= Bheight)
        {
            obj[i].posy = obj[i-1].height + obj[i-1].posy;
            obj[i].posx = tempx;
        }
        else if((obj[i].width + obj[i-1].width + obj[i-1].posx) <= Bwidth)
        {
            obj[i].posx = obj[i-1].width + obj[i-1].posx;
            obj[i].posy = 0;
            tempx = obj[i].posx;
        }
        else
        {
            cout << "cant fit" << endl;
            break;
        }
    }

    for(i=0;i<num;i++)
    {
        //cout << "y: " << object[i].posy << " x: " << object[i].posx << endl << endl;
        cout << "box " << i+1 <<" || posx = " << obj[i].posx << " || posy = " << obj[i].posy << endl;
    }
}
