
import numpy as np
import matplotlib.pyplot as plt

import sys

subjects=("classifier", "md5", "mersenne", "solver", "quicksort", "rsa", "sudoku", "zip", "torrent" ,"bitcoin")

perc = []
execs = []

if len(sys.argv) > 1:
    exp = sys.argv[1]
    perturbator = "NEGB"
else:
    exp = "IntegerAddOne"
    perturbator = "1"

for subject in subjects:
    file="results/"+subject+"/"+exp+"_CallExplorer_search_space_size_"+perturbator+".txt"
    lines = [line.rstrip('\n') for line in open(file)]
    perc.append(float(' '.join( lines[-1].split()).split(" ")[-1].replace(',','.')))
    execs.append(int(' '.join( lines[-3].split()).split(" ")[-1].replace(',','.')))

ind = np.arange(len(subjects))
width = 0.35

fig, ax = plt.subplots()


rects1 = ax.bar(ind, perc, width)

# add some text for labels, title and axes ticks
ax.set_ylim([0,100])
ax.set_ylabel('% Success')
ax.set_title('Percentage of Success By Subjects')
ax.set_xticks(ind + width)
ax.set_xticklabels(subjects, rotation=-45)

for tick in ax.xaxis.get_minor_ticks():
    tick.label1.set_horizontalalignment('left')

lgd = ax.legend((rects1[0],), ('% Success',), loc='center left', bbox_to_anchor=(1, 0.5))

def autolabel(rects, nbexecs):
    # attach some text labels
    for r in range(len(rects)):
        rect = rects[r]
        height = rect.get_height()
        print(nbexecs[r])
        ax.text(rect.get_x() + rect.get_width()/2., 1.05*height,
                '%d' % int(nbexecs[r]),
                ha='center', va='bottom')

autolabel(rects1, execs)
box = ax.get_position()
ax.set_position([box.x0, box.y0, box.width * 0.8, box.height])
fig.savefig("results/"+exp+"SuccesBarChart.pdf", bbox_extra_artists=(lgd,), bbox_inches='tight')




