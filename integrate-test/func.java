package conversion;


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
        float[] out = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
        
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