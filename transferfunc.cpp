#include <iostream>
#include <fstream>
#include <stack>
using namespace std;

int totalcut(float[],int);
float* cry(float[], float[], int);

class box
{
    public:
    float width, height, posx, posy;
};

//float* cry(float[], float[], int);

float* cry(float stock[], float cut[], int row)
{
    int i=0,j=0,tempj=0,num=0;

    num = totalcut(cut,row);

    box obj[num];
    static int temps = num*4;
    static float out[16];

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

    for(i=0;i<num*4;i++)
    {
        out[4*i]=obj[i].width;
        out[4*i+1]=obj[i].height;
        out[4*i+2]=obj[i].posx;
        out[4*i+3]=obj[i].posy;
    }

    for(i=0;i<(sizeof(obj)/sizeof(obj[0]));i++)
    {
        cout << "width: " << obj[i].width << " height: " << obj[i].height << " posx: " << obj[i].posx << " posy: " << obj[i].posy << endl;
    }

    cout << endl;

    for(i=0;i<(sizeof(out)/sizeof(out[0]));i++)
    {
        cout << out[i] << endl;
    }


    return out;
}

int totalcut(float cut[],int row)
{
    int i=0,num=0;
    for(i=0;i<row*3;i++)
    {
        if(i%3==2)
        {
            num+=cut[i];
        }
    }
    return num;
}
