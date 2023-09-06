//test function
#include <iostream>
#include <fstream>
using namespace std;
void test()
{
    int stockheight=0, stocklength=0, cutheight=0, cutlength=0;
    //get test number
    cout << "give stock sheet height in inches: ";
    cin >> stockheight;

    cout << "give stock sheet length in inches: ";
    cin >> stocklength;

    cout << "give cut height in inches: ";
    cin >> cutheight;

    cout << "give cut length in inches: ";
    cin >> cutlength;

    //debug print
    cout << endl << "stock height: " << stockheight << endl;
    cout << "stock length: " << stocklength << endl;
    cout << "cut height: " << cutheight << endl;
    cout << "cut length: " << cutlength << endl;
    cout << "stock area: " << (stockheight * stocklength) << endl;
    cout << "cut area: " << (cutheight * cutlength) << endl << endl;

    //possibility check
    if((stockheight*stocklength)>(cutheight*cutlength))
    {
        cout << "it fits" << endl;
    }
    else
    {
        cout << "it doesnt fit" << endl;
    }

    return 0;
}
