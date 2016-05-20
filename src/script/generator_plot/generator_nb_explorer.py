from matplotlib import pyplot as plt

import colors_manager

import sys

def plot_increasingNbTask_percentageSuccess(path, filename, output, subject, logscale=False):

    lines = [line.rstrip('\n') for line in open(path+"/"+filename)]

    labelOfN = ' '.join(lines[2].split()).split(" ")[0]
    n = ' '.join(lines[2].split()).split(" ")[3:]
    numberOfLocation = int(' '.join(lines[3].split()).split(" ")[0])



    percAll=[]
    nAll=[]
    indicesLocation=[]

    percSave = []
    nSave = []
    indiceSave = []

    i = 9

    while i < (numberOfLocation*len(n)):#numberOfLocation:

        perc=[]
        my_n = []

        for line in lines[i:i+len(n)]:
            point = float(' '.join(line.split()).split(" ")[-1].replace(',','.'))

            if point == point:
                perc.append(point)
            else:
                perc.append(float(100.0))

            my_n.append(n[lines[i:i+len(n)].index(line)])

        if not perc in percAll and len(perc) > 0 and [p == p for p in perc]:#and not perc[1:] == perc[:-1]:
            indexOfLocation = ' '.join(lines[i].split()).split(" ")[1]
            indicesLocation.append(indexOfLocation)
            percAll.append(perc)
            nAll.append(my_n)

        i+=len(n)

    sortedPerc, indicesLocation = [list(x)[:10] for x in zip(*sorted(zip(percAll, indicesLocation), key=lambda pair: -pair[0][-1]))]
    percAll, nAll = [list(x)[:10] for x in zip(*sorted(zip(percAll, nAll), key=lambda pair: -pair[0][-1]))]

    indexToCutAll = []
    for i in range(len(percAll)):
        indexToCut = len(percAll[i])-1
        while indexToCut > 1:
            if abs(percAll[i][indexToCut] - percAll[i][indexToCut-1]) < 0:
                indexToCut -= 1
            else:
                break;
        indexToCutAll.append(indexToCut)

    indexToCut = max(indexToCutAll)

    sortedPerc, indicesLocation = [list(x) for x in zip(*sorted(zip(percAll, indicesLocation), key=lambda pair: -pair[0][0]))]
    percAll, nAll = [list(x) for x in zip(*sorted(zip(percAll, nAll), key=lambda pair: -pair[0][0]))]

    fig = plt.figure()
    ax = fig.add_axes((.1,.4,.8,.5))
    for i in range(len(percAll)):
        cut = len(percAll[i])
        #cut = min(indexToCut, len(percAll[i]))
        color = colors_manager.getColor(int(indicesLocation[i]))
        plt.plot(nAll[i][:cut], percAll[i][:cut], marker='x', color=color, label=str(indicesLocation[i]+ " " + str(int(percAll[i][0])) + " %"))
    plt.xlabel(labelOfN)
    plt.ylabel("% success")
    box = ax.get_position()
    txt = ""
    for line in lines[0:7]:
        txt += line +"\n"
    text = fig.text(.1,-.10,txt)
    plt.title(subject)
    ax.set_position([box.x0, box.y0, box.width * 0.7, box.height])
    lgd = ax.legend(loc='center left', bbox_to_anchor=(1, 0.5))
    fig.savefig(path+"/img/"+output+"_plot.pdf", bbox_extra_artists=(text,), bbox_inches='tight')
    fig.savefig(path+"/img/"+output+"_plot.jpeg", bbox_extra_artists=(text,), bbox_inches='tight')
    ax.set_xscale('log')
    fig.savefig(path+"/img/"+output+"_plot_logscale.pdf", bbox_extra_artists=(text,), bbox_inches='tight')
    fig.savefig(path+"/img/"+output+"_plot_logscale.jpeg", bbox_extra_artists=(text,), bbox_inches='tight')
    plt.close(fig)

#subjects=["quicksort","md5","mersenne","zip"]
subjects=sys.argv[1:]
for subject in subjects:
    plot_increasingNbTask_percentageSuccess("results/"+subject, "NumberTaskExplorer.txt", "numberexplorer", subject)

