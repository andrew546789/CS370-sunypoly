#include <iostream>
#include <fstream>
#include "transferfunc.cpp"
#include <stack>

using namespace std;

int main()
{
    int row = 4, i=0, alen=0;
    float stock[3] = {10,10,3};
    float cut[12] = {2,10,1,3,10,1,3,5,1,4,4,1};
    float* a = cry(stock,cut,row);
    cout << "pass" << endl;

    int temps=0;
    while(a[temps]!=NULL)
    {
        temps++;
    }


    cout << "size=" << temps << endl;

    for(i=0;i<temps;i++)
    {
        cout << a[i] << endl;
    }

    return 0;
}

