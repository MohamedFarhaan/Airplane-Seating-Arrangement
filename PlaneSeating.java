
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class PlaneSeating {

    public static List<Integer> topPriority = new ArrayList<Integer>();
    public static List<Integer> secondPriority = new ArrayList<Integer>();
    public static List<Integer> leastPriority = new ArrayList<Integer>();
    
    
    public static boolean isPrime(int number)
    {
        for(int i=2;i<=Math.sqrt(number);i++)
        {
            if(number%i==0)
                return false;
        }
        return true;
    }
    
    public static boolean isPowerTwo(int number)
    {
        while(number>2)
        {
            if(number%2!=0)
                return false;
            else
            {
                number=number/2;
                return isPowerTwo(number);
            }
        }
        if (number==2 || number==0)
            return true;
        else
            return false;
    }
    
    public static int[] prioritize(int[] ids)
    {
        for(int i=0;i<ids.length;i++)
        {
            if(isPrime(ids[i]))
                topPriority.add(ids[i]);
            else if(isPowerTwo(ids[i]))
                secondPriority.add(ids[i]);
            else
                leastPriority.add(ids[i]);
        }
        int i=0;
            for(int j=0;j<topPriority.size();j++)
                ids[i++]=topPriority.get(j);
            for(int j=0;j<secondPriority.size();j++)
                ids[i++]=secondPriority.get(j);
            for(int j=0;j<leastPriority.size();j++)
                ids[i++]=leastPriority.get(j);
        
        return ids;
    }
    
    public static int countOccurence(String str, char c)
    {
        int count=0;
        for(int i=0;i<str.length();i++)
        {
            if(str.charAt(i)==c)
                count++;
        }
        return count;
    }
    
    public static int longestNumSize(int[] array)
    {
        Arrays.sort(array); 
        int size=0,temp=array[array.length-1];
        while(temp>0)
        {
            size++;
            temp=temp/10;
        }
        return size;
    }
    
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String arraySizeRC = scan.nextLine();
        String Ids = scan.nextLine();
        int ids_count = countOccurence(Ids,',')+1;
        int[] idArray = new int[ids_count];
        int count = countOccurence(arraySizeRC,']')-1;
        Integer[] rows = new Integer[count*2];
        Integer[] columns = new Integer[count*2];
        String[] convertedSizeArray = arraySizeRC.split(",");
        String[] convertedIdArray = Ids.split(",");
        int maxtemp=0;
        int rowsmax=0,colmax=0;
        for(int i=0;i<convertedSizeArray.length;i++)
        {
            convertedSizeArray[i]=convertedSizeArray[i].replaceAll("[^\\d]","");
            rows[i/2] = Integer.parseInt(convertedSizeArray[i]);
            if(rowsmax<rows[i/2])
                rowsmax=rows[i/2];
            if(maxtemp<rows[i/2])
                maxtemp=rows[i/2];
            i++;
            convertedSizeArray[i]=convertedSizeArray[i].replaceAll("[^\\d]","");
            columns[i/2] = Integer.parseInt(convertedSizeArray[i]);
            if(colmax<rows[i/2])
                colmax=rows[i/2];
            if(maxtemp<columns[i/2])
                maxtemp=columns[i/2];
        }
        for(int i=0;i<convertedIdArray.length;i++)
        {
            convertedIdArray[i]=convertedIdArray[i].replaceAll("[^\\d]","");
            idArray[i] = Integer.parseInt(convertedIdArray[i]);
        }
        int longSize = longestNumSize(idArray);
        //System.out.println("Size of long  = "+longSize);
        String EmptySeats="";
        String window="";
        String middle="";
        String aisle="";
        String space="";
        for(int i=0;i<longSize;i++){
            window = window + "w";
            middle = middle + "m";
            aisle = aisle + "a";
            EmptySeats = EmptySeats +"x";
            space = space + " ";
        }
        String[][][] result = new String[count][maxtemp][maxtemp];
        idArray = prioritize(idArray);
        int rowtrav=0,coltrav=0,travarr=0;
        
        //PRINTING WINDOW MIDDLE AND AISLE SEATS
        for(int i=0;i<count;i++)
        {
            for(int j=0;j<maxtemp;j++)
            {
                if(j<columns[i])
                {
                    if((i==0 && j==0) || (i==(count-1) && j==(columns[count-1])-1))
                        System.out.print(window+" ");
                    else if(((i!=0 && j==0) || (i==0 && j==(columns[0])-1)) || ((i!=0 || i!=count-1) && (j==0 || j==columns[i]-1)))
                        System.out.print(aisle+" ");
                    else
                        System.out.print(middle+" ");
                }
                else
                    System.out.print(space+" ");
            }
            
            System.out.print(space);
        }
           System.out.println();

        //filling aisle seats
        
        for(int y=0;y<maxtemp;y++)
        {
            for(int x=0;x<count;x++)
            {
                for(int z=0;z<maxtemp;z++)
                {
                    if(y<rows[x] && z<columns[x])
                    {
                        if(x==0 && z==columns[0]-1)
                        {
                            if(travarr<ids_count)
                                result[x][y][z]=String.format("%0"+longSize+"d",idArray[travarr++]);
                            else
                                result[x][y][z]=EmptySeats;
                        }
                        else if(x==count-1 && z==0) 
                        {
                            if(travarr<ids_count)
                                result[x][y][z]=String.format("%0"+longSize+"d",idArray[travarr++]);
                            else
                                result[x][y][z]=EmptySeats;
                        }
                        else
                        {
                            if((x!=0 && x!=count-1)&&(z==0 || z==columns[x]-1))
                            {
                                if(travarr<ids_count)
                                    result[x][y][z]=String.format("%0"+longSize+"d",idArray[travarr++]);
                                else
                                    result[x][y][z]=EmptySeats;
                            }
                        }
                    }
                    else
                        result[x][y][z]=space;
                }
            }
        }
        //filling window seats
        for(int y=0;y<maxtemp;y++)
        {
            for(int x=0;x<count;x++)
            {
                for(int z=0;z<maxtemp;z++)
                {
                    if(y<rows[x] && z<columns[x])
                    {
                        if((x==0 || x==count-1) && (x==z))
                        {
                            if(travarr<ids_count)
                                result[x][y][z]=String.format("%0"+longSize+"d",idArray[travarr++]);
                            else
                                result[x][y][z]=EmptySeats;
                        }
                    }
                    else
                        result[x][y][z]=space;
                }
            }
        }
        
        //filling middle seats
        for(int y=0;y<maxtemp;y++)
        {
            for(int x=0;x<count;x++)
            {
                for(int z=0;z<maxtemp;z++)
                {
                    if(y<rows[x] && z<columns[x])
                    {
                        if(result[x][y][z]==null)
                        {
                            if(travarr<ids_count)
                                result[x][y][z]=String.format("%0"+longSize+"d",idArray[travarr++]);
                            else
                                result[x][y][z]=EmptySeats;
                        }
                    }
                }
            }
        }
        
            for(int i=0;i<maxtemp;i++)
            {
                for(int j=0;j<count;j++)
                {
                    for(int k=0;k<maxtemp;k++)
                    {
                        System.out.print(result[j][i][k]+" ");
                    }
                    System.out.print(space);
                }
                System.out.println("");
            }
    }
    
} 
