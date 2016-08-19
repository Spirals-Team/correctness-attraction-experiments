
lines = [line.rstrip('\n') for line in open("results/example/IntegerAddOne_CallExplorer_detail.txt")]

for line in lines[5:]:
    array = ' '.join(line.split()).split(" ")
    perc = array[-1].replace(",", ".")
    if int(array[2]) == 6 and (float(perc) != float(perc) or float(perc) != 100.00):
        print array