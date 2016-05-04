from matplotlib import pyplot as plt
'''
this script is used to generate plot in results/<subject>/img/
'''


def plot_increasingPerturbation_percentageSuccess(path, filename, output, subject, logscale=False):

    '''
    this function is used to generate the plot of the increasing magnitudes and random rates.
    '''

    lines = [line.rstrip('\n') for line in open(path+"/"+filename)]

    labelOfN = ' '.join(lines[2].split()).split(" ")[0]
    n = ' '.join(lines[2].split()).split(" ")[3:]
    numberOfLocation = int(' '.join(lines[3].split()).split(" ")[0])

    percAll=[]
    nAll=[]
    callAll=[]
    perturbAll=[]
    indicesLocation=[]
    i = 9
    currentLoc = 0

    while currentLoc != 10 and i < (numberOfLocation*len(n)):#numberOfLocation:

        perc=[]
        my_n = []
        my_call = 0
        my_perturb = 0

        for line in lines[i:i+len(n)]:
            point = float(' '.join(line.split()).split(" ")[-1].replace(',','.'))

            if point == point:
                perc.append(point)
            else:
                perc.append(float(100.0))
            my_call += int(' '.join(line.split()).split(" ")[-4].replace(',','.'))
            my_perturb += int(' '.join(line.split()).split(" ")[-3].replace(',','.'))
            my_n.append(n[lines[i:i+len(n)].index(line)])


        if not perc in percAll and len(perc) > 0 and [p == p for p in perc]:
            indexOfLocation = ' '.join(lines[i].split()).split(" ")[1]
            indicesLocation.append(indexOfLocation)
            percAll.append(perc)
            nAll.append(my_n)
            currentLoc += 1
            callAll.append(my_call)
            perturbAll.append(my_perturb)

        i+=len(n)

    indexToCutAll = []
    for i in range(len(percAll)):
        indexToCut = len(percAll[i])-1
        while indexToCut > 1:
            if percAll[i][indexToCut] == percAll[i][indexToCut-1]:
                indexToCut -= 1
            else:
                break;
        indexToCutAll.append(indexToCut)

    indexToCut = max(indexToCutAll)

    sortedPerc, indicesLocation = [list(x) for x in zip(*sorted(zip(percAll, indicesLocation), key=lambda pair: -pair[0][0]))]
    sortedPerc, callAll = [list(x) for x in zip(*sorted(zip(percAll, callAll), key=lambda pair: -pair[0][0]))]
    sortedPerc, perturbAll = [list(x) for x in zip(*sorted(zip(percAll, perturbAll), key=lambda pair: -pair[0][0]))]
    percAll, nAll = [list(x) for x in zip(*sorted(zip(percAll, nAll), key=lambda pair: -pair[0][0]))]

    fig = plt.figure()
    ax = fig.add_axes((.1,.4,.8,.5))
    for i in range(len(percAll)):
        #cut = len(percAll[i])
        cut = min(indexToCut, len(percAll[i]))
        plt.plot(nAll[i][:cut], percAll[i][:cut], marker='x', label=str(indicesLocation[i]+ " " + str(int(percAll[i][0])) + " %"))
    plt.xlabel(labelOfN)
    plt.ylabel("% success")
    box = ax.get_position()
    txt = ""
    for line in lines[0:7]:
        txt += line +"\n"
    width = 8
    #txt+= '{0:{width}} {1:{width}} {2:{width}}'.format("Loc","#Execs", "#Perturbation", width=width)
    #txt+='\n'
    #for i in range(len(callAll)):
    #    txt+='{0:{width}} {1:{width}} {2:{width}}'.format(indicesLocation[i],str(callAll[i]),str(perturbAll[i]), width=width)
    #    txt+='\n'

    text = fig.text(.1,-.3,txt)
    plt.title(subject)
    ax.set_position([box.x0, box.y0, box.width * 0.8, box.height])
    lgd = ax.legend(loc='center left', bbox_to_anchor=(1, 0.5))
    fig.savefig(path+"/img/"+output+"_plot.pdf", bbox_extra_artists=(lgd,text), bbox_inches='tight')
    ax.set_xscale('log')
    fig.savefig(path+"/img/"+output+"_plot_logscale.pdf", bbox_extra_artists=(lgd,text), bbox_inches='tight')
    plt.close(fig)

def scatterPlotSuccessNumPerturb(path, filename, output, subject):
     lines = [line.rstrip('\n') for line in open(path+"/"+filename)]
     title = ' '.join(lines[0].split()).split(" ")[0]

     labelOfN = ' '.join(lines[1].split()).split(" ")[0]
     n = ' '.join(lines[2].split()).split(" ")[3:]
     numberOfLocation = int(' '.join(lines[3].split()).split(" ")[0])
     nbTask = int(' '.join(lines[6].split()).split(" ")[0])
     nbRepeat = int(' '.join(lines[4].split()).split(" ")[0])

     percAll=[]
     nbPerturbAll=[]
     labelsAll=[]
     indicesLocation=[]
     i = 9
     currentLoc = 0

     while currentLoc != 10 and i < (numberOfLocation*len(n)):#numberOfLocation:

        perc=[]
        label=[]
        nbPerturb=[]

        for line in lines[i:i+len(n)]:
            point = float(' '.join(line.split()).split(" ")[-1].replace(',','.'))
            if point == point:
                perc.append(point)
                nbPerturb.append(int(' '.join(line.split()).split(" ")[6].replace(',','.')))

        label.append(str(' '.join(lines[i].split()).split(" ")[0].replace(',','.')))
        label.append(str(' '.join(lines[i+(len(n))/2].split()).split(" ")[0].replace(',','.')))
        label.append(str(' '.join(lines[i+len(n)-1].split()).split(" ")[0].replace(',','.')))

        if not perc in percAll and not len(perc) == 0:
            indexOfLocation = ' '.join(lines[i].split()).split(" ")[1]
            indicesLocation.append(indexOfLocation)

            labelsAll.append(label)
            nbPerturbAll.append(nbPerturb)
            percAll.append(perc)

            currentLoc += 1

        i+=len(n)


     sortedPerc, indicesLocation = [list(x) for x in zip(*sorted(zip(percAll, indicesLocation), key=lambda pair: -pair[0][0]))]
     percAll, nbPerturbAll = [list(x) for x in zip(*sorted(zip(percAll, nbPerturbAll), key=lambda pair: -pair[0][0]))]

     fig = plt.figure()
     ax = fig.add_axes((.1,.4,.8,.5))
     for i in range(len(percAll)):
         mid = len(percAll[i]) / 2
         plt.plot(nbPerturbAll[i][len(nbPerturbAll[i])-len(percAll[i]):], percAll[i], marker='x', label=str(indicesLocation[i]+" "+ str(int(percAll[i][0]))+ " %"))
         for z in [0,-1,mid]:
            ax.annotate(labelsAll[i][z if z != mid else 1], xy = (nbPerturbAll[i][z], percAll[i][z]),
                        xytext=(0.5, 5), textcoords='offset points', size=5)

     plt.xlabel("Avg Perturbation Per Tasks")
     plt.ylabel("% success")
     plt.title(subject)

     txt = "Annotation are the probability rate of enaction\n"
     for line in lines[0:7]:
         txt += line +"\n"
     text = fig.text(.1,-.1,txt)

     box = ax.get_position()
     ax.set_position([box.x0, box.y0, box.width * 0.8, box.height])
     lgd = ax.legend(loc='center left', bbox_to_anchor=(1, 0.5))
     ax.set_xscale('symlog')
     fig.savefig(path+"/img/scatterPlotSuccessNumPerturb_"+output+".pdf", bbox_extra_artists=(lgd,text), bbox_inches='tight')
     plt.close(fig)

#subjects=["quicksort","zip","md5","sudoku","optimizer","mersenne"]
subjects=sys.argv[1:]
for subject in subjects:
    scatterPlotSuccessNumPerturb("results/"+subject, "IntegerAdd1RndEnactorExplorer_random_rates_analysis_graph_data.txt", "intadd1_rnd" ,subject)
    scatterPlotSuccessNumPerturb("results/"+subject, "BooleanInvRndEnactorExplorer_random_rates_analysis_graph_data.txt", "boolinv_rnd", subject)
    plot_increasingPerturbation_percentageSuccess("results/"+subject, "AddNExplorer_magnitude_analysis_graph_data.txt", "magnitude_call" ,subject)
    plot_increasingPerturbation_percentageSuccess("results/"+subject, "IntegerAdd1RndEnactorExplorer_random_rates_analysis_graph_data.txt", "intadd1_rnd",subject, logscale=True)
    plot_increasingPerturbation_percentageSuccess("results/"+subject, "BooleanInvRndEnactorExplorer_random_rates_analysis_graph_data.txt", "boolinv_rnd", subject, logscale=True)

