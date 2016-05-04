from matplotlib import pyplot as plt

def gen_all(path,file):
    lines = [line.rstrip('\n') for line in open(path+"/"+file+".txt")]

    unperturbed = ' '.join(lines[0].split()).split(" ")
    p = []

    for line in lines[1:]:
        l = ' '.join(line.split()).split(" ")
        print(len(l), len(unperturbed)+60)
        if len(l) < len(unperturbed)+60:
            p.append(l)

    cptGreen = 0
    cptRed = 0

    fig = plt.figure()
    ax = fig.add_axes((.1,.4,.8,.5))
    for i in range(len(p)):
        if p[i][-1] == '0':
            plt.plot(range(0,len(p[i])), p[i], color=(0.4,1,0.4), alpha=0.55,  linewidth=0.6)
            cptGreen += 1
        else:
            plt.plot(range(0,len(p[i])), p[i], color=(1,0.6,0.6), alpha=0.55,  linewidth=0.6)
            cptRed += 1

    caption = "Perturbation envelope of quicksort on an array of 100 integers\n"
    caption += "In blue, the unperturbed execution,\n"
    caption += "in green, the "+ str(cptGreen)  +" perturbed execution that end with a success, and\n"
    caption += "in light red, the "+ str(cptRed)  +" perturbed execution that end with a success with \n failure or exception among the 3 campaigns.\n"
    caption += "This is a sample of 30% (" + str((cptGreen+1+cptRed)) + " execs) of all execution of the 3 campaigns."

    text=fig.text(.1,-.1,caption)
    plt.plot(range(0,len(unperturbed)), unperturbed, 'b-', label="unperturbed", linewidth=1.5)

    plt.xlabel("time (in recursive call)")
    plt.ylabel("Number of unsorted pairs")
    plt.title("Executions of Quicksort")

    box = ax.get_position()
    ax.set_position([box.x0, box.y0, box.width * 0.8, box.height])
    fig.savefig(path+"/img/perturbationVisualization_All.pdf", bbox_extra_artists=(text,), bbox_inches='tight')
    ax.set_xscale('symlog')
    fig.savefig(path+"/img/perturbationVisualization_All_log.pdf", bbox_extra_artists=(text,), bbox_inches='tight')
    plt.close(fig)

def gen_all_per_xp(path, prefixe):

    files = ["ADD1","ADD2","ADD5", "ADD5", "ADD20","ADD10", "ADD50", "RND0.001", "RND0.01","RND0.1","RND0.005","RND0.05","RND0.5","RND0.9"]

    for file in files:

        lines = [line.rstrip('\n') for line in open(path+"/"+prefixe+file+".txt")]

        unperturbed = ' '.join(lines[0].split()).split(" ")[1:]
        p = []

        for line in lines[1:]:
            p.append(' '.join(line.split()).split(" ")[1:])

        cptGreen = 0
        cptRed = 0

        fig = plt.figure()
        ax = fig.add_axes((.1,.4,.8,.5))
        for i in range(len(p)):
            if p[i][-1] == '0':
                plt.plot(range(0,len(p[i])), p[i], color=(0.4,1,0.4), alpha=0.55,  linewidth=0.6)
                cptGreen += 1
            else:
                plt.plot(range(0,len(p[i])), p[i], color=(1,0.6,0.6), alpha=0.55,  linewidth=0.6)
                cptRed += 1

        caption = "Perturbation envelope of quicksort on an array of 100 integers\n"
        caption += "In blue, the unperturbed execution,\n"
        caption += "in green, the "+ str(cptGreen) +" perturbed execution that end with a success, and\n"
        caption += "in light red, the "+ str(cptRed) +" perturbed execution that end with a success with \n  failure or exception for the " +file + "exp.\n"
        caption += "in total, there is " + str((cptGreen+1+cptRed)) + " executions."

        text=fig.text(.1,.1,caption)
        plt.plot(range(0,len(unperturbed)), unperturbed, 'b-', label="unperturbed", linewidth=1.5)

        plt.xlabel("time (in recursive call)")
        plt.ylabel("Number of unsorted pairs")
        plt.title("Executions of Quicksort")

        box = ax.get_position()
        ax.set_position([box.x0, box.y0, box.width * 0.8, box.height])
        fig.savefig(path+"/img/perturbationVisualization_"+file+"_All.pdf", bbox_extra_artists=(text,), bbox_inches='tight')
        plt.close(fig)

def gen_sample_per_exp(path, prefixename):

    files = ["ADD1","ADD2","ADD5", "ADD5", "ADD20","ADD10", "ADD50", "RND0.001", "RND0.01","RND0.1","RND0.005","RND0.05","RND0.5","RND0.9"]

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
            plt.plot(range(0,len(p[i])), p[i], label=labels[i], linewidth=1)

        plt.plot(range(0,len(unperturbed)), unperturbed, 'b-', label="unperturbed", linewidth=1.5)

        caption = "Perturbation on execution of quicksort on an array of 100 integers\n"
        caption += "In blue without marker, the unperturbed execution, and\n"
        caption += "others are 5 perturbed execution on "+ file +" campaigns."

        text=fig.text(.1,.1,caption)

        plt.xlabel("time (in recursive call)")
        plt.ylabel("Number of unsorted pairs")
        plt.title("Executions of Quicksort on " + file)

        box = ax.get_position()
        ax.set_position([box.x0, box.y0, box.width * 0.8, box.height])
        lgd = ax.legend(loc='center left', bbox_to_anchor=(1, 0.5))
        fig.savefig(path+"/img/perturbationVisualization_"+file+".pdf", bbox_extra_artists=(lgd,text), bbox_inches='tight')
        plt.close(fig)

def gen_sample_for_all_exp(path, prefixename):

    files = ["RND","ADD"]

    for file in files:

        lines = [line.rstrip('\n') for line in open(path+"/"+prefixename+file+".txt")]

        unperturbed = ' '.join(lines[0].split()).split(" ")[1:]

        p = []
        labels = []

        for i in [1,40,80,120,160,200,-1]:
            labels.append(' '.join(lines[i].split()).split(" ")[0])
            p.append(' '.join(lines[i].split()).split(" ")[1:])

        fig = plt.figure()
        ax = fig.add_axes((.1,.4,.8,.5))

        for i in range(len(p)):
            plt.plot(range(0,len(p[i])), p[i], label=labels[i], linewidth=1)

        plt.plot(range(0,len(unperturbed)), unperturbed, 'b-', label="unperturbed", linewidth=1.5)

        caption = "Perturbation on execution of quicksort on an array of 100 integers\n"
        caption += "In blue without marker, the unperturbed execution, and\n"
        caption += "others are 5 perturbed execution on "+ file +" campaigns."

        text=fig.text(.1,.1,caption)

        plt.xlabel("time (in recursive call)")
        plt.ylabel("Number of unsorted pairs")
        plt.title("Executions of Quicksort on " + file)

        box = ax.get_position()
        ax.set_position([box.x0, box.y0, box.width * 0.8, box.height])
        lgd = ax.legend(loc='center left', bbox_to_anchor=(1, 0.5))
        fig.savefig(path+"/img/perturbationVisualization_"+file+".pdf", bbox_extra_artists=(lgd,text), bbox_inches='tight')
        plt.close(fig)

gen_all("results/quicksort-visualization", "exec_path_all")
gen_all_per_xp("results/quicksort-visualization", "exec_path_")
gen_sample_per_exp("results/quicksort-visualization", "exec_path_")
gen_sample_for_all_exp("results/quicksort-visualization", "exec_path_")
