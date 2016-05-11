from matplotlib import pyplot as plt
import numpy as np

import colors_manager

import sys
'''
this script is used to generate plot in results/<subject>/img/
'''


def plot_increasingPerturbation_percentageSuccess(path, filename, output, subject, indexN, indexLoc, columnLoc, offsetStart=0):

    '''
    this function is used to generate the plot of the increasing magnitudes and random rates.
    '''

    lines = [line.rstrip('\n') for line in open(path+"/"+filename)]


    labelOfN = ' '.join(lines[indexN].split()).split(" ")[0]
    print(labelOfN)
    n = ' '.join(lines[indexN].split()).split(" ")[3:]
    numberOfLocation = int(' '.join(lines[indexLoc].split()).split(" ")[0])

    percAll=[]
    nAll=[]
    callAll=[]
    perturbAll=[]
    indicesLocation=[]
    i = 8 + offsetStart

    print(n)

    while i < (numberOfLocation*len(n)):#numberOfLocation:

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
            my_call += int(' '.join(line.split()).split(" ")[5+offsetStart].replace(',','.'))
            my_perturb += int(' '.join(line.split()).split(" ")[6+offsetStart].replace(',','.'))
            my_n.append(n[lines[i:i+len(n)].index(line)])


        if perc not in percAll and len(perc) > 0 and [p == p for p in perc]:
            indexOfLocation = ' '.join(lines[i].split()).split(" ")[columnLoc]
            indicesLocation.append(indexOfLocation)
            percAll.append(perc)
            nAll.append(my_n)
            callAll.append(my_call)
            perturbAll.append(my_perturb)

        i+=len(n)

    sortedPerc, indicesLocation = [list(x)[:10] for x in zip(*sorted(zip(percAll, indicesLocation), key=lambda pair: -pair[0][-1]))]
    sortedPerc, callAll = [list(x)[:10] for x in zip(*sorted(zip(percAll, callAll), key=lambda pair: -pair[0][-1]))]
    sortedPerc, perturbAll = [list(x)[:10] for x in zip(*sorted(zip(percAll, perturbAll), key=lambda pair: -pair[0][-1]))]
    percAll, nAll = [list(x)[:10] for x in zip(*sorted(zip(percAll, nAll), key=lambda pair: -pair[0][-1]))]

    indexToCutAll = []
    for i in range(len(percAll)):
        indexToCut = len(percAll[i])-1
        while indexToCut > 1:
            if percAll[i][indexToCut] == percAll[i][indexToCut-1]:
                indexToCut -= 1
            else:
                break;
        indexToCutAll.append(indexToCut+1 if indexToCut < len(percAll[i]) - 1 else indexToCut)

    indexToCut = max(indexToCutAll)

    sortedPerc, indicesLocation = [list(x) for x in zip(*sorted(zip(percAll, indicesLocation), key=lambda pair: -pair[0][0]))]
    sortedPerc, callAll = [list(x) for x in zip(*sorted(zip(percAll, callAll), key=lambda pair: -pair[0][0]))]
    sortedPerc, perturbAll = [list(x) for x in zip(*sorted(zip(percAll, perturbAll), key=lambda pair: -pair[0][0]))]
    percAll, nAll = [list(x) for x in zip(*sorted(zip(percAll, nAll), key=lambda pair: -pair[0][0]))]

    fig = plt.figure()
    ax = fig.add_axes((.1,.4,.8,.5))
    for i in range(len(percAll)):
        cut = min(indexToCut, len(percAll[i]))
        color = colors_manager.getColor(int(indicesLocation[i]))
        plt.plot(nAll[i][:cut], percAll[i][:cut], marker='x', label=str(indicesLocation[i]+ " " + str(int(percAll[i][0])) + " %"), color=color)
    plt.xlabel(labelOfN)
    plt.ylabel("% success")
    box = ax.get_position()
    txt = ""
    for line in lines[0:7]:
        txt += line +"\n"
    width = 8
    txt+= '{0:{width}} {1:{width}} {2:{width}}'.format("Loc","#Call", "#Perturbation", width=width)
    txt+='\n'
    for i in range(len(callAll)):
        txt+='{0:{width}} {1:{width}} {2:{width}}'.format(indicesLocation[i],str(callAll[i]),str(perturbAll[i]), width=width)
        txt+='\n'

    text = fig.text(.1,-.45,txt)
    plt.title(subject)
    ax.set_position([box.x0, box.y0, box.width * 0.8, box.height])
    lgd = ax.legend(loc='center left', bbox_to_anchor=(1, 0.5))
    fig.savefig(path+"/img/"+output+"_plot.jpeg", bbox_extra_artists=(lgd,text), bbox_inches='tight')
    fig.savefig(path+"/img/"+output+"_plot.pdf", bbox_extra_artists=(lgd,text), bbox_inches='tight')
    ax.set_xscale('log')
    fig.savefig(path+"/img/"+output+"_plot_logscale.pdf", bbox_extra_artists=(lgd,text), bbox_inches='tight')
    fig.savefig(path+"/img/"+output+"_plot_logscale.jpeg", bbox_extra_artists=(lgd,text), bbox_inches='tight')
    plt.close(fig)

def scatterPlotSuccessNumPerturb(path, filename, output, subject):
     lines = [line.rstrip('\n') for line in open(path+"/"+filename)]

     n = ' '.join(lines[4].split()).split(" ")[3:]
     numberOfLocation = int(' '.join(lines[2].split()).split(" ")[0])

     repeat = int(' '.join(lines[5].split()).split(" ")[0])
     nbTask = int(' '.join(lines[6].split()).split(" ")[0])

     nbExec = float(repeat * nbTask)

     percAll=[]
     nbPerturbAll=[]
     labelsAll=[]
     indicesLocation=[]
     nbCallAll=[]
     i = 9
     currentLoc = 0

     while currentLoc != 10 and i < (numberOfLocation*len(n)):#numberOfLocation:

        perc=[]
        label=[]
        nbPerturb=[]
        nbCall=[]

        for line in lines[i:i+len(n)]:
            point = float(' '.join(line.split()).split(" ")[-1].replace(',','.'))
            if point == point:
                perc.append(point)
                nbPerturb.append(int(' '.join(line.split()).split(" ")[7].replace(',','.')))
                nbCall.append(int(' '.join(line.split()).split(" ")[6].replace(',','.')))

        label.append(str(' '.join(lines[i].split()).split(" ")[0].replace(',','.')))
        label.append(str(' '.join(lines[i+(len(n))/2].split()).split(" ")[0].replace(',','.')))
        label.append(str(' '.join(lines[i+len(n)-1].split()).split(" ")[0].replace(',','.')))

        if not perc in percAll and not len(perc) == 0:
            indexOfLocation = ' '.join(lines[i].split()).split(" ")[2]
            indicesLocation.append(indexOfLocation)

            labelsAll.append(label)
            nbPerturbAll.append(nbPerturb)
            percAll.append(perc)
            nbCallAll.append(nbCall)

            currentLoc += 1

        i+=len(n)

     sortedPerc, indicesLocation = [list(x) for x in zip(*sorted(zip(percAll, indicesLocation), key=lambda pair: -pair[0][0]))]
     sortedPerc, nbPerturbAll = [list(x) for x in zip(*sorted(zip(percAll, nbPerturbAll), key=lambda pair: -pair[0][0]))]
     percAll, nbCallAll = [list(x) for x in zip(*sorted(zip(percAll, nbCallAll), key=lambda pair: -pair[0][0]))]

     fig = plt.figure()
     ax = fig.add_axes((.1,.4,.8,.5))
     for i in range(len(percAll)):
         mid = len(percAll[i]) / 2
         color = colors_manager.getColor(int(indicesLocation[i]))
         x = [float(float(nbPerturbAll[i][len(nbPerturbAll[i])-len(percAll[i]):][z]) / nbExec)
              for z in range(len(nbCallAll[i][len(nbCallAll[i])-len(percAll[i]):]))]
         plt.plot(x , percAll[i], color=color, marker='x', label=str(indicesLocation[i]+" "+ str(int(percAll[i][0]))+ " %"))
         for z in [0,-1,mid]:
            x = float( float(nbPerturbAll[i][z]) / nbExec)
            ax.annotate(labelsAll[i][z if z != mid else 1], xy = (x, percAll[i][z]),
                        xytext=(0.5, 5), textcoords='offset points', size=5)

     plt.xlabel("Avg perturbation per exec")
     plt.ylabel("% success")
     plt.title(subject)

     txt = "Annotation are the probability rate of enaction\n"
     for line in lines[0:7]:
         txt += line +"\n"
     text = fig.text(.1,-.1,txt)

     box = ax.get_position()
     ax.set_position([box.x0, box.y0, box.width * 0.8, box.height])
     lgd = ax.legend(loc='center left', bbox_to_anchor=(1, 0.5))
     fig.savefig(path+"/img/scatterPlotSuccessNumPerturb_"+output+".pdf", bbox_extra_artists=(lgd,text), bbox_inches='tight')
     fig.savefig(path+"/img/scatterPlotSuccessNumPerturb_"+output+".jpeg", bbox_extra_artists=(lgd,text), bbox_inches='tight')
     ax.set_xscale('symlog')
     fig.savefig(path+"/img/scatterPlotSuccessNumPerturb_"+output+"_log.pdf", bbox_extra_artists=(lgd,text), bbox_inches='tight')
     fig.savefig(path+"/img/scatterPlotSuccessNumPerturb_"+output+"_log.jpeg", bbox_extra_artists=(lgd,text), bbox_inches='tight')
     plt.close(fig)

#subjects=["quicksort","zip","md5","sudoku","optimizer","mersenne"]
subjects=sys.argv[1:]
for subject in subjects:
    print(subject)
    scatterPlotSuccessNumPerturb("results/"+subject, "IntegerAddOne_RandomExplorer_analysis_graph_data.txt", "intadd1_rnd" ,subject)
    scatterPlotSuccessNumPerturb("results/"+subject, "BooleanNegation_RandomExplorer_analysis_graph_data.txt", "boolinv_rnd", subject)
    plot_increasingPerturbation_percentageSuccess("results/"+subject, "IntegerAddM_CallExplorer_analysis_graph_data.txt", "magnitude_call" ,subject, 2, 3, 1)
    plot_increasingPerturbation_percentageSuccess("results/"+subject, "IntegerAddOne_RandomExplorer_analysis_graph_data.txt", "intadd1_rnd",subject, 4, 2, 2, offsetStart=1)
    plot_increasingPerturbation_percentageSuccess("results/"+subject, "BooleanNegation_RandomExplorer_analysis_graph_data.txt", "boolinv_rnd", subject, 4 ,2, 2, offsetStart=1)
