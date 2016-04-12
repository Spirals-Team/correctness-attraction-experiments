
from matplotlib import pyplot as plt

def gen(path, filename):

    lines = [line.rstrip('\n') for line in open(path+"/"+filename)]

    noperturb = ' '.join(lines[0].split()).split(" ")
    p = []
    for line in lines[1:]:
        p.append(' '.join(line.split()).split(" "))

    maxsize = max(len(noperturb), max(enumerate(p), key = lambda tup: len(tup[1]))[1])

    fig, ax = plt.subplots(nrows=1, ncols=1)
    plt.plot(range(0,len(noperturb)), noperturb, marker='D', label="not perturbed")
    for i in range(len(p)):
    	plt.plot(range(0,len(p[i])), p[i], marker='x', label="p"+str(i))
    plt.plot(range(0,len(maxsize)), [0] * len(maxsize) , linestyle='dashed')

    plt.xlabel("time (in recursive call)")
    plt.ylabel("Number Of unsorted pair")
    plt.title("Executions of Quicksort")

    box = ax.get_position()
    #ax.set_xscale('log')
    ax.set_position([box.x0, box.y0, box.width * 0.8, box.height])
    ax.legend(loc='center left', bbox_to_anchor=(1, 0.5))
    fig.savefig(path+"/img/plot.pdf")
    plt.close(fig)

gen("results/sort2", "exec_path.txt")
