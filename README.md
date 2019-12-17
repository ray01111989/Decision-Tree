# Decision-Tree
# This Java program is a decision tree, that emulates the "20 questions" game...
# and it is not limited to 20 questions. Each tree node holds 2 pointers
# to other tree nodes. One tree node is labeled yes and the other No. Also,
# each Tree node has a flag that checks if it's data is a question or an answer.
#  That is the first line of the file is Q: and under it is the question. Then the next line
# is either Q: or A: with the answer under A: and question under Q:. If the program
# guesses the wrong object it will ask for a yes/no question related to the answer
# thought off, and it will add it to the tree and file accordingly to make sure to ask 
# the question again to get the answer right. I made my code that take argument command line and if 
# you run the default file it will save to that file and read from it. 
# Additional thing is if there is no tree it will create tree in the default file also it will overwrite 
# the default file if you chose to not scan the previous tree in the default file. 
