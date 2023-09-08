#include <iostream>
#include <fstream>
#include <stack>
using namespace std;

class box
{
    public:
    float width, height, posx, posy;
};

stack<float>* cry(float stock[], float cut[], int row)
{
    int i=0,j=0,tempj=0,num=0;

    for(i=0;i<row*3;i++)
    {
        if(i%3==2)
        {
            num+=cut[i];
        }
    }

    box obj[num];
    static stack<float>* out;

    for(i=0;i<row*3;i++)
    {
        if(i%3==0)
        {
            for(j=0;j<cut[i+2];j++)
            {
                obj[j+tempj].width = cut[i];
                obj[j+tempj].height = cut[i+1];
            }
            tempj+=j;
        }
    }

    obj[0].posx = 0;
    obj[0].posy = 0;
    float tempx = 0;

    for(i=1;i<num;i++)
    {
        if((obj[i].height + obj[i-1].height + obj[i-1].posy) <= stock[1])
        {
            obj[i].posy = obj[i-1].height + obj[i-1].posy;
            obj[i].posx = tempx;
        }
        else if((obj[i].width + obj[i-1].width + obj[i-1].posx) <= stock[0])
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
    cout << num*4 << endl <<endl;
    for(i=num;i>1;i--)
    {
        out.push(obj[i].posy);
        out.push(obj[i].posx);
        out.push(obj[i].height);
        out.push(obj[i].width);
    }

    for(i=0;i<(sizeof(obj)/sizeof(obj[0]));i++)
    {
        cout << "width: " << obj[i].width << " height: " << obj[i].height << " posx: " << obj[i].posx << " posy: " << obj[i].posy << endl;
    }

    while(!out.empty())
    {
        cout << out.top() << " ";
        out.pop();
    }

    return out;
}
