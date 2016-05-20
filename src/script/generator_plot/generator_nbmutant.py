
import numpy as np
import matplotlib.pyplot as plt

import sys

subjects=sys.argv[1:]

for subject in subjects:
    lines = [line.rstrip('\n') for line in open("results/"+str(subject)+"/mutant_number.txt")]
    arrayNbTask = ' '.join(lines[0].split()).split(" ")
    sosies=[]
    pmutant=[]
    for nbTask in range(len(arrayNbTask)):
        sosies.append(' '.join(lines[2+nbTask].split()).split(" ")[1])
        pmutant.append(' '.join(lines[2+nbTask].split()).split(" ")[2])
    fig = plt.figure()
    ax = fig.add_axes((.1,.4,.8,.5))
    box = ax.get_position()
    ax.set_position([box.x0, box.y0, box.width * 0.7, box.height])
    plt.plot(arrayNbTask, sosies, label="#Sosie")
    plt.plot(arrayNbTask, pmutant, label="#PMutant")
    plt.title("# sosies/partial mutant by number of task on " + str(subject))
    plt.xlabel("#Task")
    plt.ylabel("#Mutant")
    lgd = ax.legend(loc='center left', bbox_to_anchor=(1, 0.5))
    fig.savefig("results/"+str(subject)+"/img/mutant_number_plot.pdf", bbox_extra_artists=(lgd,), bbox_inches='tight')
    fig.savefig("results/"+str(subject)+"/img/mutant_number_plot.jpeg", bbox_extra_artists=(lgd,), bbox_inches='tight')
    plt.close(fig)


