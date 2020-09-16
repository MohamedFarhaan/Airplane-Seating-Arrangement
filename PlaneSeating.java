/**
 Plane Seating Arrangement
 * This Class is used to generate seating arrangement in plane deck
 * Priority Constraints : Prime Number IDs' will be given top-priority
 *                     Factor of 2 IDs' will be given second top priority
 * Input Constraints : Seat Matrix Size range - 10Power3
 *                     Passenger ID Range - 10Power9
 * Output: Matrix Arrangement with IDs' marked along the Aisle[A], Window[W], Middle Seats[M]
 *         Empty seats are marked as X
 *         Total Seating Capacity is also calculated and displayed
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/** 
 Author: Mohamed Farhaan A
 */

public class PlaneSeating {
    //Used to Spearate The Prime Ids' from Passenger List
    public static List<Long> topPriority = new ArrayList<Long>(); 
    //Used to Spearate The Factor of 2 Ids' from Passenger List
    public static List<Long> secondPriority = new ArrayList<Long>();
    //The Leftover Passenger IDs' are stored with least priority
    public static List<Long> leastPriority = new ArrayList<Long>(); 
    /*
        largestMatrixSize  [int]variable - Used to store the largest dimension from the provided input of Seats
        setCount           [int]variable - Used to store number of sets of seating positions in plane which is divided by the walking area
        passengersCount    [int]variable - Used to store the total  number of passengers booked for the flight
        traverseArray      [int]variable - Used to traverse through seats
    */
    public static int largestMatrixSize=0,setCount=0,passengersCount=0,traverseArray=0;
    // [long]variable - Used to acquire lengthy Passeneger ID among the inputs(which ranges upto 10Power9)
    public static long longSize; 
    // [int]variable - Used to store the dimensions of the set of seats
    public static int[] rows,columns;
    // [long]variable - Used to acquire Passeneger ID (which ranges upto 10Power9)
    public static long[] passengerIDs;
    //[String]variable - Used as Gap Fillers
    public static String emptySeats="",windowMarkings="",middleMarkings="",aisleMarkings="",spaceBetweenElements="";
    /*
    [3D String Array]variable- Used to store the Result of Seating Arrangement. 
    Data Type is used as 
    */
    public static String[][][] idMarkedSeats;
    
    /*
        isPrime                  - Function
        Arguments[Variable Name] - Long[number]
        Retruns Boolean Value
        Use - To check whether a number is Prime Number or not
        NOTE : 0 and 1 are not considered as Prime Number
    */
    public static boolean isPrime(long number){
        // 0 and 1 are considered as composite numbers
        if(number==1 || number==0)
            return false;
        /*
            Checking prime number upto its square rooted value factor which is more optimized 
            way to find Prime or composite number
        */
        for(long i=2;i<=Math.sqrt(number);i++){
            if(number%i==0)
                return false;
        }
        return true;
    }
    
    /*
        isPowerTwo               - Function
        Arguments[Variable Name] - Long[number]
        Retruns Boolean Value
        Use - To check whether a number is Factor of 2 or not
    */
    public static boolean isPowerTwo(long number){
        /*
        temp['double'-data type] vairable stores the number log to base of 2
        Condition check : If rounding the temp value equals value without rounding, it denotes the number is a power 2
        */
          double temp = Math.log(number) / Math.log(2);
          if(temp==Math.round(temp) && number!=1)
              return true;
          else
              return false;
    }
    
    /*
        prioritize               - Function
        Arguments[Variable Name] - Long[ids] Array
        Retruns Long Array
        Use - To prioritize the values of Passenger  IDs' as per the constraints
    */
    public static long[] prioritize(long[] ids){
        for(int i=0;i<ids.length;i++){
            // Segregating the top priority (i.e.,) Prime Values
            if(isPrime(ids[i]))
                topPriority.add(ids[i]);
            // Segregating the second top priority (i.e.,) Power2 Values
            else if(isPowerTwo(ids[i]))
                secondPriority.add(ids[i]);
            //Appending the Least priority values to another ArrayList
            else
                leastPriority.add(ids[i]);
        }
        int i=0;
        //Rearranging the Values in ids' array in prioritized manner
            //Top priority Values are inserted
            for(int j=0;j<topPriority.size();j++)
                ids[i++]=topPriority.get(j);
            //Second Top priority Values are inserted
            for(int j=0;j<secondPriority.size();j++)
                ids[i++]=secondPriority.get(j);
            //Least priority Values are inserted
            for(int j=0;j<leastPriority.size();j++)
                ids[i++]=leastPriority.get(j);
        //Prioritized Array is returned
        return ids;
    }
    
     /*
        findMaximumSeatingCapacity - Function
        Arguments[Variable Name]   - none
        Retruns integer
        Use - To find maximum capacity in deck
    */
    public static int findMaximumSeatingCapacity(){
        //maxCap stores the Maximum Capacity in deck
        int maxCap=0;
        //traverses through the number of set
        for(int i=0;i<setCount;i++){
            //Rows and Columns are multiplied
            maxCap += rows[i]*columns[i];
        }
        //The maximum capacity is found and Returned
        return maxCap;
    }
    
    /*
        countOccurence             - Function
        Arguments[Variable Name]   - String[str] and Character[c]
        Retruns integer
        Use - To find maximum occurence of a cahracter in the given string
    */
    public static int countOccurence(String str, char c){
        //setCount['integer'-data type] is used to store the maximum occurence of the character in given string
        setCount=0;
        //The string is traversed character by character
        for(int i=0;i<str.length();i++){
            //if cahracter at 'i'th position equals the "toFind" character, setCount is incremented
            if(str.charAt(i)==c)
                setCount++;
        }
        //The maximum occurence value is returned
        return setCount;
    }
    
    /*
        longestNumSize             - Function
        Arguments[Variable Name]   - Long Array[array]
        Retruns integer
        Use - To find largest numbers' number of digitis 
    */
    public static int longestNumSize(long[] array){
        if(array.length!=0){
            //Array is sorted and the last number's number of digits is found and stored in size variable
            Arrays.sort(array); 
            int size=0;
            long temp=array[array.length-1];
            while(temp>0){
                size++;
                temp=temp/10;
            }
            //The size of largest Number in given array is returned
            return size;
        }
        //Array Length 0 denotes there are no Passengers
        else
            return 1;
    }
    
    /*
        co_ordinatesFinder         - Function
        Arguments[Variable Name]   - String[arraySizeRC]
        Use - To parse the rows and columns dimensions of set in deck and store them in an array 
    */
    public static void co_ordinatesFinder(String arraySizeRC){
        //setCount is used to store the Number of set in deck, since co-ordinates are closed by ']',']' character is passed to countOccurence function to get number of set
        setCount = countOccurence(arraySizeRC,']')-1;
        //Resetting setCount back to 1 if only one set is present
        if(setCount==0)
            setCount=1;
        //initializing rows and columns arrays to size of number of Dimensions
        rows = new int[setCount];
        columns = new int[setCount];
        /*The arraySizeRC string is splitted using comma, so that rows and columns
        values are found altrnatively to each other, so that it can be parsed from String array "convertedSizeArray"
        */
        String[] convertedSizeArray = arraySizeRC.split(",");
        int rowsmax=0,colmax=0;
        for(int i=0;i<convertedSizeArray.length;i++){
            //All non-digit characters are replaced with null cahracters while leaving the digit alone to be parsed
            convertedSizeArray[i]=convertedSizeArray[i].replaceAll("[^\\d]","");
            rows[i/2] = Integer.parseInt(convertedSizeArray[i]);
            if(rowsmax<rows[i/2])
                rowsmax=rows[i/2];
            //The Largest Matrix size is used to determine the Square Matrix Set size
            if(largestMatrixSize<rows[i/2])
                largestMatrixSize=rows[i/2];
            i++;
            //All non-digit characters are replaced with null cahracters while leaving the digit alone to be parsed
            convertedSizeArray[i]=convertedSizeArray[i].replaceAll("[^\\d]","");
            columns[i/2] = Integer.parseInt(convertedSizeArray[i]);
            if(colmax<rows[i/2])
                colmax=rows[i/2];
            if(largestMatrixSize<columns[i/2])
                largestMatrixSize=columns[i/2];
        }
    }
    
    /*
        idsFinder                  - Function
        Arguments[Variable Name]   - String[Ids]
        Use - To parse the Passenger IDs store them in an array 
    */
    public static void idsFinder(String Ids){
        //The Passenger IDs being spearated by commas, theryby we can find the number of Passengers
        passengersCount = countOccurence(Ids,',')+1;
        if(Ids.equals("[]"))
            passengersCount=0;
        passengerIDs = new long[passengersCount];
        //The Passenger IDs being spearated by commas, theryby we can parse the Passenger ID
        String[] convertedIdArray = Ids.split(",");
        for(int i=0;i<convertedIdArray.length;i++){
            convertedIdArray[i]=convertedIdArray[i].replaceAll("[^\\d]","");
            passengerIDs[i] = Long.parseLong(convertedIdArray[i]);
        }
    }
    
    /*
        fillersForMarking          - Function
        Arguments[Variable Name]   - none
        Use - To fil lgaps while printing the Seat placement chart
    */
    public static void fillersForMarking(){
        /*Since the Gap fillers needs to fill max digit in Passenger ID, longest Number 
          Sized digits are found and gap fillers are padded accordingly
        */        
        longSize = longestNumSize(passengerIDs);
        for(int i=0;i<longSize;i++){
            windowMarkings = windowMarkings + "w";
            middleMarkings = middleMarkings + "m";
            aisleMarkings = aisleMarkings + "a";
            emptySeats = emptySeats +"x";
            spaceBetweenElements = spaceBetweenElements + " ";
        }
    }
    
    /*
        fillAisleSeats             - Function
        Arguments[Variable Name]   - none
        Use - To fil Aisle Seats in deck
    */
    public static void fillAisleSeats(){
            //traversing through the set in deck
            for(int x=0;x<setCount;x++){
                //Neglecting if first Set or last set has only one Column Seating
                if((x==0 && columns[0]==1)||(x==setCount-1 && columns[setCount-1]==1))
                    continue;
                else{
                //traversing through the rows
                for(int z=0;z<largestMatrixSize;z++){
                    //traversing through the columns
                    for(int y=0;y<largestMatrixSize;y++){
                        //constraining the traversing till the specified dimensions of set
                        if(y<rows[x] && z<columns[x] ){
                            //Aisle Seats on First Set are traversed
                            if(x==0 && z==columns[0]-1){
                                //checking whether traversing array index is within the passenger count value
                                if(traverseArray<passengersCount)
                                    idMarkedSeats[x][y][z]=String.format("%0"+longSize+"d",passengerIDs[traverseArray++]);
                                //else filling it with empty seat gap filler
                                else
                                    idMarkedSeats[x][y][z]=emptySeats;
                            }
                            //Aisle Seats on Last Set are traversed
                            else if(x==setCount-1 && z==0){
                                //checking whether traversing array index is within the passenger count value
                                if(traverseArray<passengersCount)
                                    idMarkedSeats[x][y][z]=String.format("%0"+longSize+"d",passengerIDs[traverseArray++]);
                                //else filling it with empty seat gap filler
                                else
                                    idMarkedSeats[x][y][z]=emptySeats;
                            }
                            //Aisle Seats on Middle Sets are traversed
                            else{
                                if((x!=0 && x!=setCount-1)&&(z==0 || z==columns[x]-1)){
                                    //checking whether traversing array index is within the passenger count value
                                    if(traverseArray<passengersCount)
                                        idMarkedSeats[x][y][z]=String.format("%0"+longSize+"d",passengerIDs[traverseArray++]);
                                    //else filling it with empty seat gap filler
                                    else
                                        idMarkedSeats[x][y][z]=emptySeats;
                                }
                            }
                        }
                        //Filling out of dimensional co-ordinates with Gap Filler
                        else
                            idMarkedSeats[x][y][z]=spaceBetweenElements;
                    }
                }
            }
        }
    }
    
    /*
        fillWindowSeats            - Function
        Arguments[Variable Name]   - none
        Use - To fil Window Seats in deck
    */
    public static void fillWindowSeats(){
         //traversing through the set in deck
        for(int x=0;x<setCount;x++){
            //traversing through the rows
            for(int z=0;z<largestMatrixSize;z++){
                //traversing through the columns
                for(int y=0;y<largestMatrixSize;y++){
                    //constraining the traversing till the specified dimensions of set
                    if(y<rows[x] && z<columns[x]){
                        //Filling up 0th set window seat and last set window seat
                        if((x==0 && z==0)|| (x==setCount-1 && z==(columns[setCount-1]-1))){
                            //checking whether traversing array index is within the passenger count value
                            if(traverseArray<passengersCount)
                                idMarkedSeats[x][y][z]=String.format("%0"+longSize+"d",passengerIDs[traverseArray++]);
                            //else filling it with empty seat gap filler
                            else
                                idMarkedSeats[x][y][z]=emptySeats;
                        }
                    }
                    //Filling out of dimensional co-ordinates with Gap Filler
                    else
                        idMarkedSeats[x][y][z]=spaceBetweenElements;
                }
            }
        }
    }
    
    /*
        fillMiddleSeats            - Function
        Arguments[Variable Name]   - none
        Use - To fil middle Seats in deck
    */
    public static void fillMiddleSeats(){
        //traversing through the set in deck
        for(int x=0;x<setCount;x++){
            //traversing through the columns
            for(int z=0;z<largestMatrixSize;z++){
                //traversing through the columns
                for(int y=0;y<largestMatrixSize;y++){
                    //constraining the traversing till the specified dimensions of set
                    if(y<rows[x] && z<columns[x]){
                        //If Values are not filled, filling it with Passengers/EmptySpaceFillers
                        if(idMarkedSeats[x][y][z]==null){
                            //checking whether traversing array index is within the passenger count value
                            if(traverseArray<passengersCount)
                                idMarkedSeats[x][y][z]=String.format("%0"+longSize+"d",passengerIDs[traverseArray++]);
                            //else filling it with empty seat gap filler
                            else
                                idMarkedSeats[x][y][z]=emptySeats;
                        }
                    }
                }
            }
        }
    }
    
    /*
        printSeatingOrder          - Function
        Arguments[Variable Name]   - none
        Use - To print Seating Arrangement
    */
    public static void printSeatingOrder(){
        //Prints Total Seating Capacity
        System.out.println("Total Seating Capacity : "+findMaximumSeatingCapacity());
        //Prints total Passengers
        System.out.println("Total Passengers : "+passengersCount);
        //traversing through the set in deck
        for(int i=0;i<setCount;i++){
            //Traverse through the column markings and printing refering marks
            for(int j=0;j<largestMatrixSize;j++){
                if(j<columns[i]){
                    //First set and last set window Seats are selected
                    if((i==0 && j==0) || (i==(setCount-1) && j==(columns[setCount-1])-1))
                        System.out.print(windowMarkings+" ");
                    //First set right column, Last set first column, middle set leftmost and rightmost columns Aisle Seats are selected
                    else if(((i!=0 && j==0) || (i==0 && j==(columns[0])-1)) || ((i!=0 || i!=setCount-1) && (j==0 || j==columns[i]-1)))
                        System.out.print(aisleMarkings+" ");
                    //Others are Middle Columns
                    else
                        System.out.print(middleMarkings+" ");
                }
                else
                    System.out.print(spaceBetweenElements+" ");
            }
            
            System.out.print(spaceBetweenElements);
        }
           System.out.println();
           //Traversing upto end dimension of square matrix
            for(int i=0;i<largestMatrixSize;i++){
                //traversing through number of set
                for(int j=0;j<setCount;j++){
                    //Traversing upto end dimension of square matrix
                    for(int k=0;k<largestMatrixSize;k++){
                        //In order to print column wise of all sets the indexing is modified as below
                        System.out.print(idMarkedSeats[j][i][k]+" ");
                    }
                    System.out.print(spaceBetweenElements);
                }
                System.out.println("");
            }
            //If Passenger count is higher than Seating Capacity of deck, Passengers left without seat are intimated
            if(findMaximumSeatingCapacity()<passengersCount){
                System.out.print("Passengers without Seat Allotment : ");
                for(int i=findMaximumSeatingCapacity();i<passengersCount;i++)
                    System.out.print(passengerIDs[traverseArray++]+" ");
            }
    }
    
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        //Array Dimensions as a string - input
        String arraySizeRC = scan.nextLine();
        //Passenger IDs as a string - input
        String Ids = scan.nextLine();
            try{
                //Spearates Passenger Ids
                idsFinder(Ids);
            }
            //if there is no number to parse, it throws exception
            catch(NumberFormatException ex){
                System.out.println("**Please provide proper Passenger ID Input**");
            }
            try{
                //Spearates Coordinates
                co_ordinatesFinder(arraySizeRC);
            }
            //if there is no number to parse, it throws exception
            catch(NumberFormatException ex){
                System.out.println("**Please provide proper Seating Input**");
            }
            //GapFillers are padded accordingly
            fillersForMarking();
            //Array of APssenger IDs' are arranged in prioritized manner
            passengerIDs = prioritize(passengerIDs);
            //REsulting Arrangement of PAssenger IDs are stored in idMarkedSheets[An 3D array]
            idMarkedSeats = new String[setCount][largestMatrixSize][largestMatrixSize];
            //If there is only one set there will not be any aisle seats
            if(setCount>1)
                //Fill the Aisle Seats
                fillAisleSeats();
            //Fill the Window Seats
            fillWindowSeats();
            //Fill the Middle Seats
            fillMiddleSeats();
            // Print the Seating Arrangement
            printSeatingOrder();
        }
    
} 

/*
    TestCase 1 :
         Input : [[1,1],[2,2],[3,3],[4,4]]
                 [1,2,3,4,5,6,7,8,9,10]
         Output: 
                Total Seating Capacity : 30
                Total Passengers : 10
                ww            aa aa         aa mm aa      aa mm mm ww   
                xx            02 05         04 xx 06      xx xx xx xx   
                              03 07         08 xx 09      xx xx xx xx   
                                            01 xx 10      xx xx xx xx   
                                                          xx xx xx xx 

    TestCase 2 :
         Input : [[2,3], [3,4], [3,2], [4,3]]
                 [29,59,14,11,3,13,15,18,12,16,6,17,7,47,61,5,21,2,41,9,10,8,19,1,4]
         Output: 
                Total Seating Capacity : 36
                Total Passengers : 25
                ww mm aa      aa mm mm aa   aa aa         aa mm ww      
                09 21 02      05 xx xx 13   29 59         08 xx 12      
                10 xx 03      07 xx xx 17   41 61         16 xx 14      
                              11 xx xx 19   47 04         01 xx 15      
                                                          06 xx 18 

    TestCase 3 :
         Input : [[2,3],[3,4],[3,3]]
                 [1,57,17,9,6,15,8,29,23,16,25,89,90,91,92,93,94]
         Output: 
                Total Seating Capacity : 27
                Total Passengers : 17
                ww mm aa      aa mm mm aa   aa mm ww      
                57 94 17      29 xx xx 16   09 xx 91      
                90 xx 23      89 xx xx 01   15 xx 92      
                              08 xx xx 06   25 xx 93 

    TestCase 4 :
         Input : [[1,1],[2,2]]
                 [5,0,3,2,7,9,6]
         Output: 
                Total Seating Capacity : 5
                Total Passengers : 7
                w    a w  
                5    2 7  
                     3 0  
                Passengers without Seat Allotment : 6 9

    TestCase 5 :
         Input : []
                 [1,2,3,4,5,6,7,8,9]
         Output: 
                Total Seating Capacity : 5
                Total Passengers : 7
                w    a w  
                5    2 7  
                     3 0  
                Passengers without Seat Allotment : 6 9

    TestCase 6:
         Input : []
                 [1,2,3,4,5,6,7,8,9]
         Output: 
                **Please provide proper Seating Input**
                Total Seating Capacity : 0
                Total Passengers : 9

                Passengers without Seat Allotment : 2 3 5 7 4 8 1 6 9

    TestCase 7:
         Input : [[2,3],[5,1],[4,1]]
                 []
         Output: 
                **Please provide proper Passenger ID Input**
                Total Seating Capacity : 15
                Total Passengers : 0
                w m a      a          w          
                x x x      x          x          
                x x x      x          x          
                           x          x          
                           x          x          
                           x             

    TestCase 8:
         Input : []
                 []
         Output: 
                **Please provide proper Passenger ID Input**
                **Please provide proper Seating Input**
                Total Seating Capacity : 0
                Total Passengers : 0

*/
