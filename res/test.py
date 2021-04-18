file_open = open("words.txt", "a")

word_list = ['APPLE', 'AVOCADO', 'BANANA', 'BLACKBERRY', 'BLUEBERRY', 'CHERRY', 'COCONUT', 'GRAPE', 'LEMON', 'LIME', 'MANGO', 'ORANGE', 'PAPAYA', 'PASSION FRUIT', 'PEACH', 'PEAR', 'PINEAPPLE', 'PLUM', 'RASPBERRY', 'STRAWBERRY', 'WATERMELON']

for i in word_list[:-1]:
    file_open.write(i + "\n")

file_open.write(word_list[-1])
file_open.close()
