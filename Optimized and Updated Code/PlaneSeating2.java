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
import java.util.LinkedList;
import java.util.Scanner;

/** 
 Author: Mohamed Farhaan A
 */

public class PlaneSeating2 {
    //Used to Acquire Data from User
    public static Scanner scan = new Scanner(System.in);
    public static String unplacedPasengers;
    //Used to store Passenger IDs'
    public static LinkedList passengerIDs;
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
        Returns Boolean Value
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
        Returns Boolean Value
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
        findMaximumSeatingCapacity - Function
        Arguments[Variable Name]   - none
        Returns integer
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
        longestNumSize             - Function
        Arguments[Variable Name]   - Long [longSize]
        Returns integer
        Use - To find largest numbers' number of digitis 
    */
    public static int longestNumSize(long longSize){
            int size=0;
            while(longSize>0){
                size++;
                longSize=longSize/10;
            }
            //The size of largest Number is returned
            return size;
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
        if(longSize==0)
            //longSize 0 denotes there are no passenger, therefore to show empty seat[x], character length of 1 is provided.
            longSize=1;
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
                                    idMarkedSeats[x][y][z]=String.format("%0"+longSize+"d",passengerIDs.get(traverseArray++));
                                //else filling it with empty seat gap filler
                                else
                                    idMarkedSeats[x][y][z]=emptySeats;
                            }
                            //Aisle Seats on Last Set are traversed
                            else if(x==setCount-1 && z==0){
                                //checking whether traversing array index is within the passenger count value
                                if(traverseArray<passengersCount)
                                    idMarkedSeats[x][y][z]=String.format("%0"+longSize+"d",passengerIDs.get(traverseArray++));
                                //else filling it with empty seat gap filler
                                else
                                    idMarkedSeats[x][y][z]=emptySeats;
                            }
                            //Aisle Seats on Middle Sets are traversed
                            else{
                                if((x!=0 && x!=setCount-1)&&(z==0 || z==columns[x]-1)){
                                    //checking whether traversing array index is within the passenger count value
                                    if(traverseArray<passengersCount)
                                        idMarkedSeats[x][y][z]=String.format("%0"+longSize+"d",passengerIDs.get(traverseArray++));
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
                                idMarkedSeats[x][y][z]=String.format("%0"+longSize+"d",passengerIDs.get(traverseArray++));
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
                                idMarkedSeats[x][y][z]=String.format("%0"+longSize+"d",passengerIDs.get(traverseArray++));
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
            }
            System.out.print("  ");
        }
           System.out.println();
           //Traversing upto end dimension of square matrix
            for(int i=0;i<largestMatrixSize;i++){
                //traversing through number of set
                for(int j=0;j<setCount;j++){
                    //Traversing upto end dimension of square matrix
                    for(int k=0;k<largestMatrixSize;k++){
                        if(k<columns[j]){
                            //In order to print column wise of all sets the indexing is modified as below
                            System.out.print(idMarkedSeats[j][i][k]+" ");
                        }
                    }
                    System.out.print("  ");
                }
                System.out.println("");
            }
            //If Passenger count is higher than Seating Capacity of deck, Passengers left without seat are intimated
            if(traverseArray<passengersCount){
                System.out.print("Passengers without Seat Allotment : ");
                for(int i=traverseArray;i<passengersCount;i++)
                    System.out.print(passengerIDs.get(i)+" ");
            }
            if(unplacedPasengers!=null)
                System.out.print(unplacedPasengers);
    }
    
    /*
        getPassengerIDs            - Function
        Arguments[Variable Name]   - none
        Use - To Get the PassengerIDs are store it in prioritized Manner
    */
    public static void getPassengerIDs(){
        System.out.print("Enter Number of Passengers : ");
        passengersCount = scan.nextInt();
        //Every Value from the user is obtained through tempGetValue variable;
        long tempGetValue=0;
        passengerIDs = new LinkedList();
        /*
            primeCheck - used to store and traverse the index position of last obtained prime number ID
            SecCheck   - used to store and traverse the index position of last obtained Power2 ID
        */
        int primeCheck=0,SecCheck=0; 
        if(passengersCount!=0)
            System.out.print("Enter the Passenger IDs (separated by space) : ");
        for(int i=0;i<passengersCount;i++)
        {
            tempGetValue = scan.nextLong();
            if(tempGetValue>longSize)
                longSize = tempGetValue;
            if(isPrime(tempGetValue))
            {
                passengerIDs.add(primeCheck, tempGetValue);
                primeCheck++;
                SecCheck++;
            }
            else if(isPowerTwo(tempGetValue))
            {
                passengerIDs.add(SecCheck, tempGetValue);
                SecCheck++;
            }
            else
            {
                passengerIDs.add(i, tempGetValue);
            }
        }
        longSize = longestNumSize(longSize);
        if((unplacedPasengers=scan.nextLine())!=null){}
        
    }
    
    /*
        getSeatDimensions          - Function
        Arguments[Variable Name]   - none
        Use - To Get the Seat Set Dimensions from the User
    */
    public static void getSeatDimensions(){
        System.out.print("Enter the Number of Sets of seat : ");
        setCount = Integer.parseInt(scan.nextLine());
        rows= new int[setCount];
        columns= new int[setCount];
        for(int i=0;i<setCount;i++){
            //Using Ternary Operator to denoth the suffix of set
            System.out.print("Enter Number of Rows for "+(i+1)+(i==0?"st":(i==1?"nd":(i==2?"rd":"th")))+" set : ");
            rows[i] = Integer.parseInt(scan.nextLine());
            if(largestMatrixSize<rows[i])
                largestMatrixSize=rows[i];
            //Using Ternary Operator to denoth the suffix of set
            System.out.print("Enter Number of Columns for "+(i+1)+(i==0?"st":(i==1?"nd":(i==2?"rd":"th")))+" set : ");
            columns[i] = Integer.parseInt(scan.nextLine());
            if(largestMatrixSize<columns[i])
                largestMatrixSize=columns[i];
        }
    }
    
    public static void main(String[] args) {
            //To Obtain the set dimensions from user
            getSeatDimensions();
            //To obtain the PassengerIDs from user
            getPassengerIDs();
            //To generate References of Seating Positions in Chart
            fillersForMarking();
            //Resulting Arrangement of PAssenger IDs are stored in idMarkedSheets[An 3D array]
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
    TestCase 1 :  SET DIMENSION - [[1,2],[2,2],[3,3],[4,4]]         PASSENGER IDs - [1,2,3,4,5,6,7,8,9,10]
         Input :    Enter the Number of Sets of seat : 4
                    Enter Number of Rows for 1st set : 1
                    Enter Number of Columns for 1st set : 1
                    Enter Number of Rows for 2nd set : 2
                    Enter Number of Columns for 2nd set : 2
                    Enter Number of Rows for 3rd set : 3
                    Enter Number of Columns for 3rd set : 3
                    Enter Number of Rows for 4th set : 4
                    Enter Number of Columns for 4th set : 4
                    Enter Number of Passengers : 10
                    Enter the Passenger IDs (separated by space) : 1 2 3 4 5 6 7 8 9 10
         Output: 
                Total Seating Capacity : 30
                Total Passengers : 10
                ww   aa aa   aa mm aa   aa mm mm ww   
                xx   02 05   04 xx 06   xx xx xx xx   
                     03 07   08 xx 09   xx xx xx xx   
                             01 xx 10   xx xx xx xx   
                                        xx xx xx xx 

    TestCase 2 : SET DIMENSION - [[2,3], [3,4], [3,2], [4,3]]         PASSENGER IDs - [29,59,14,11,3,13,15,18,12,16,6,17,7,47,61,5,21,2,41,9,10,8,19,1,4]
         Input :    Enter the Number of Sets of seat : 4
                    Enter Number of Rows for 1st set : 2
                    Enter Number of Columns for 1st set : 3
                    Enter Number of Rows for 2nd set : 3
                    Enter Number of Columns for 2nd set : 4
                    Enter Number of Rows for 3rd set : 3
                    Enter Number of Columns for 3rd set : 2
                    Enter Number of Rows for 4th set : 4
                    Enter Number of Columns for 4th set : 3
                    Enter Number of Passengers : 25
                    Enter the Passenger IDs (separated by space) : 29 59 14 11 3 13 15 18 12 16 6 17 7 47 61 5 21 2 41 9 10 8 19 1 4
         Output: 
                Total Seating Capacity : 36
                Total Passengers : 25
                ww mm aa   aa mm mm aa   aa aa   aa mm ww      
                18 01 29   11 xx xx 17   61 41   08 xx 06      
                12 xx 59   03 xx xx 07   05 19   04 xx 21      
                           13 xx xx 47   02 16   14 xx 09      
                                                 15 xx 10    

    TestCase 3 : SET DIMENSION - [[2,3],[3,4],[3,3]]         PASSENGER IDs - [1,57,17,9,6,15,8,29,23,16,25,89,90,91,92,93,94]
         Input :    Enter the Number of Sets of seat : 3
                    Enter Number of Rows for 1st set : 2
                    Enter Number of Columns for 1st set : 3
                    Enter Number of Rows for 2nd set : 3
                    Enter Number of Columns for 2nd set : 4
                    Enter Number of Rows for 3rd set : 3
                    Enter Number of Columns for 3rd set : 3
                    Enter Number of Passengers : 17
                    Enter the Passenger IDs (separated by space) : 1 57 17 9 6 15 8 29 23 16 25 89 90 91 92 93 94
         Output: 
                Total Seating Capacity : 27
                Total Passengers : 17
                ww mm aa   aa mm mm aa   aa mm ww      
                25 94 17   23 xx xx 16   09 xx 91      
                90 xx 29   89 xx xx 01   06 xx 92      
                           08 xx xx 57   15 xx 93    

    TestCase 4 : SET DIMENSION - [[1,1],[2,2]]         PASSENGER IDs - [5,0,3,2,7,9,6]
         Input :    Enter Number of Rows for 1st set : 1
                    Enter Number of Columns for 1st set : 1
                    Enter Number of Rows for 2nd set : 2
                    Enter Number of Columns for 2nd set : 2
                    Enter Number of Passengers : 5
                    Enter the Passenger IDs (separated by space) : 5 0 3 2 7 9 6
         Output: 
                Total Seating Capacity : 5
                Total Passengers : 5
                w   a w  
                2   5 7  
                    3 0  
                Passengers without Seat Allotment : 9 6

    TestCase 5: SET DIMENSION -  []         PASSENGER IDs - [1,2,3,4,5,6,7,8,9]
         Input :    Enter the Number of Sets of seat : 0
                    Enter Number of Passengers : 9
                    Enter the Passenger IDs (separated by space) : 1 2 3 4 5 6 7 8 9
         Output: 
                    Total Seating Capacity : 0
                    Total Passengers : 9

                    Passengers without Seat Allotment : 2 3 5 7 4 8 1 6 9

    TestCase 6: SET DIMENSION -  [[2,3],[5,1],[4,1]]         PASSENGER IDs - []
         Input :    Enter the Number of Sets of seat : 3
                    Enter Number of Rows for 1st set : 2
                    Enter Number of Columns for 1st set : 3
                    Enter Number of Rows for 2nd set : 5
                    Enter Number of Columns for 2nd set : 1
                    Enter Number of Rows for 3rd set : 4
                    Enter Number of Columns for 3rd set : 1
                    Enter Number of Passengers : 0
         Output: 
                    Total Seating Capacity : 15
                    Total Passengers : 0
                    w m a   a   w          
                    x x x   x   x          
                    x x x   x   x          
                            x   x          
                            x   x          
                            x            

    TestCase 7: SET DIMENSION -  []         PASSENGER IDs - []
         Input :    Enter the Number of Sets of seat : 0
                    Enter Number of Passengers : 0
         Output: 
                    Total Seating Capacity : 0
                    Total Passengers : 0

*/
