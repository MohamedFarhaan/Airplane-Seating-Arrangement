# Airplane-Seating-Arrangement
A program that helps seat audiences in a flight based on the following input and rules.
Airplane Seating Arrangement
A program that helps seat audiences in a flight based on the following input and rules.

Rules for seating
● Always seat passengers starting from the front row to back, starting from the left to the right.
● Fill aisle seats first followed by window seats followed by center seats (any order in center seats).
● Passenger_id with a prime number should be given first priority.
● Passenger_id with a number multiple of 2 power n will be given second priority.

Input
● A 2 dimension input array that represents the rows and columns
	○ Example [[3,2], [4,3], [2,3], [3,4]]
● List of passenger id’s will be given.
	○ Example [29,59,14,11,3,13,15,18,12,16,6,17,7,47,61,5,21,2,41,9,10,8,19,1,4]

Expected Output
ww mm aa       aa mm mm aa    aa aa          aa mm ww       
09 21 01       03 xx xx 11    19 47          04 xx 12       
10 xx 02       05 xx xx 13    29 59          08 xx 14       
               07 xx xx 17    41 61          16 xx 15       
                                             06 xx 18   
										
Description of Code:
Initially we spilt the execution of the code into 3 parts.
	1. We parse the inputs in the string and store it in ArrayList
	2. We sort the ArrayList in Prioritized manner
	3. We place the Ids in repective postitions according to the rules mentioned
	
The placement of Ids in Respective Window, Middle and Aisle seats are done in main function. It can also be created into separate function and accessed further.
Since we need to sort the ids in specific manner. Separate functions has been written in order to segregate the ids.
	Function isPrime [Line 15:23] - It is a boolean function. Checks whether the passed argument is prime or not.
	Function isPowerTwo [Line 25:41] - It is a boolean function. Checks whether the passed argument is a power of two.
	Function prioritize [Line 43:63] - It is Array return function. It helps in segregating the ids' according to the rules and returns the values in a single array for further processing.
	Function countOccurence [Line 35:74] - This function helps in finding the number of occurences  of specific character in a string and returns the count as integer.
	Function longestNumSize [Line 76:86] - This function helps to find the number of digits in Longest Number of Ids' given.
Line 10, 11, 12 helps in initializing prioritized groups for ids' which will be put for segregation according the rules as mentioned.
The Main function extends from Line 88:256.
Line 89: Scanner object used to get input from the user.
Line 90: The Arrangement of seats in Aeroplane is given as a string of co-ordinates, which will further be processed to get the array row and column size.
Line 91: The IDs' from the users is got and stored in a string which will also be segregated into array of numbers.
Line 94: The number of sets of seats as a array is found by counting the number of ']'.
Line 92: The number of IDs' from the input is found by counting the number of commas. 
Line 95 and 96: The x and y co-ordinates are stored in rows and column arrays respectively.
We print the seating arrangement of the plane in a pattern of n sized square matrix, where the non-seat co-ordinates will be left blank while empty seats will be marked X.
Line 101:116: The rows and Columns are stored in specific arrays.
Line 117:121: The IDs' from the string are parsed into an array.
Line 122: The number of digits in Longest Number in Ids' is found.
Line 129:135: Strings for marking window, middle and aisle seats are padded with repective letter.
Line 136: The 3-Dimensional Result array will be consisting of the Seating arrangement of the IDs'.
Line 141:159: It prints the Marking for Window, middle and aisle seats with respective lettered strings.
Line 164:201: It consists for 3 for loops which is used to traverse through the array co-ordinates and fill only the aisle seats first with the prioritized array elements in order.
The String format function is used to maintain the alignment between the markings.
Line 124:224: It also consists of 3 for loops which is used to traverse through the array co-ordinates and fill only the window seats with the prioritized array elements in order.
Line 228:246: It also consists of 3 for loops which is used to traverse through the array co-ordinates and fill the remaining seats with the prioritized array elements in order.
Line 247:258: It consists of 3 for loops used to print the marked output.
