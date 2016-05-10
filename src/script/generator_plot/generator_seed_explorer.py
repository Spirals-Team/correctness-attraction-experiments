import numpy as np
from matplotlib import pyplot as plt
import sys
import random

random.seed(23)

path = "results/zip"
filename = "SeedExploration.txt"
lines = [line.rstrip('\n') for line in open(path+"/"+filename)]
seeds = ' '.join(lines[2].split()).split(" ")[3:]
nbLocation = int(' '.join(lines[3].split()).split(" ")[0])

ind = np.arange(len(seeds))
width = 0.35

i  = 9
N = 5
perc = []

for currentLocation in range(N):#in range(nbLocation):
    indexLocation = ' '.join(lines[i].split()).split(" ")[1]
    perc.append([])
    for seed in range(len(seeds)):
        perc[currentLocation].append(float(' '.join(lines[seed+i].split()).split(" ")[-1].replace(",", ".")))
    i = i + len(seeds)

fig, ax = plt.subplots()
for currentLocation in range(N):
    color = [random.random(), random.random(), random.random()]
    ax.bar(width*currentLocation + ind, perc[currentLocation], width, color=color)

ax.set_xticks(ind + width)
ax.set_xticklabels(seeds)

plt.show()

