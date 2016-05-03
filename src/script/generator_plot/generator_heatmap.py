import numpy as np
import numpy.random
import matplotlib.pyplot as plt

x = []
y = []
success = []

nbPerloc = 1000

lines = [line.rstrip('\n') for line in open("results/quicksort/IntegerAddM_HeatMap.txt")]

for i in range(11):
    for line in lines[8+(i*nbPerloc):8+((i+1)*nbPerloc)]:
        l = ' '.join(line.split()).split(" ")
        x.append(float(l[0].replace(',','.')))
        y.append(float(l[1].replace(',','.')))
        perc = float(l[-1].replace(',','.'))
        print(perc / 100.0)
        success.append((0.25 + perc/100.0, 0.0, 1 - (perc / 100.0)))

plt.scatter(x,y,color=success,alpha=0.3)
plt.show()