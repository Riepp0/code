#Create an empty list of 10 elements
list1 = [0] * 10
#Fill the list with user input
for i in range(10):
    list1[i] = input("Enter a number: ")
#Print the highest number
print("The highest number is", max(list1))
#Print the lowest number
print("The lowest number is", min(list1))
#End of program