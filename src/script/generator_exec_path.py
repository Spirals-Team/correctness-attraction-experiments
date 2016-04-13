from matplotlib import pyplot as plt

def gen_all(path, filename):

    lines = [line.rstrip('\n') for line in open(path+"/"+filename)]

    unperturbed = ' '.join(lines[0].split()).split(" ")
    p = []

    for line in lines[4:1004]:
        p.append(' '.join(line.split()).split(" "))

    maxsize = max(len(unperturbed), max(enumerate(p), key = lambda tup: len(tup[1]))[1])

    fig = plt.figure()
    ax = fig.add_axes((.1,.4,.8,.5))
    for i in range(len(p)):
    	plt.plot(range(0,len(p[i])), p[i], color='0.85', alpha=0.45,  linewidth=0.6)
    plt.plot(range(0,len(maxsize)), [0] * len(maxsize) , linestyle='dashed')

    caption = "Perturbation envelope of quicksort on an array of 100 integers\n"
    caption += "In red, the unperturbed execution, and\n"
    caption += "in gray, 1000 perturbed execution among the 3 campaigns."

    text=fig.text(.1,.1,caption)
    plt.plot(range(0,len(unperturbed)), unperturbed, 'r-', label="unperturbed", linewidth=1.5)

    plt.xlabel("time (in recursive call)")
    plt.ylabel("Number of unsorted pairs")
    plt.title("Executions of Quicksort")

    box = ax.get_position()
    #ax.set_xscale('log')
    ax.set_position([box.x0, box.y0, box.width * 0.8, box.height])
    lgd = ax.legend(loc='center left', bbox_to_anchor=(1, 0.5))
    fig.savefig(path+"/img/perturbationVisualization_All.pdf", bbox_extra_artists=(lgd,text), bbox_inches='tight')
    plt.close(fig)

def gen_for_exp(path, prefixename):

    files = ["ADD1","ADDN","RNDE"]

    for file in files:

        lines = [line.rstrip('\n') for line in open(path+"/"+prefixename+file+".txt")]

        unperturbed = ' '.join(lines[0].split()).split(" ")[1:]

        p = []
        labels = []

        for line in lines[1:6]:
           labels.append(' '.join(line.split()).split(" ")[0])
           p.append(' '.join(line.split()).split(" ")[1:])

        fig = plt.figure()
        ax = fig.add_axes((.1,.4,.8,.5))

        for i in range(len(p)):
            plt.plot(range(0,len(p[i])), p[i], marker='x', label=labels[i], linewidth=1)

        plt.plot(range(0,len(unperturbed)), unperturbed, 'r-', label="unperturbed", linewidth=1.5)

        caption = "Perturbation on execution of quicksort on an array of 100 integers\n"
        caption += "In red without marker, the unperturbed execution, and\n"
        caption += "others are the 5 perturbed execution on "+ file +" campaigns."

        text=fig.text(.1,.1,caption)

        plt.xlabel("time (in recursive call)")
        plt.ylabel("Number of unsorted pairs")
        plt.title("Executions of Quicksort on " + file)

        box = ax.get_position()
        ax.set_position([box.x0, box.y0, box.width * 0.8, box.height])
        lgd = ax.legend(loc='center left', bbox_to_anchor=(1, 0.5))
        fig.savefig(path+"/img/perturbationVisualization_"+file+".pdf", bbox_extra_artists=(lgd,text), bbox_inches='tight')
        plt.close(fig)



gen_all("results/quicksort-visualization", "exec_path_all.txt")
gen_for_exp("results/quicksort-visualization", "exec_path_")
