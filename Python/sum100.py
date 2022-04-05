#Asking two numbers in input
num1 = input("Enter a number: ")
num2 = input("Enter another number: ")
#If the sum is greater than 100, print "That's a big number!"
if int(num1) + int(num2) > 100:
    print("That's a big number!")
    #If the sum is less than 100, print "That's a small number!"
elif int(num1) + int(num2) < 100:
    print("That's a small number!")
    #End of program