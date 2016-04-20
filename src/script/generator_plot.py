from matplotlib import pyplot as plt

import math

'''
this script is used to generate plot in results/<subject>/img/
Method are names : plot_Xaxis_Yaxis with
    Xaxis and Yaxis value used to build the plot.
'''


def plot_increasingPerturbation_percentageSuccess(path, filename, subject):

    '''
    this function is used to generate the plot of the increasing magnitudes and random rates.
    '''

    lines = [line.rstrip('\n') for line in open(path+"/"+filename)]

    labelOfN = ' '.join(lines[2].split()).split(" ")[0]
    n = ' '.join(lines[2].split()).split(" ")[3:]
    numberOfLocation = int(' '.join(lines[3].split()).split(" ")[0])

    percAll=[]
    indicesLocation=[]
    i = 9
    currentLoc = 0

    while currentLoc != 10 and i < (numberOfLocation*len(n)):#numberOfLocation:

        indexOfLocation = ' '.join(lines[i].split()).split(" ")[1]
        indicesLocation.append(indexOfLocation)

        perc=[]

        for line in lines[i:i+len(n)]:
            point  = float(' '.join(line.split()).split(" ")[-1].replace(',','.'))
            if point == point:
                perc.append(point)

        if not perc in percAll and len(perc) > 0:
            percAll.append(perc)
            currentLoc += 1

        i+=len(n)

    indexToCut = len(n)-1
    while indexToCut > 1:
        bool = True
        for i in range(len(percAll)):
            bool &= percAll[i][indexToCut] == percAll[i][indexToCut-1]
        if not bool:
            break;
        else:
            indexToCut -= 1

    percAll = sorted(percAll, key=lambda x: -x[0])

    fig = plt.figure()
    ax = fig.add_axes((.1,.4,.8,.5))
    for i in range(len(percAll)):
        plt.plot(n[:indexToCut], percAll[i][:indexToCut], marker='x', label=str(indicesLocation[i][:indexToCut]+ " " + str(int(percAll[i][0])) + " %"))
    plt.xlabel(labelOfN)
    plt.ylabel("% success")
    box = ax.get_position()
    txt = ""
    for line in lines[0:7]:
        txt += line +"\n"
    text = fig.text(.1,.0,txt)
    plt.title(subject)
    ax.set_position([box.x0, box.y0, box.width * 0.8, box.height])
    lgd = ax.legend(loc='center left', bbox_to_anchor=(1, 0.5))
    fig.savefig(path+"/img/"+labelOfN+"_plot.pdf", bbox_extra_artists=(lgd,text), bbox_inches='tight')
    plt.close(fig)

def scatterPlotSuccessNumPerturb(path, filename, subject):
     lines = [line.rstrip('\n') for line in open(path+"/"+filename)]
     title = ' '.join(lines[0].split()).split(" ")[0]

     labelOfN = ' '.join(lines[1].split()).split(" ")[0]
     n = ' '.join(lines[2].split()).split(" ")[3:]
     numberOfLocation = int(' '.join(lines[3].split()).split(" ")[0])

     percAll=[]
     nbPerturbAll=[]
     labelsAll=[]
     indicesLocation=[]
     i = 9
     currentLoc = 0

     while currentLoc != 10 and i < (numberOfLocation*len(n)):#numberOfLocation:
        indexOfLocation = ' '.join(lines[i].split()).split(" ")[1]
        indicesLocation.append(indexOfLocation)

        perc=[]
        label=[]
        nbPerturb=[]

        for line in lines[i:i+len(n)]:
            perc.append(float(' '.join(line.split()).split(" ")[-1].replace(',','.')))
            nbPerturb.append(int(' '.join(line.split()).split(" ")[6].replace(',','.')))

        label.append(str(' '.join(lines[i].split()).split(" ")[0].replace(',','.')))
        label.append(str(' '.join(lines[i+(len(n))/2].split()).split(" ")[0].replace(',','.')))
        label.append(str(' '.join(lines[i+len(n)-1].split()).split(" ")[0].replace(',','.')))

        if not perc in percAll:

            labelsAll.append(label)
            nbPerturbAll.append(nbPerturb)
            percAll.append(perc)

            currentLoc += 1

        i+=len(n)

     percAll = sorted(percAll, key=lambda x: -x[0])

     fig = plt.figure()
     ax = fig.add_axes((.1,.4,.8,.5))
     for i in range(len(percAll)):
         mid = len(percAll[i]) / 2
         plt.plot(nbPerturbAll[i], percAll[i], marker='x', label=str(indicesLocation[i]+" "+ str(int(percAll[i][0]))+ " %"))
         for z in [0,-1,mid]:
            ax.annotate(labelsAll[i][z if z != mid else 1], xy = (nbPerturbAll[i][z], percAll[i][z]),
                        xytext=(0.5, 5), textcoords='offset points', size=5)

     plt.xlabel("Number of Perturbations")
     plt.ylabel("% success")
     plt.title(subject)

     txt = "Annotation are the probability rate of enaction\n"
     for line in lines[0:7]:
         txt += line +"\n"
     text = fig.text(.1,.0,txt)

     box = ax.get_position()
     ax.set_position([box.x0, box.y0, box.width * 0.8, box.height])
     lgd = ax.legend(loc='center left', bbox_to_anchor=(1, 0.5))
     ax.set_xscale('symlog')
     fig.savefig(path+"/img/scatterPlotSuccessNumPerturb.pdf", bbox_extra_artists=(lgd,text), bbox_inches='tight')
     plt.close(fig)

subjects=["quicksort","zip","md5","sudoku","optimizer", "mersenne"]
for subject in subjects:
    scatterPlotSuccessNumPerturb("results/"+subject, "IntegerAdd1RndEnactorExplorer_random_rates_analysis_graph_data.txt", subject)
    plot_increasingPerturbation_percentageSuccess("results/"+subject, "AddNExplorer_magnitude_analysis_graph_data.txt", subject)
    plot_increasingPerturbation_percentageSuccess("results/"+subject, "IntegerAdd1RndEnactorExplorer_random_rates_analysis_graph_data.txt", subject)

